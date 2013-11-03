package lavajug.sample2;

import org.junit.Test;

import java.lang.invoke.*;
import java.text.MessageFormat;

import static java.lang.invoke.MethodType.methodType;

public class BootStrapping {

  public static String  greeterFormat = "Hello {0} {1}!";

  public static CallSite bootstrap(MethodHandles.Lookup lookup, String name, MethodType type) throws Throwable {
    MethodHandle target = lookup.findStatic(MessageFormat.class, "format",
            methodType(String.class, String.class, Object[].class))
            .bindTo(greeterFormat)
            .asCollector(String[].class, 2)
            .asType(type);
    return new ConstantCallSite(target);
  }

  @Test
  public void test() throws Throwable{

    CallSite callSite = bootstrap(
            MethodHandles.lookup(),
            "myGreeter",
            methodType(String.class, String.class, String.class)
    );

    String greet = (String) callSite.dynamicInvoker().invokeWithArguments("John", "Doe");

    assert greet.equals("Hello John Doe!");
  }
}