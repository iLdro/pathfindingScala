package Graphs

import org.scalatest.funsuite.AnyFunSuiteLike

class UnDirectedGraphTest extends AnyFunSuiteLike {

  val graph = new UnDirectedGraph(Map("A" -> List("B", "C"), "B" -> List("A"), "C" -> List("A")))

  test("testRemoveEdge") {
    val newGraph = graph.removeEdge("A", "B")
    assert(!newGraph.edges.contains(("A", "B")))
    assert(!newGraph.edges.contains(("B", "A")))
  }

  test("testAddEdge") {
    val newGraph = graph.addEdge("A", "D")
    assert(newGraph.edges.contains(("A", "D")))
    assert(newGraph.edges.contains(("D", "A")))
  }
}
