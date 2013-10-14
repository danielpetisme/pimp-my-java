package lavajug.sample1;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;


public class StandardJavaTest {

  @Test
  public void invoke() {
    //Given
    String noun = "Java";
    String verb = " sucks";
    //When
    String sentence = noun.concat(verb);
    //Then
    assertThat(sentence, is("Java sucks"));
  }

  @Test
  public void invokeRecflect() throws Exception {
    //Given
    String noun = "Java";
    String verb = " sucks";

    //When
    Method toUpperMethod = Class.forName("java.lang.String").getMethod("concat", String.class);
    String sentence = (String) toUpperMethod.invoke(noun, verb);

    //Then
    System.out.println(sentence);
    assertThat(sentence, is("Java sucks"));
  }

  @Test
  public void invokeDynamic() throws Throwable {
    //Given
    String noun = "Java";
    String verb = " sucks";

    MethodHandles.Lookup lookup = MethodHandles.lookup();

    Class receiverType = String.class;
    Class returnType = String.class;
    Class argType = String.class;

    MethodHandle concat = lookup.findVirtual(receiverType, "concat", MethodType.methodType(returnType, argType));

    //When
    String sentence = (String) concat.invokeWithArguments(noun, verb);

    //Then
    assertThat(sentence, is("Java sucks"));
  }

  @Test
  public void invokeDynamicCombinator() throws Throwable {

   //Given
    String prefix = ">>";
    String word = "Java";

    MethodHandles.Lookup lookup = MethodHandles.lookup();
    Class receiverType = String.class;
    Class returnType = String.class;
    Class argType = String.class;


    MethodHandle prefixHandler = lookup.findVirtual(receiverType, "concat", MethodType.methodType(returnType, argType)).bindTo(prefix);
    //When
    String sentence = (String) prefixHandler.invokeWithArguments(word);

    //Then
    assertThat(sentence, is(">>Java"));
  }


  }
