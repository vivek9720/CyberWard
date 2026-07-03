package core;
public final class CidrBlock{
 private final IpAddress network;
 private final int prefix;
 private final int mask;
 public CidrBlock(IpAddress network,int prefix){if(network==null)throw new IllegalArgumentException("network required");if(prefix<0||prefix>32)throw new IllegalArgumentException("prefix out of range");this.prefix=prefix;this.mask=prefix==0?0:(int)(0xffffffffL<<(32-prefix));this.network=new IpAddress(network.value()&mask);}
 public static CidrBlock parse(String text){String[] p=text==null?new String[0]:text.trim().split("/");if(p.length==1)return new CidrBlock(IpAddress.parse(p[0]),32);if(p.length!=2)throw new IllegalArgumentException("invalid CIDR");return new CidrBlock(IpAddress.parse(p[0]),Integer.parseInt(p[1]));}
 public boolean contains(IpAddress ip){return ip!=null&&(ip.value()&mask)==network.value();}
 public IpAddress network(){return network;}
 public int prefix(){return prefix;}
 public long size(){return prefix==32?1:1L<<(32-prefix);}
 public boolean overlaps(CidrBlock other){return other!=null&&(contains(other.network)||other.contains(network));}
 public String toString(){return network+"/"+prefix;}
 public boolean equals(Object o){return o instanceof CidrBlock&&((CidrBlock)o).network.equals(network)&&((CidrBlock)o).prefix==prefix;}
 public int hashCode(){return network.hashCode()*31+prefix;}
}
