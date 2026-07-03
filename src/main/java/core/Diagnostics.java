package core;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public final class Diagnostics {
 private final List<Issue> issues=new ArrayList<>();
 public void add(Severity severity,String code,String message){issues.add(new Issue(severity,code,message));}
 public void add(Severity severity,String code,String message,int offset){issues.add(new Issue(severity,code,message,offset));}
 public void info(String code,String message){add(Severity.INFO,code,message);}
 public void low(String code,String message){add(Severity.LOW,code,message);}
 public void low(String code,String message,int offset){add(Severity.LOW,code,message,offset);}
 public void medium(String code,String message){add(Severity.MEDIUM,code,message);}
 public void medium(String code,String message,int offset){add(Severity.MEDIUM,code,message,offset);}
 public void high(String code,String message){add(Severity.HIGH,code,message);}
 public void high(String code,String message,int offset){add(Severity.HIGH,code,message,offset);}
 public void critical(String code,String message){add(Severity.CRITICAL,code,message);}
 public boolean hasErrors(){return highestSeverity().weight()>=Severity.HIGH.weight();}
 public boolean isEmpty(){return issues.isEmpty();}
 public int size(){return issues.size();}
 public List<Issue> issues(){return Collections.unmodifiableList(issues);}
 public Severity highestSeverity(){Severity best=Severity.INFO;for(Issue i:issues)best=best.max(i.severity());return best;}
 public void merge(Diagnostics other){if(other!=null)issues.addAll(other.issues);}
 public String render(){StringBuilder sb=new StringBuilder();for(Issue i:issues)sb.append(i.format()).append(System.lineSeparator());return sb.toString();}
}
