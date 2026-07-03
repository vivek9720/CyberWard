package core;
public final class RiskScore{
 private int score;
 public void add(int delta){score=Math.max(0,Math.min(100,score+delta));}
 public int value(){return score;}
 public Severity severity(){if(score>=85)return Severity.CRITICAL;if(score>=65)return Severity.HIGH;if(score>=35)return Severity.MEDIUM;if(score>0)return Severity.LOW;return Severity.INFO;}
 public String label(){return severity().name().toLowerCase(java.util.Locale.ROOT);}
}
