package core;
public final class Endian{
 private Endian(){}
 public static int u16be(byte[] d,int o){require(d,o,2);return ((d[o]&255)<<8)|(d[o+1]&255);}
 public static int u16le(byte[] d,int o){require(d,o,2);return (d[o]&255)|((d[o+1]&255)<<8);}
 public static long u32be(byte[] d,int o){require(d,o,4);return ((long)(d[o]&255)<<24)|((long)(d[o+1]&255)<<16)|((long)(d[o+2]&255)<<8)|(long)(d[o+3]&255);}
 public static long u32le(byte[] d,int o){require(d,o,4);return (long)(d[o]&255)|((long)(d[o+1]&255)<<8)|((long)(d[o+2]&255)<<16)|((long)(d[o+3]&255)<<24);}
 public static byte[] u16beBytes(int v){return new byte[]{(byte)((v>>>8)&255),(byte)(v&255)};}
 public static byte[] u32beBytes(long v){return new byte[]{(byte)((v>>>24)&255),(byte)((v>>>16)&255),(byte)((v>>>8)&255),(byte)(v&255)};}
 private static void require(byte[] d,int o,int l){if(d==null||o<0||l<0||o+l>d.length)throw new IllegalArgumentException("out of range");}
}
