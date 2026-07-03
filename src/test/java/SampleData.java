import java.io.ByteArrayOutputStream;
public final class SampleData {
 static byte[] smallPcap() throws Exception {
  ByteArrayOutputStream dns = new ByteArrayOutputStream();
  dns.write(new byte[]{0x12,0x34,0x01,0x00,0x00,0x01,0,0,0,0,0,0});
  for (String l : "example.com".split("\\.")) { dns.write(l.length()); dns.write(l.getBytes(java.nio.charset.StandardCharsets.US_ASCII)); }
  dns.write(0); dns.write(new byte[]{0,1,0,1});
  byte[] d = dns.toByteArray();
  int udpLen = 8 + d.length;
  ByteArrayOutputStream udp = new ByteArrayOutputStream();
  udp.write(new byte[]{0x30,0x39,0,0x35,(byte)(udpLen >> 8),(byte)udpLen,0,0}); udp.write(d);
  byte[] u = udp.toByteArray();
  int ipLen = 20 + u.length;
  ByteArrayOutputStream ip = new ByteArrayOutputStream();
  ip.write(new byte[]{0x45,0,(byte)(ipLen >> 8),(byte)ipLen,0,1,0,0,64,17,0,0,(byte)192,(byte)168,1,2,8,8,8,8}); ip.write(u);
  ByteArrayOutputStream eth = new ByteArrayOutputStream();
  eth.write(new byte[]{0,1,2,3,4,5,6,7,8,9,10,11,8,0}); eth.write(ip.toByteArray());
  byte[] frame = eth.toByteArray();
  ByteArrayOutputStream p = new ByteArrayOutputStream();
  p.write(new byte[]{(byte)0xd4,(byte)0xc3,(byte)0xb2,(byte)0xa1,2,0,4,0,0,0,0,0,0,0,0,0,(byte)0xff,(byte)0xff,0,0,1,0,0,0});
  int len = frame.length;
  p.write(new byte[]{1,0,0,0,0,0,0,0,(byte)len,0,0,0,(byte)len,0,0,0}); p.write(frame);
  return p.toByteArray();
 }
}
