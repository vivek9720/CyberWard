# CyberWard

CyberWard is a local defensive security toolkit for inspecting packet captures, indicator lists, IDS-style rules, and firewall policy exports. It is designed for security engineers and system administrators who need deterministic offline analysis of artifacts collected from managed systems.

## Use cases

- Summarize PCAP files and extract IPv4, TCP, UDP, and DNS metadata.
- Normalize IP, domain, URL, hash, and CIDR indicators from local IOC files.
- Match local indicators against packet metadata without network access.
- Parse and validate a practical subset of Snort and Suricata-style signatures.
- Review iptables, nftables, CSV, JSON, and INI policy snippets for duplicates, broad allows, and shadowed ordering.

## Supported formats

PCAP, Ethernet, IPv4, TCP, UDP, DNS, plain-text IOC lists, CSV IOC rows, flat JSON IOC objects, Snort/Suricata-like rules, iptables-style rules, nftables-style lines, CSV policy rows, JSON policy objects, and INI policy sections.

## Architecture

- `core` contains byte readers, endian helpers, diagnostics, result types, checksums, safe slicing, string utilities, time parsing, CIDR/IP helpers, CSV/INI/JSON helpers, and security reference data.
- `packet` parses PCAP, Ethernet, IPv4, TCP, UDP, and DNS and produces packet summaries.
- `ioc` parses, normalizes, scores, deduplicates, and matches indicators.
- `rules` lexes, parses, validates, normalizes, and matches IDS-style rule metadata.
- `policy` parses firewall and local policy formats and analyzes ordering and duplicates.
- `tools` provides command-line entry points that call the library code.

## Build

A Java 17 JDK is required. No third-party dependencies are used.

```bash
./build.sh
```

## Tests

```bash
./build.sh test
```

## CLI usage

```bash
java -cp build/classes tools.PacketScan capture.pcap
java -cp build/classes tools.IocMatch indicators.txt capture.pcap
java -cp build/classes tools.RuleCheck local.rules
java -cp build/classes tools.PolicyAudit firewall.rules
```

## Developer QA and robustness testing

The `fuzz/` directory contains Jazzer-compatible harness classes for the packet, IOC, IDS rule, and policy parsers. They accept raw bytes and call the same library paths used by the CLI tools. The seed corpus under `fuzz/corpus/` provides realistic starting inputs, and `fuzz/dictionary.txt` contains protocol, IOC, rule, and firewall tokens useful for mutation.

ClusterFuzzLite-compatible build metadata is in `.clusterfuzzlite/`. The build script compiles the Java sources and harnesses into `$OUT` without downloading dependencies.

## Seed corpus

Packet seeds include valid PCAP samples for TCP and UDP/DNS traffic plus a malformed near-valid capture. IOC seeds cover IPs, domains, URLs, hashes, CIDR blocks, and malformed indicators. Rule and policy seeds include simple valid artifacts, multi-rule files, and near-valid malformed inputs.

## Manual review checklist

- Confirm all command-line tools operate only on local files.
- Review parser bounds handling and expected invalid-input diagnostics.
- Review rule and policy normalization behavior against local conventions.
- Extend seed files with artifacts from internal test networks where permitted.
- Confirm new code remains original, readable, deterministic, and dependency-free.
