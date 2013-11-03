package lavajug.yaal.runtime;

public class YaalClassLoader extends ClassLoader{
  public Class<?> load(byte[] bytecode) {
    return super.defineClass(null, bytecode, 0, bytecode.length);
  }
}
