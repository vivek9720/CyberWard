package core;
import java.util.Objects;
public final class Issue {
 private final Severity severity;
 private final String code;
 private final String message;
 private final int offset;
 public Issue(Severity severity,String code,String message){this(severity,code,message,-1);}
 public Issue(Severity severity,String code,String message,int offset){
  this.severity=severity==null?Severity.INFO:severity;
  this.code=code==null?"general":code;
  this.message=message==null?"":message;
  this.offset=offset;
 }
 public Severity severity(){return severity;}
 public String code(){return code;}
 public String message(){return message;}
 public int offset(){return offset;}
 public String format(){return offset>=0?severity+" "+code+" @"+offset+": "+message:severity+" "+code+": "+message;}
 public String toString(){return format();}
 public boolean equals(Object o){if(!(o instanceof Issue))return false;Issue i=(Issue)o;return severity==i.severity&&offset==i.offset&&Objects.equals(code,i.code)&&Objects.equals(message,i.message);}
 public int hashCode(){return Objects.hash(severity,code,message,offset);}
}
