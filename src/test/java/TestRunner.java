import core.*;
import packet.*;
import ioc.*;
import rules.*;
import policy.*;
public final class TestRunner {
 public static void main(String[] args) throws Exception {
  testCore(); testIoc(); testRules(); testPolicy(); testPacket();
  System.out.println("all tests passed");
 }
 static void check(boolean v, String m) { if (!v) throw new AssertionError(m); }
 static void testCore() {
  check(IpAddress.parse("192.168.1.1").isPrivate(), "private ip");
  check(CidrBlock.parse("192.168.1.0/24").contains(IpAddress.parse("192.168.1.5")), "cidr");
  check(PortRange.parse("20:30").contains(25), "port range");
 }
 static void testIoc() {
  String text = "1.2.3.4\nexample.com\nhttps://Example.com/a\n0123456789abcdef0123456789abcdef\n10.0.0.0/8";
  java.util.List<Ioc> i = new IocParser().parseText(text).orNull();
  check(i.size() == 5, "ioc size");
  check(new DuplicateReport().duplicates(java.util.Arrays.asList(i.get(0), i.get(0))).size() == 1, "dupes");
 }
 static void testRules() {
  String rule = "alert tcp any any -> 1.2.3.4 80 (msg:\"web\"; content:\"GET\"; sid:1; rev:1;)";
  java.util.List<IdsRule> r = new RuleParser().parseText(rule).orNull();
  check(r.size() == 1, "rule parse");
  check(new RuleValidator().validate(r).isEmpty(), "rule valid");
  check(new RuleNormalizer().normalize(r.get(0)).contains("sid:1"), "normalize");
 }
 static void testPolicy() {
  String rules = "-A INPUT -p tcp --dport 22 -j ACCEPT\n-A INPUT -p tcp --dport 22 -j ACCEPT";
  PolicyDocument d = new PolicyParser().parseText(rules).orNull();
  check(d.size() == 2, "policy parse");
  check(new RuleOrderingAnalyzer().duplicates(d).size() == 1, "policy dup");
 }
 static void testPacket() throws Exception {
  byte[] p = SampleData.smallPcap();
  java.util.List<PacketMetadata> m = new PacketInspector().inspectPcap(p);
  check(m.size() == 1, "pcap one");
  check("udp".equals(m.get(0).protocol()), "udp");
  check(m.get(0).dnsNames().contains("example.com"), "dns name");
 }
}
