package patduin.gameoflife

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BoardSuite extends FlatSpec with ShouldMatchers {
//1) Any live cell with fewer than two live neighbours dies, as if caused by under-population.
//2) Any live cell with two or three live neighbours lives on to the next generation.
//3) Any live cell with more than three live neighbours dies, as if by overcrowding.
//4) any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 
// board should make cell alive
// board should make cell dead
// board should kill live cells with < 2 live neighbours
// board should keep live cell with  2 || 3 live neighbours
// board should kill live cell with > 3 live neighbours
// board should make a cell alive with 3 live neighbours
// board should wrap
  
  "the board" should "make a cell alive" in {
    val board:Board = new Board(8,10)
    board.cellAt(2,3).isAlive should be (false) 
    board.makeAlive(2,3)
    board.cellAt(2,3).isAlive should be (true)
  }
  
  "the board" should "kill a cell" in {
    val board:Board = new Board(8,10)
    board.makeAlive(2,3)
    board.makeDead(2,3)
    board.cellAt(2,3).isAlive should be (false)
  }

  "the board" should "kill a cell with less then two alive neighbours" in {
    val board:Board = new Board(8,10)
    board.makeAlive(2,3)
    board.makeAlive(2,4)
    board.nextRound
    board.cellAt(2,3).isAlive should be (false)
  }
  
  "the board" should "kill a cell with more then three alive neighbours" in {
    val board:Board = new Board(8,10)
    board.makeAlive(2,3)
    board.makeAlive(2,4)
    board.makeAlive(2,5)
    board.makeAlive(3,4)
    board.makeAlive(3,5)
    board.nextRound
    board.cellAt(2,4).isAlive should be (false)
  }
  
  "the board" should "make a cell alive with three alive neighbours" in {
    val board:Board = new Board(8,10)
    board.makeAlive(2,3)
    board.makeAlive(2,5)
    board.makeAlive(3,4)
    board.nextRound
    board.cellAt(2,4).isAlive should be (true)
  }
  
  "the board" should "be a stable 'blinker'" in {
    val board:Board = new Board(8,10)
    board.makeAlive(2,3)
    board.makeAlive(2,4)
    board.makeAlive(2,5)
    board.nextRound
    board.cellAt(1,4).isAlive should be (true)
    board.cellAt(2,4).isAlive should be (true)
    board.cellAt(3,4).isAlive should be (true)
    board.nextRound
    board.cellAt(2,3).isAlive should be (true)
    board.cellAt(2,4).isAlive should be (true)
    board.cellAt(2,5).isAlive should be (true)
  }
}