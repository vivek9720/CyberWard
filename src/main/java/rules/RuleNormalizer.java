package rules;
import java.util.Map;
public final class RuleNormalizer {
 public String normalize(IdsRule r) {
  StringBuilder sb = new StringBuilder();
  sb.append(r.action().toLowerCase()).append(' ').append(r.protocol().toLowerCase()).append(' ').append(r.source().normalized()).append(' ').append(r.sourcePort().normalized()).append(' ').append(r.direction()).append(' ').append(r.destination().normalized()).append(' ').append(r.destinationPort().normalized()).append(" (");
  for (Map.Entry<String,String> e : r.options().entrySet()) {
   sb.append(e.getKey().toLowerCase()).append(':');
   String value = e.getKey().equals("content") ? normalizeContent(e.getValue()) : e.getValue();
   if (value.matches("[A-Za-z0-9_.-]+")) sb.append(value); else sb.append('"').append(value.replace("\"", "\\\"")).append('"');
   sb.append(';');
  }
  sb.append(')');
  return sb.toString();
 }
 private String normalizeContent(String value) {
  if (value == null || !value.startsWith("|") || !value.endsWith("|")) return value == null ? "" : value;
  String hex = value.substring(1, value.length() - 1).replace(" ", "");
  StringBuilder out = new StringBuilder();
  for (int i = 0; i < hex.length(); i += 2) {
   int b = Integer.parseInt(hex.substring(i, i + 2), 16);
   out.append((char)b);
  }
  return out.toString();
 }
}
