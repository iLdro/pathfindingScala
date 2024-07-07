package Operations

import Graphs.DirectedGraph
import org.scalatest.funsuite.AnyFunSuiteLike

class TopologicalSortingTest extends AnyFunSuiteLike{
  val topSort = new TopologicalSorting()
  test("TopologicalSortingTest") {
    val graph = new DirectedGraph(Map("A" -> List("B", "C")))
    val topologicalSorting = 
  }

}
