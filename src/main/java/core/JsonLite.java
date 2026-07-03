package core;
import java.util.LinkedHashMap;
import java.util.Map;
public final class JsonLite {
 private JsonLite() {}
 public static Map<String,String> parseFlatObject(String text) {
  Map<String,String> out = new LinkedHashMap<>();
  if (text == null) return out;
  String s = text.trim();
  if (s.startsWith("{") && s.endsWith("}")) s = s.substring(1, s.length() - 1);
  for (String part : StringUtil.splitRespectingQuotes(s, ',')) {
   int colon = part.indexOf(':');
   if (colon < 0) continue;
   out.put(StringUtil.unquote(part.substring(0, colon).trim()), StringUtil.unquote(part.substring(colon + 1).trim()));
  }
  return out;
 }
 public static String quote(String s) { if (s == null) return "null"; return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n") + "\""; }
}
