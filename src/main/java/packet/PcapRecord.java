package packet;
import java.time.Instant;
public final class PcapRecord{private final Instant timestamp;private final int capturedLength;private final int originalLength;private final byte[] data;public PcapRecord(Instant timestamp,int capturedLength,int originalLength,byte[] data){this.timestamp=timestamp;this.capturedLength=capturedLength;this.originalLength=originalLength;this.data=data==null?new byte[0]:data;}public Instant timestamp(){return timestamp;}public int capturedLength(){return capturedLength;}public int originalLength(){return originalLength;}public byte[] data(){return data;}}
