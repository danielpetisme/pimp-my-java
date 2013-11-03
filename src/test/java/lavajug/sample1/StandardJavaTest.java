package lavajug.sample1;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import static java.lang.invoke.MethodType.methodType;
import static java.lang.invoke.MethodHandles.Lookup;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;


public class StandardJavaTest {

  @Test
  public void simpleMethodHandleTest() throws Throwable {

    String word = "Java";

    MethodHandles.Lookup lookup = MethodHandles.lookup();

    Class receiverType = String.class;
    Class returnType = char.class;
    Class paramType = int.class;

    MethodHandle charAtHandle = lookup.findVirtual(
            receiverType,
            "charAt",
            MethodType.methodType(returnType, paramType)
    );

    char charAt0 = (char) charAtHandle.invokeWithArguments(word, 0);

    assert charAt0 == 'J';
  }


  @Test
  public void unreflectTest() throws Throwable {

    Lookup lookup = MethodHandles.lookup();

    Method formatMethod = MessageFormat.class.getMethod(
            "format",
            String.class,
            Object[].class
    );

    MethodHandle greeterHandle = lookup.unreflect(formatMethod);

    assert greeterHandle.type() == methodType(
            String.class,
            String.class,
            Object[].class
    );


    //Partial application
    String greeterFormat = "{0} {1} {2}{3}";

    greeterHandle = greeterHandle.bindTo(greeterFormat);

    assert greeterHandle.type() == methodType(
            String.class,
            Object[].class
    );

    //Reshape the Method Descriptor
    greeterHandle = greeterHandle.asCollector(Object[].class, 4);

    assert greeterHandle.type() == methodType(
            String.class,
            Object.class,
            Object.class,
            Object.class,
            Object.class
    );

    String greet1 = (String) greeterHandle.invokeWithArguments(
            "Hello",
            "John",
            "Doe",
            "!"
    );

    assert greet1.equals("Hello John Doe!");


    //Arguments manipulation
    greeterHandle = MethodHandles.insertArguments(
            greeterHandle,
            greeterHandle.type().parameterCount() - 1,
            "!"
    );

    assert greeterHandle.type() == methodType(
            String.class,
            Object.class,
            Object.class,
            Object.class
    );

    String greet2 = (String) greeterHandle.invokeWithArguments(
            "Hello",
            "John",
            "Doe"
    );

    assert greet2.equals("Hello John Doe!");


    //Chaining
    Method toUpperMethod = String.class.getMethod("toUpperCase");
    MethodHandle toUpperHandle = lookup.unreflect(toUpperMethod);

    greeterHandle = MethodHandles.filterReturnValue(
            greeterHandle,
            toUpperHandle
    );

    String greet3 = (String) greeterHandle.invokeWithArguments(
            "Hello",
            "John",
            "Doe"
    );

    assert greet3.equals("HELLO JOHN DOE!");




  }


  @Test
  public void unreflectTestFun() throws Throwable {

    String greeterFormat = "Hello {0} {1}{2}";
    String suffix = "!";
    Lookup lookup = MethodHandles.lookup();

    Method formatMethod = MessageFormat.class.getMethod(
            "format",
            String.class,
            Object[].class
    );

    MethodHandle greeterHandle = lookup.unreflect(formatMethod);


    greeterHandle = greeterHandle.
            bindTo(greeterFormat). //Curry
            asCollector(Object[].class, 3); //become a 3 args method

    //Arguments manipulation
    greeterHandle = MethodHandles.insertArguments(
            greeterHandle,
            greeterHandle.type().parameterCount() - 1,
            suffix
    );

    //Combining
    Method toUpperMethod = String.class.getMethod("toUpperCase");
    MethodHandle toUpperHandle = lookup.unreflect(toUpperMethod);

    greeterHandle = MethodHandles.filterReturnValue(
            greeterHandle,
            toUpperHandle
    );

    //Test
    String greet = (String) greeterHandle.invokeWithArguments(
            "John",
            "Doe"
    );

    assert greet.equals("HELLO JOHN DOE!");




  }
}