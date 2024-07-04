package Graphs

import org.scalatest.funsuite.AnyFunSuiteLike

class WeightedGraphTest extends AnyFunSuiteLike {

  test("testEdges") {
    val graph = new WeightedGraph(Map("A" -> List(WeightedEdge("B", 1), WeightedEdge("C", 2)), "B" -> List(WeightedEdge("A", 1), WeightedEdge("C", 3)), "C" -> List(WeightedEdge("A", 2))))
    assert(graph.edges == List(("A", "B"), ("A", "C"), ("B", "A"), ("B", "C"), ("C", "A")))
  }

  test("testNeighbours") {
    val graph = new WeightedGraph(Map("A" -> List(WeightedEdge("B", 1), WeightedEdge("C", 2)), "B" -> List(WeightedEdge("A", 1), WeightedEdge("C", 3)), "C" -> List(WeightedEdge("A", 2))))
    assert(graph.neighbors("A") == List("B", "C"))
  }

  test("testRemoveEdge") {
    val graph = new WeightedGraph(Map("A" -> List(WeightedEdge("B", 1), WeightedEdge("C", 2)), "B" -> List(WeightedEdge("A", 1), WeightedEdge("C", 3)), "C" -> List(WeightedEdge("A", 2))))
    val newGraph = graph.removeEdge("A", "B")
    assert(newGraph.edges == List(("A", "C"), ("B", "A"), ("B", "C"), ("C", "A")))
  }

  test("testAddEdgeWithoutWeight") {
    val graph = new WeightedGraph(Map("A" -> List(WeightedEdge("B", 1), WeightedEdge("C", 2)), "B" -> List(WeightedEdge("A", 1), WeightedEdge("C", 3)), "C" -> List(WeightedEdge("A", 2))))
    val newGraph = graph.addEdge("A", "D")
    assert(newGraph.edges == List(("A", "B"), ("A", "C"), ("A", "D"), ("B", "A"), ("B", "C"), ("C", "A")))
  }

  test("testAddEdgeWithWeight") {
    val graph = new WeightedGraph(Map("A" -> List(WeightedEdge("B", 1), WeightedEdge("C", 2)), "B" -> List(WeightedEdge("A", 1), WeightedEdge("C", 3)), "C" -> List(WeightedEdge("A", 2))))
    val newGraph = graph.addEdge("A", WeightedEdge("D", 4))
    assert(newGraph.edges == List(("A", "B"), ("A", "C"), ("A", "D"), ("B", "A"), ("B", "C"), ("C", "A")))
  }

}
