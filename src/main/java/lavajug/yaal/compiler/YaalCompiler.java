package lavajug.yaal.compiler;

import lavajug.yaal.ir.YaalNode;
import org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.ClassWriter.*;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.List;

public class YaalCompiler {

  public byte[] compile(List<YaalNode> nodes, String unitName) throws IOException {

    ClassWriter writer = new ClassWriter(COMPUTE_MAXS | COMPUTE_FRAMES);
    writer.visit(V1_7, ACC_PUBLIC, unitName, null, "java/lang/Object", null);

    MethodVisitor mv = writer.visitMethod(ACC_STATIC | ACC_PUBLIC, "main", "([Ljava/lang/String;)V", null, null);

    for (YaalNode node : nodes) {
      for(String argument : node.arguments) {
        mv.visitLdcInsn(argument);
      }

      String bootstrapOwner =  "lavajug/yaal/runtime/YaalFunctionSupport";
      String bootstrapMethod = "bootstrap";
      String desc = MethodType.methodType(
              CallSite.class,
              MethodHandles.Lookup.class,
              String.class,
              MethodType.class).toMethodDescriptorString();

      Handle bsm = new Handle(H_INVOKESTATIC, bootstrapOwner, bootstrapMethod, desc);

      mv.visitInvokeDynamicInsn(node.operator, MethodType.genericMethodType(node.arguments.length).toMethodDescriptorString(), bsm);

    }

    mv.visitInsn(RETURN);
    mv.visitMaxs(666, 666);
    mv.visitEnd();
    writer.visitEnd();

    return writer.toByteArray();
  }

}
