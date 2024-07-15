package Operations

import Graphs.*
import org.scalatest.funsuite.AnyFunSuiteLike

class BFSTest extends AnyFunSuiteLike {
  val BFS = new BFS()
  test("testBfs") {
    val graph = new UnDirectedGraph(Map("A" -> List("B", "C"), "B" -> List("A"), "C" -> List("A")))
    val bfs = BFS.bfs("A", graph)
    assert(bfs == Set("A", "B", "C"))
  }
  
  test("testBfsDisconnected") {
    val graph = new UnDirectedGraph(Map("A" -> List("B", "C"), "B" -> List("A"), "C" -> List("A"), "D" -> List("E"), "E" -> List("D")))
    val bfs = BFS.bfs("A", graph)
    assert(bfs == Set("A", "B", "C"))
  }
  
  test("testBfsDisconnected2") {
    val graph = new UnDirectedGraph(Map("A" -> List("B", "C"), "B" -> List("A"), "C" -> List("A"), "D" -> List("E"), "E" -> List("D")))
    val bfs = BFS.bfs("D", graph)
    assert(bfs == Set("D", "E"))
  }
}
