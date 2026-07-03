package core;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
public final class StringUtil {
 private StringUtil() {}
 public static String normalizeSpace(String s) { return s == null ? "" : s.trim().replaceAll("\\s+", " "); }
 public static String lowerAscii(String s) { return s == null ? "" : s.toLowerCase(java.util.Locale.ROOT); }
 public static String stripComment(String line) {
  if (line == null) return "";
  boolean quote = false;
  for (int i = 0; i < line.length(); i++) {
   char c = line.charAt(i);
   if (c == '"') quote = !quote;
   if (!quote && c == '#') return line.substring(0, i);
   if (!quote && c == '/' && i + 1 < line.length() && line.charAt(i + 1) == '/' && (i == 0 || Character.isWhitespace(line.charAt(i - 1)))) return line.substring(0, i);
  }
  return line;
 }
 public static List<String> splitRespectingQuotes(String text, char delimiter) {
  List<String> out = new ArrayList<>();
  if (text == null) return out;
  StringBuilder sb = new StringBuilder();
  boolean quote = false;
  char q = 0;
  for (int i = 0; i < text.length(); i++) {
   char c = text.charAt(i);
   if ((c == '"' || c == '\'') && (i == 0 || text.charAt(i - 1) != '\\')) {
    if (!quote) { quote = true; q = c; }
    else if (q == c) quote = false;
    sb.append(c);
   } else if (c == delimiter && !quote) { out.add(sb.toString().trim()); sb.setLength(0); }
   else sb.append(c);
  }
  out.add(sb.toString().trim());
  return out;
 }
 public static String unquote(String s) {
  s = normalizeSpace(s);
  if (s.length() >= 2 && ((s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') || (s.charAt(0) == '\'' && s.charAt(s.length() - 1) == '\''))) return s.substring(1, s.length() - 1).replace("\\\"", "\"").replace("\\'", "'");
  return s;
 }
 public static String asciiDomain(String s) { String n = Normalizer.normalize(lowerAscii(s), Normalizer.Form.NFKC); if (n.endsWith(".")) n = n.substring(0, n.length() - 1); return n; }
 public static boolean containsControl(String s) { if (s == null) return false; for (int i = 0; i < s.length(); i++) if (Character.isISOControl(s.charAt(i)) && !Character.isWhitespace(s.charAt(i))) return true; return false; }
}
