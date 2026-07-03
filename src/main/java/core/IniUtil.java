package core;
import java.util.LinkedHashMap;
import java.util.Map;
public final class IniUtil {
 private IniUtil() {}
 public static Map<String,Map<String,String>> parse(String text) {
  Map<String,Map<String,String>> out = new LinkedHashMap<>();
  String section = "default";
  out.put(section, new LinkedHashMap<>());
  if (text == null) return out;
  for (String raw : text.split("\\R")) {
   String line = StringUtil.stripComment(raw).trim();
   if (line.isEmpty()) continue;
   if (line.startsWith("[") && line.endsWith("]")) { section = line.substring(1, line.length() - 1).trim(); out.putIfAbsent(section, new LinkedHashMap<>()); }
   else { int eq = line.indexOf('='); if (eq > 0) out.get(section).put(line.substring(0, eq).trim(), line.substring(eq + 1).trim()); }
  }
  return out;
 }
}
