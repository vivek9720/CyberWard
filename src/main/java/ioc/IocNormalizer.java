package ioc;
import core.*;
import java.net.URI;
public final class IocNormalizer {
 public Ioc normalize(String raw) { return normalize(raw, Severity.MEDIUM, 0.75, "local"); }
 public Ioc normalize(String raw, Severity sev, double confidence, String source) {
  String s = raw == null ? "" : StringUtil.stripComment(raw).trim();
  if (s.isEmpty()) return new Ioc(IocType.UNKNOWN, "", Severity.INFO, 0, source);
  if (s.contains(",")) {
   java.util.List<String> cells = CsvUtil.parseLine(s);
   if (!cells.isEmpty()) s = cells.get(0);
   if (cells.size() > 1) sev = Severity.fromText(cells.get(1));
   if (cells.size() > 2) try { confidence = Double.parseDouble(cells.get(2)); } catch (Exception ignored) {}
  }
  if (s.startsWith("sha256:") || s.startsWith("md5:") || s.startsWith("sha1:")) s = s.substring(s.indexOf(':') + 1);
  IocType type = typeOf(s);
  return new Ioc(type, canonical(type, s), sev, confidence, source);
 }
 public IocType typeOf(String s) {
  if (s == null) return IocType.UNKNOWN;
  String x = s.trim();
  if (x.contains("/") && x.matches("\\d+\\.\\d+\\.\\d+\\.\\d+/\\d{1,2}")) return IocType.CIDR;
  if (x.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) return IocType.IP;
  if (x.matches("(?i)[a-f0-9]{32}|[a-f0-9]{40}|[a-f0-9]{64}")) return IocType.HASH;
  if (x.matches("(?i)https?://.+")) return IocType.URL;
  if (x.matches("(?i)[a-z0-9][a-z0-9.-]*\\.[a-z]{2,63}")) return IocType.DOMAIN;
  return IocType.UNKNOWN;
 }
 public String canonical(IocType type, String s) {
  s = s == null ? "" : s.trim();
  try {
   switch (type) {
    case IP: return IpAddress.parse(s).toString();
    case CIDR: return CidrBlock.parse(s).toString();
    case DOMAIN: return StringUtil.asciiDomain(s);
    case URL:
     URI u = URI.create(s);
     String host = u.getHost() == null ? "" : StringUtil.asciiDomain(u.getHost());
     String path = u.getRawPath() == null ? "/" : u.getRawPath();
     return (u.getScheme() == null ? "http" : u.getScheme().toLowerCase()) + "://" + host + path + (u.getRawQuery() == null ? "" : "?" + u.getRawQuery());
    case HASH: return s.toLowerCase(java.util.Locale.ROOT);
    default: return s;
   }
  } catch (Exception e) { return s.toLowerCase(java.util.Locale.ROOT); }
 }
}
