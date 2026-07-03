package rules;
import core.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
public final class RuleParser {
 public ParseResult<List<IdsRule>> parse(byte[] data) { return parseText(new String(data == null ? new byte[0] : data, StandardCharsets.UTF_8)); }
 public ParseResult<List<IdsRule>> parseText(String text) {
  Diagnostics d = new Diagnostics();
  List<IdsRule> rules = new ArrayList<>();
  if (text == null) return ParseResult.of(rules, d);
  int lineNo = 0;
  for (String raw : text.split("\\R")) {
   lineNo++;
   String line = StringUtil.stripComment(raw).trim();
   if (line.isEmpty()) continue;
   try { rules.add(parseRule(line)); } catch (Exception e) { d.medium("rule.parse", "line " + lineNo + ": " + e.getMessage()); }
  }
  return ParseResult.of(rules, d);
 }
 public IdsRule parseRule(String line) {
  int open = line.indexOf('('); int close = line.lastIndexOf(')');
  String header = open >= 0 ? line.substring(0, open).trim() : line;
  String opts = (open >= 0 && close > open) ? line.substring(open + 1, close) : "";
  String[] h = header.split("\\s+");
  if (h.length < 7) throw new IllegalArgumentException("rule header requires action protocol addresses ports and direction");
  IdsRule r = new IdsRule();
  r.action(h[0].toLowerCase()); r.protocol(h[1].toLowerCase()); r.source(new AddressExpression(h[2])); r.sourcePort(new PortExpression(h[3])); r.direction(h[4]); r.destination(new AddressExpression(h[5])); r.destinationPort(new PortExpression(h[6]));
  for (String part : StringUtil.splitRespectingQuotes(opts, ';')) {
   if (part.isBlank()) continue;
   int colon = part.indexOf(':');
   if (colon < 0) r.option(part.trim().toLowerCase(), "true");
   else r.option(part.substring(0, colon).trim().toLowerCase(), StringUtil.unquote(part.substring(colon + 1).trim()));
  }
  return r;
 }
}
