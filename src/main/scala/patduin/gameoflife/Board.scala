package patduin.gameoflife

import scala.math.Ordered
import scala.math.Ordering

class Board(width: Int, height: Int) {

  val cells: Array[Array[Cell]] = Array.tabulate(width, height)((x, y) => new Cell(x, y))

  def makeAlive(x: Int, y: Int) {
    cellAt(x, y).isAlive = true
  }

  def makeDead(x: Int, y: Int) {
    cellAt(x, y).isAlive = false
  }

  def cellAt(x: Int, y: Int): Cell = {
    cells(x)(y)
  }

  def nextRound {
    println("nextRound init:")
    printBoard
    val underPopulatedCells: List[(Int, Int)] = cells(underpopulated);
    val overcrowdedCells: List[(Int, Int)] = cells(overcrowded);
    val reproducingCells: List[(Int, Int)] = cells(reproducing);
    turn(underPopulatedCells, makeDead)
    turn(overcrowdedCells, makeDead)
    turn(reproducingCells, makeAlive)
    println("nextRound end:")
    printBoard
  }

  def underpopulated(x: Int, y: Int) = cellAt(x, y).isAlive && countLiveNeighbours(x, y) < 2
  def overcrowded(x: Int, y: Int) = cellAt(x, y).isAlive && countLiveNeighbours(x, y) > 3
  def reproducing(x: Int, y: Int) = !cellAt(x, y).isAlive && countLiveNeighbours(x, y) == 3

  def turn(cells: List[(Int, Int)], p: (Int,Int) => Unit) = cells.foreach { case (x, y) => p(x, y) }

  private def cells(p: (Int, Int) => Boolean): List[(Int, Int)] = {
    for {
      x <- (0 until width).toList
      y <- 0 until height
      if (p(x, y))
    } yield (x, y)
  }

  private def countLiveNeighbours(x: Int, y: Int): Int = {
    neighbours(x, y).filter(_.isAlive).size
  }

  def neighbours(x: Int, y: Int): List[Cell] = for {
    i <- wrapX(x - 1 to x + 1)
    j <- wrapY(y - 1 to y + 1)
    if (!(i == x && j == y))
  } yield { cellAt(i, j) }

  private def wrapX(xs: Range) = wrap(xs, width)
  private def wrapY(ys: Range) = wrap(ys, height)

  private def wrap(xs: Range, bound: Int) =
    xs.map { x =>
      if (x < 0)
        bound + x
      else {
        x % bound
      }
    }.sorted.toList

  def printBoard = {
    for (y <- (0 until height).reverse) {
      print(y+" ")
      for (x <- 0 until width) {
        if (cellAt(x, y).isAlive)
          print("*")
        else
          print("-")
      }
      println()
    }
    print("  ")
    (0 until width).foreach(print(_))
    println()
  }
}