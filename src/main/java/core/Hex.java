package core;
public final class Hex{
 private static final char[] CHARS="0123456789abcdef".toCharArray();
 private Hex(){}
 public static String encode(byte[] bytes){if(bytes==null)return"";char[] out=new char[bytes.length*2];for(int i=0;i<bytes.length;i++){int v=bytes[i]&255;out[i*2]=CHARS[v>>>4];out[i*2+1]=CHARS[v&15];}return new String(out);}
 public static byte[] decode(String text){if(text==null)return new byte[0];String s=text.replaceAll("[^0-9A-Fa-f]","");if((s.length()&1)==1)s="0"+s;byte[] out=new byte[s.length()/2];for(int i=0;i<out.length;i++)out[i]=(byte)((Character.digit(s.charAt(i*2),16)<<4)|Character.digit(s.charAt(i*2+1),16));return out;}
 public static boolean isHex(String text){return text!=null&&!text.isBlank()&&text.matches("[0-9A-Fa-f]+")&&(text.length()%2==0);}
}
