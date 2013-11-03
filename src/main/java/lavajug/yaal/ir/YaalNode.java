package lavajug.yaal.ir;

public class YaalNode {

  public String operator;
  public String[] arguments;

  public YaalNode(String operator, String[] arguments){
    this.operator = operator;
    this.arguments = new String[arguments.length];
    System.arraycopy(arguments, 0, this.arguments, 0, arguments.length);
  }

}
