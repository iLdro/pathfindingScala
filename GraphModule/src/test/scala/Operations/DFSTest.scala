package Operations

import Graphs.UnDirectedGraph
import org.scalatest.funsuite.AnyFunSuiteLike

class DFSTest extends AnyFunSuiteLike {
  val DFS = new DFS()
  test("DFSTest") {
    val graph = new UnDirectedGraph(Map("A" -> List("B", "C", "D"), "B" -> List("A"), "C" -> List("D", "E"), "D" -> List("A", "C"), "E" -> List("C")))
    val dfs = DFS.dfs("A", graph, println)
    assert(dfs == Set("A", "B", "C", "E", "D"))
  }

  test("longGraphDFS"){
    val graph = new UnDirectedGraph(Map("A" -> List("B", "C", "D"),
                                        "B" -> List("A", "E"),
                                        "C" -> List("A", "F", "G"),
                                        "D" -> List("A"),
                                        "E" -> List("B", "H"),
                                        "F" -> List("C", "I"),
                                        "G" -> List("C"),
                                        "H" -> List("E"),
                                        "I" -> List("F")))
    val dfs = DFS.dfs("A", graph, println)
    assert(dfs == Set("A", "B", "E", "H", "C", "F", "I", "G", "D"))
  }
}