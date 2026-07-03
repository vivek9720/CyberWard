package core;
import java.util.Optional;
public final class ParseResult<T>{
 private final T value;
 private final Diagnostics diagnostics;
 private ParseResult(T value,Diagnostics diagnostics){this.value=value;this.diagnostics=diagnostics==null?new Diagnostics():diagnostics;}
 public static <T> ParseResult<T> of(T value,Diagnostics diagnostics){return new ParseResult<>(value,diagnostics);}
 public static <T> ParseResult<T> ok(T value){return new ParseResult<>(value,new Diagnostics());}
 public static <T> ParseResult<T> failure(String code,String message){Diagnostics d=new Diagnostics();d.high(code,message);return new ParseResult<>(null,d);}
 public Optional<T> value(){return Optional.ofNullable(value);}
 public T orNull(){return value;}
 public Diagnostics diagnostics(){return diagnostics;}
 public boolean isOk(){return value!=null&&!diagnostics.hasErrors();}
}
