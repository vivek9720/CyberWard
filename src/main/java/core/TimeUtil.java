package core;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
public final class TimeUtil{
 private TimeUtil(){}
 public static Instant fromEpochSeconds(long seconds,long micros){return Instant.ofEpochSecond(seconds,Math.max(0,micros)*1000L);}
 public static String iso(Instant instant){return DateTimeFormatter.ISO_INSTANT.format(instant==null?Instant.EPOCH:instant);}
 public static Instant parseFlexible(String text){if(text==null||text.isBlank())return Instant.EPOCH;String s=text.trim();try{return Instant.parse(s);}catch(Exception ignored){}try{return Instant.ofEpochSecond(Long.parseLong(s));}catch(Exception ignored){}try{return LocalDateTime.parse(s,DateTimeFormatter.ISO_LOCAL_DATE_TIME).toInstant(ZoneOffset.UTC);}catch(Exception ignored){}return Instant.EPOCH;}
}
