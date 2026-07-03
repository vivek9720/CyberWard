package core;
public final class IpAddress implements Comparable<IpAddress> {
 private final int value;
 public IpAddress(int value) { this.value = value; }
 public static IpAddress of(int a,int b,int c,int d) { return new IpAddress(((a & 255) << 24) | ((b & 255) << 16) | ((c & 255) << 8) | (d & 255)); }
 public static IpAddress fromBytes(byte[] b,int off) { if (b == null || off < 0 || off + 4 > b.length) throw new IllegalArgumentException("IPv4 bytes missing"); return of(b[off]&255,b[off+1]&255,b[off+2]&255,b[off+3]&255); }
 public static IpAddress parse(String text) { String[] p = text == null ? new String[0] : text.trim().split("\\."); if (p.length != 4) throw new IllegalArgumentException("invalid IPv4 address"); int[] v = new int[4]; for (int i=0;i<4;i++){ if(p[i].isBlank()) throw new IllegalArgumentException("empty IPv4 octet"); v[i]=Integer.parseInt(p[i]); if(v[i]<0||v[i]>255) throw new IllegalArgumentException("IPv4 octet out of range"); } return of(v[0],v[1],v[2],v[3]); }
 public int value(){return value;} public long unsigned(){return value & 0xffffffffL;} public byte[] bytes(){return new byte[]{(byte)(value>>>24),(byte)(value>>>16),(byte)(value>>>8),(byte)value};}
 public boolean isPrivate(){long v=unsigned();return (v>=parse("10.0.0.0").unsigned()&&v<=parse("10.255.255.255").unsigned())||(v>=parse("172.16.0.0").unsigned()&&v<=parse("172.31.255.255").unsigned())||(v>=parse("192.168.0.0").unsigned()&&v<=parse("192.168.255.255").unsigned());}
 public boolean isLoopback(){return (value&0xff000000)==0x7f000000;} public boolean isMulticast(){return (value&0xf0000000)==0xe0000000;}
 public String toString(){return ((value>>>24)&255)+"."+((value>>>16)&255)+"."+((value>>>8)&255)+"."+(value&255);} public int compareTo(IpAddress o){return Long.compare(unsigned(),o.unsigned());} public boolean equals(Object o){return o instanceof IpAddress&&((IpAddress)o).value==value;} public int hashCode(){return value;}
}
