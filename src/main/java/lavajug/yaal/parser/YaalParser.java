package lavajug.yaal.parser;

import lavajug.yaal.ir.YaalNode;

import java.util.ArrayList;
import java.util.List;

public class YaalParser {

  public List<YaalNode> parse(String script){
    List<YaalNode> ast = new ArrayList<>();

    for(String line: script.split("\n")){
      String[] symbols = line.split(" ");
      String operator = symbols[0];
      String[] arguments = new String[symbols.length - 1];
      System.arraycopy(symbols, 1, arguments, 0, arguments.length);
      ast.add(new YaalNode(operator, arguments));
    }

    return ast;
  }

}
