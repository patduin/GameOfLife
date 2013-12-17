package patduin.gameoflife

class Cell(x:Int, y:Int) {
  var isAlive:Boolean = false
  override def toString = "("+x+","+y+","+isAlive.toString+")"
}