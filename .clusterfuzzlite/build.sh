#!/usr/bin/env bash
set -eu
ROOT="${SRC:-$(pwd)}"
OUT="${OUT:-$ROOT/out}"
BUILD="$ROOT/build/clusterfuzzlite"
path_for_jdk() {
  if command -v cygpath >/dev/null 2>&1; then cygpath -w "$1"; else printf '%s
' "$1"; fi
}
source_list() {
  if command -v cygpath >/dev/null 2>&1; then while IFS= read -r f; do cygpath -w "$f"; done; else cat; fi
}
CPSEP=":"
if command -v cygpath >/dev/null 2>&1; then CPSEP=";"; fi
rm -rf "$BUILD"
mkdir -p "$BUILD/classes" "$OUT"
find "$ROOT/src/main/java" "$ROOT/fuzz" -name '*.java' | sort | source_list > "$BUILD/sources.txt"
javac --release 17 -d "$(path_for_jdk "$BUILD/classes")" @"$(path_for_jdk "$BUILD/sources.txt")"
jar cf "$(path_for_jdk "$OUT/cyberward-fuzzers.jar")" -C "$(path_for_jdk "$BUILD/classes")" .
for target in PacketFuzzer IocFuzzer RulesFuzzer PolicyFuzzer; do
  cat > "$OUT/$target" <<'WRAP'
#!/usr/bin/env bash
DIR="$(cd "$(dirname "$0")" && pwd)"
if [ -n "${JAZZER_STANDALONE_JAR:-}" ]; then
  exec java -cp "$DIR/cyberward-fuzzers.jar:$JAZZER_STANDALONE_JAR" com.code_intelligence.jazzer.Jazzer --target_class="$(basename "$0")" "$@"
elif command -v jazzer >/dev/null 2>&1; then
  exec jazzer --cp="$DIR/cyberward-fuzzers.jar" --target_class="$(basename "$0")" "$@"
else
  exec java -cp "$DIR/cyberward-fuzzers.jar" "$(basename "$0")" "$@"
fi
WRAP
  chmod +x "$OUT/$target"
done
