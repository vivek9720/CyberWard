package packet;
import core.SecurityCatalog;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
public final class PacketSummary {
 public String summarize(List<PacketMetadata> packets) {
  StringBuilder sb = new StringBuilder();
  Map<String,Integer> proto = new TreeMap<>();
  Map<Integer,Integer> dstPorts = new TreeMap<>();
  int dns = 0;
  for (PacketMetadata p : packets) { proto.merge(p.protocol(), 1, Integer::sum); if (p.destinationPort() >= 0) dstPorts.merge(p.destinationPort(), 1, Integer::sum); dns += p.dnsNames().size(); }
  sb.append("packets=").append(packets.size()).append('\n');
  sb.append("protocols=").append(proto).append('\n');
  sb.append("dns_names=").append(dns).append('\n');
  sb.append("destination_ports=");
  int shown = 0;
  for (Map.Entry<Integer,Integer> e : dstPorts.entrySet()) { if (shown++ > 0) sb.append(' '); sb.append(e.getKey()).append('/').append(SecurityCatalog.service(e.getKey()).name()).append('=').append(e.getValue()); if (shown >= 20) break; }
  sb.append('\n');
  return sb.toString();
 }
}
