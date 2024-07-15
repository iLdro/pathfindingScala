package Operations


import Graphs.DirectedGraph
import org.scalatest.funsuite.AnyFunSuiteLike

class TopologicalSortingTest extends AnyFunSuiteLike {

  val topologicalSorting = new TopologicalSorting[Int]()

  test("testTopologicalSort") {
    val graph = DirectedGraph(Map(1 -> List(2, 3), 2 -> List(3), 3 -> List(4), 4 -> List()))
    val topologicalSort = topologicalSorting.topologicalSort(graph)
    assert(topologicalSort == List(1, 2, 3, 4))
  }

  test("testTopologicalSortDisconnected") {
    val graph = DirectedGraph(Map(1 -> List(2, 3), 2 -> List(3), 3 -> List(4), 4 -> List(), 5 -> List(6), 6 -> List(7), 7 -> List()))
    val topologicalSort = topologicalSorting.topologicalSort(graph)
    assert(topologicalSort == List(1, 2, 3, 4, 5, 6, 7))
  }
}