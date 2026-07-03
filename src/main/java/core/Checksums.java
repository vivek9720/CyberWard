package core;
public final class Checksums{
 private Checksums(){}
 public static int internetChecksum(byte[] data){return internetChecksum(data,0,data==null?0:data.length);}
 public static int internetChecksum(byte[] data,int offset,int length){
  if(data==null)return 0xffff;
  long sum=0;
  int start=Math.max(0,offset);
  int end=Math.min(data.length,start+Math.max(0,length));
  for(int i=start;i+1<end;i+=2)sum+=((data[i]&255)<<8)|(data[i+1]&255);
  if(((end-start)&1)==1)sum+=(data[end-1]&255)<<8;
  while((sum>>>16)!=0)sum=(sum&0xffff)+(sum>>>16);
  return (int)(~sum)&0xffff;
 }
 public static long crc32(byte[] data){java.util.zip.CRC32 crc=new java.util.zip.CRC32();if(data!=null)crc.update(data);return crc.getValue();}
}
