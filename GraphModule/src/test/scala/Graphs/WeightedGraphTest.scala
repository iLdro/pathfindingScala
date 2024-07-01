package Graphs

import org.scalatest.funsuite.AnyFunSuiteLike

class WeightedGraphTest extends AnyFunSuiteLike {

  val graph = new WeightedGraph(Map("A" -> List(WeightedEdge("B", 1), WeightedEdge("C", 2)), "B" -> List(WeightedEdge("A", 1),WeightedEdge("Z", 6)), "C" -> List(WeightedEdge("A", 2))))

  test("testVertices") {
    assert(graph.vertices == Set("A", "B", "C", "Z"))
  }

  test("testEdges") {
    assert(graph.edges == List(("A", "B"), ("A", "C"), ("B", "A"),("B","Z"), ("C", "A")))
  }

  test("testNeighbors") {
    assert(graph.neighbors("A") == List("B", "C"))
    assert(graph.neighbors("B") == List("A"))
    assert(graph.neighbors("C") == List("A"))
  }

  test("testAddEdge") {
    val newGraph = graph.addEdge("B", new WeightedEdge("D", 3))
    assert(newGraph.edges.contains(("B", "D")))
  }

  test("testRemoveEdge") {
    val newGraph = graph.removeEdge("A", "B")
    assert(!newGraph.edges.contains(("A", "B")))
  }

  test("testAddEdgeWithoutWeight") {
    val newGraph = graph.addEdge("B", "D")
    assert(newGraph.edges.contains(("B", "D")))
  }

  test("testRemoveEdgeWithoutWeight") {
    val newGraph = graph.removeEdge("A", "B")
    assert(!newGraph.edges.contains(("A", "B")))
  }

  test("testRemoveEdgeWithWeight") {
    val newGraph = graph.removeEdge("A", "B")
    assert(!newGraph.edges.contains(("A", "B")))
  }

}
