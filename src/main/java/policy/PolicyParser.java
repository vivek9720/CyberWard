package policy;
import core.*;
import java.nio.charset.StandardCharsets;
public final class PolicyParser {
 public ParseResult<PolicyDocument> parse(byte[] data) { return parseText(new String(data == null ? new byte[0] : data, StandardCharsets.UTF_8)); }
 public ParseResult<PolicyDocument> parseText(String text) {
  Diagnostics d = new Diagnostics();
  PolicyDocument doc = new PolicyDocument();
  IptablesParser ipt = new IptablesParser(); NftablesParser nft = new NftablesParser(); CsvPolicyParser csv = new CsvPolicyParser(); LocalPolicyParser local = new LocalPolicyParser();
  if (text == null) return ParseResult.of(doc, d);
  int lineNo = 0;
  for (String raw : text.split("\\R")) {
   lineNo++;
   String line = StringUtil.stripComment(raw).trim();
   if (line.isEmpty() || line.startsWith("*COMMIT") || line.equals("COMMIT")) continue;
   try {
    FirewallRule r = null;
    if (line.startsWith("-A ") || line.startsWith("-I ")) r = ipt.parseLine(line);
    else if (line.contains(" dport ") || line.contains(" saddr ") || line.contains(" daddr ")) r = nft.parseLine(line);
    else if (line.startsWith("{")) r = local.parseJsonRule(line);
    else if (line.contains(",")) r = csv.parseLine(line);
    if (r != null) doc.add(r); else d.low("policy.unrecognized", "line " + lineNo + " was not recognized");
   } catch (Exception e) { d.medium("policy.parse", "line " + lineNo + ": " + e.getMessage()); }
  }
  return ParseResult.of(doc, d);
 }
}
