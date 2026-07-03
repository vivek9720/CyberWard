package ioc;
import core.*;import packet.PacketMetadata;import java.util.ArrayList;import java.util.List;
public final class IocMatcher{
 public List<MatchResult> match(IocSet set,List<PacketMetadata> packets){List<MatchResult> out=new ArrayList<>();if(set==null||packets==null)return out;List<Ioc> iocs=set.all();for(PacketMetadata p:packets){for(Ioc i:iocs){if(matches(i,p,"source"))out.add(new MatchResult(i,p,"source"));if(matches(i,p,"destination"))out.add(new MatchResult(i,p,"destination"));for(String name:p.dnsNames())if((i.type()==IocType.DOMAIN&&domainMatches(i.value(),name))||(i.type()==IocType.URL&&i.value().contains(name)))out.add(new MatchResult(i,p,"dns"));}}return out;}
 private boolean matches(Ioc i,PacketMetadata p,String side){try{IpAddress ip=side.equals("source")?p.sourceIp():p.destinationIp();if(ip==null)return false;if(i.type()==IocType.IP)return ip.equals(IpAddress.parse(i.value()));if(i.type()==IocType.CIDR)return CidrBlock.parse(i.value()).contains(ip);}catch(Exception ignored){}return false;}
 public boolean domainMatches(String ioc,String observed){String a=StringUtil.asciiDomain(ioc);String b=StringUtil.asciiDomain(observed);return b.equals(a)||b.endsWith("."+a);}
}
