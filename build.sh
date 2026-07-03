#!/usr/bin/env bash
set -eu
ROOT="$(cd "$(dirname "$0")" && pwd)"
BUILD="$ROOT/build"
CPSEP=":"
path_for_jdk() {
  if command -v cygpath >/dev/null 2>&1; then cygpath -w "$1"; else printf '%s
' "$1"; fi
}
source_list() {
  if command -v cygpath >/dev/null 2>&1; then while IFS= read -r f; do cygpath -w "$f"; done; else cat; fi
}
if command -v cygpath >/dev/null 2>&1; then CPSEP=";"; fi
rm -rf "$BUILD"
mkdir -p "$BUILD/classes" "$BUILD/test-classes"
find "$ROOT/src/main/java" -name '*.java' | sort | source_list > "$BUILD/sources.txt"
javac --release 17 -d "$(path_for_jdk "$BUILD/classes")" @"$(path_for_jdk "$BUILD/sources.txt")"
if [ "${1:-}" = "test" ]; then
  find "$ROOT/src/test/java" -name '*.java' | sort | source_list > "$BUILD/tests.txt"
  javac --release 17 -cp "$(path_for_jdk "$BUILD/classes")" -d "$(path_for_jdk "$BUILD/test-classes")" @"$(path_for_jdk "$BUILD/tests.txt")"
  java -cp "$(path_for_jdk "$BUILD/classes")$CPSEP$(path_for_jdk "$BUILD/test-classes")" TestRunner
fi
