package core;
import java.util.Arrays;
public final class SafeSlice{
 private SafeSlice(){}
 public static byte[] copy(byte[] data,int offset,int length){
  if(data==null)return new byte[0];
  if(offset<0)offset=0;
  if(length<0)length=0;
  if(offset>data.length)return new byte[0];
  int end=Math.min(data.length,offset+length);
  return Arrays.copyOfRange(data,offset,end);
 }
 public static String printable(byte[] data,int offset,int length){
  byte[] slice=copy(data,offset,length);
  StringBuilder sb=new StringBuilder(slice.length);
  for(byte b:slice){int c=b&255;if(c>=32&&c<127)sb.append((char)c);else sb.append('.');}
  return sb.toString();
 }
 public static boolean rangeAvailable(int total,int offset,int length){return total>=0&&offset>=0&&length>=0&&offset<=total&&length<=total-offset;}
}
