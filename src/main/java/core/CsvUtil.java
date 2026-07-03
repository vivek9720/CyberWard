package core;
import java.util.ArrayList;
import java.util.List;
public final class CsvUtil {
 private CsvUtil() {}
 public static List<String> parseLine(String line) {
  List<String> out = new ArrayList<>();
  if (line == null) return out;
  StringBuilder sb = new StringBuilder();
  boolean quote = false;
  for (int i = 0; i < line.length(); i++) {
   char c = line.charAt(i);
   if (c == '"') {
    if (quote && i + 1 < line.length() && line.charAt(i + 1) == '"') { sb.append('"'); i++; }
    else quote = !quote;
   } else if (c == ',' && !quote) { out.add(sb.toString().trim()); sb.setLength(0); }
   else sb.append(c);
  }
  out.add(sb.toString().trim());
  return out;
 }
 public static String writeLine(List<String> cells) {
  StringBuilder sb = new StringBuilder();
  if (cells == null) return "";
  for (int i = 0; i < cells.size(); i++) {
   if (i > 0) sb.append(',');
   String c = cells.get(i) == null ? "" : cells.get(i);
   if (c.contains(",") || c.contains("\"") || c.contains("\n")) sb.append('"').append(c.replace("\"", "\"\"")).append('"');
   else sb.append(c);
  }
  return sb.toString();
 }
}
