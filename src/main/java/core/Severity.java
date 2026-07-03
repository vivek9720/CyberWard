package core;
public enum Severity {
 INFO(1), LOW(2), MEDIUM(3), HIGH(4), CRITICAL(5);
 private final int weight;
 Severity(int weight){this.weight=weight;}
 public int weight(){return weight;}
 public static Severity fromText(String text){
  if(text==null||text.isBlank())return INFO;
  String n=text.trim().toUpperCase().replace('-','_');
  for(Severity s:values())if(s.name().equals(n))return s;
  if(n.equals("WARN")||n.equals("WARNING"))return MEDIUM;
  if(n.equals("ERR")||n.equals("ERROR"))return HIGH;
  return INFO;
 }
 public Severity max(Severity other){return other!=null&&other.weight>weight?other:this;}
}
