package lavajug.sample1.groovy

class Adder {

  def add(a, b) {
    return a + b
  }

  def main(args) {
    def myAdder = new Adder()
    def x = 10
    def y = 10

    def jugs = []
    def theBestOne = "LavaJUG";
    jugs.add(theBestOne)

    myAdder.add(x, y)
    myAdder.add("The Best JUG is: ",theBestOne)

  }
}
