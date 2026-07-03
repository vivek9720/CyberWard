package core;
public final class PortRange{
 private final int start;
 private final int end;
 public PortRange(int start,int end){if(start<0||end>65535||start>end)throw new IllegalArgumentException("invalid port range");this.start=start;this.end=end;}
 public static PortRange any(){return new PortRange(0,65535);}
 public static PortRange parse(String text){String s=text==null?"any":text.trim().toLowerCase();if(s.equals("any")||s.equals("*"))return any();if(s.contains(":")){String[] p=s.split(":",-1);int a=p[0].isBlank()?0:Integer.parseInt(p[0]);int b=p.length<2||p[1].isBlank()?65535:Integer.parseInt(p[1]);return new PortRange(a,b);}if(s.contains("-")){String[] p=s.split("-",2);return new PortRange(Integer.parseInt(p[0]),Integer.parseInt(p[1]));}int v=Integer.parseInt(s);return new PortRange(v,v);}
 public boolean contains(int port){return port>=start&&port<=end;}
 public boolean overlaps(PortRange other){return other!=null&&start<=other.end&&other.start<=end;}
 public int start(){return start;}
 public int end(){return end;}
 public String toString(){if(start==0&&end==65535)return"any";if(start==end)return Integer.toString(start);return start+":"+end;}
}
