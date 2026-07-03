package ioc;
import core.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
public final class IocParser {
 private final IocNormalizer normalizer = new IocNormalizer();
 public ParseResult<List<Ioc>> parse(byte[] data) { return parseText(new String(data == null ? new byte[0] : data, StandardCharsets.UTF_8)); }
 public ParseResult<List<Ioc>> parseText(String text) {
  Diagnostics d = new Diagnostics();
  List<Ioc> out = new ArrayList<>();
  if (text == null) return ParseResult.of(out, d);
  int lineNo = 0;
  for (String raw : text.split("\\R")) {
   lineNo++;
   String line = StringUtil.stripComment(raw).trim();
   if (line.isEmpty()) continue;
   if (line.startsWith("{") && line.endsWith("}")) { java.util.Map<String,String> obj = JsonLite.parseFlatObject(line); line = obj.getOrDefault("value", obj.getOrDefault("ioc", "")); }
   Ioc i = normalizer.normalize(line);
   if (i.type() == IocType.UNKNOWN) d.low("ioc.unknown", "unrecognized indicator at line " + lineNo); else out.add(i);
  }
  return ParseResult.of(out, d);
 }
}
