package packet;
import core.IpAddress;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public final class PacketMetadata{
 private Instant timestamp=Instant.EPOCH;private String linkType="unknown";private String protocol="unknown";private IpAddress sourceIp;private IpAddress destinationIp;private int sourcePort=-1;private int destinationPort=-1;private int capturedLength;private int originalLength;private final List<String> dnsNames=new ArrayList<>();private final List<String> notes=new ArrayList<>();
 public Instant timestamp(){return timestamp;} public void timestamp(Instant v){timestamp=v==null?Instant.EPOCH:v;} public String linkType(){return linkType;} public void linkType(String v){linkType=v==null?"unknown":v;} public String protocol(){return protocol;} public void protocol(String v){protocol=v==null?"unknown":v.toLowerCase(java.util.Locale.ROOT);} public IpAddress sourceIp(){return sourceIp;} public void sourceIp(IpAddress v){sourceIp=v;} public IpAddress destinationIp(){return destinationIp;} public void destinationIp(IpAddress v){destinationIp=v;} public int sourcePort(){return sourcePort;} public void sourcePort(int v){sourcePort=v;} public int destinationPort(){return destinationPort;} public void destinationPort(int v){destinationPort=v;} public int capturedLength(){return capturedLength;} public void capturedLength(int v){capturedLength=v;} public int originalLength(){return originalLength;} public void originalLength(int v){originalLength=v;} public void addDnsName(String n){if(n!=null&&!n.isBlank())dnsNames.add(n);} public List<String> dnsNames(){return Collections.unmodifiableList(dnsNames);} public void note(String n){if(n!=null&&!n.isBlank())notes.add(n);} public List<String> notes(){return Collections.unmodifiableList(notes);} public String flowKey(){return(sourceIp==null?"?":sourceIp)+":"+sourcePort+" -> "+(destinationIp==null?"?":destinationIp)+":"+destinationPort+"/"+protocol;}
}
