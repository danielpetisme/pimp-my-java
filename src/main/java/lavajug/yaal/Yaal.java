package lavajug.yaal;

import lavajug.yaal.compiler.YaalCompiler;
import lavajug.yaal.ir.YaalNode;
import lavajug.yaal.parser.YaalParser;
import lavajug.yaal.runtime.YaalClassLoader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.invoke.MethodHandles.publicLookup;
import static java.lang.invoke.MethodType.methodType;

public class Yaal {

  public static String NAMESPACE = "lavajug/yaal/";
  public static String OUTPUT_DIR = "target/classes/";


  public static void main(String[] args) throws Throwable {
   String yaalScript = readFile("src/main/resources/greeter.yaal");

    String unitName = NAMESPACE + "Greeter";

    List<YaalNode> ast = new YaalParser().parse(yaalScript);
    byte[] bytecode = new YaalCompiler().compile(ast, unitName);

    toClassFile(bytecode, unitName);

    YaalClassLoader loader = new YaalClassLoader();
    Class klass = loader.load(bytecode);
    MethodHandle main = publicLookup().findStatic(klass, "main", methodType(void.class, String[].class));
    main.invoke(args);

  }

  static void toClassFile(byte[] bytes, String unitName) throws IOException {
    FileOutputStream classOut = new FileOutputStream(OUTPUT_DIR + unitName + ".class");
    classOut.write(bytes);
    classOut.close();


  }
  static String readFile(String path)
          throws IOException
  {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return Charset.defaultCharset().decode(ByteBuffer.wrap(encoded)).toString();
  }

}
