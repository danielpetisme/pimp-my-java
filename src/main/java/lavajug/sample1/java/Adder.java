package lavajug.sample1.java;

import java.util.ArrayList;
import java.util.List;

public class Adder {

  public Integer add(Integer a, Integer b ) {
    return a + b;
  }

  public String add(String a, String b ) {
    return a + b;
  }

  public static void main(String[] args) {
    Adder myAdder = new Adder();
    int x = 10;
    int y = 10;

    List<String> jugs = new ArrayList<>();
    String theBestOne = "LavaJUG";

    jugs.add(theBestOne);
    myAdder.add(x, y);

    myAdder.add("The Best JUG is: ",theBestOne);

  }

}
