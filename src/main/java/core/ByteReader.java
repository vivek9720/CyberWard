package core;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
public final class ByteReader{
 private final byte[] data;
 private int position;
 public ByteReader(byte[] data){this.data=data==null?new byte[0]:data;}
 public int position(){return position;}
 public int length(){return data.length;}
 public int remaining(){return Math.max(0,data.length-position);}
 public boolean has(int count){return count>=0&&remaining()>=count;}
 public void seek(int p){if(p<0||p>data.length)throw new IllegalArgumentException("position out of range");position=p;}
 public int u8(){require(1);return data[position++]&255;}
 public int u16be(){require(2);int v=Endian.u16be(data,position);position+=2;return v;}
 public int u16le(){require(2);int v=Endian.u16le(data,position);position+=2;return v;}
 public long u32be(){require(4);long v=Endian.u32be(data,position);position+=4;return v;}
 public long u32le(){require(4);long v=Endian.u32le(data,position);position+=4;return v;}
 public byte[] bytes(int count){require(count);byte[] out=Arrays.copyOfRange(data,position,position+count);position+=count;return out;}
 public byte[] remainingBytes(){return bytes(remaining());}
 public byte[] peekBytes(int count){if(!has(count))return new byte[0];return Arrays.copyOfRange(data,position,position+count);}
 public int peekU8(){require(1);return data[position]&255;}
 public String ascii(int count){return new String(bytes(count),StandardCharsets.US_ASCII);}
 private void require(int count){if(!has(count))throw new IllegalArgumentException("truncated input at offset "+position+" need "+count+" bytes");}
}
