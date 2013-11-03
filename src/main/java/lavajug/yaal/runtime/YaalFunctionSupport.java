package lavajug.yaal.runtime;

import lavajug.yaal.Predefined;

import java.lang.invoke.*;
import java.lang.reflect.Method;

public class YaalFunctionSupport {

  public static CallSite bootstrap(MethodHandles.Lookup lookup, String name, MethodType type) throws NoSuchMethodException, IllegalAccessException {

    Method predefinedMethod = findMethodByName(Predefined.class, name);

    MethodHandle target = lookup.unreflect(predefinedMethod).asType(type);

    return new ConstantCallSite(target);
  }


  private static Method findMethodByName(Class klass, String name){
    for (Method it : klass.getDeclaredMethods()){
      if(it.getName().equals(name)) {
        return it;
      }
    }

    return null;
  }
}
