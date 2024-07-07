package Graphs

import org.scalatest.funsuite.AnyFunSuiteLike

class DirectedGraphTest extends AnyFunSuiteLike {

  val graph = new DirectedGraph(Map("A" -> List("B", "C"), "B" -> List("A"), "C" -> List("A")))

  test("testEdges") {
    assert(graph.edges == List(("A", "B"), ("A", "C"), ("B", "A"), ("C", "A")))
  }

  test("testNeighbours") {
    assert(graph.neighbors("A") == List("B", "C"))
  }

  test("testRemoveEdge") {
    val newGraph = graph.removeEdge("A", "B")
    assert(newGraph.edges == List(("A", "C"), ("B", "A"), ("C", "A")))
  }

  test("testAddEdge") {
    val newGraph = graph.addEdge("A", "D")
    assert(newGraph.edges == List(("A", "B"), ("A", "C"), ("A", "D"), ("B", "A"), ("C", "A")))
  }

  
  test("JSON") {
    object DirectedGraph {
      val graph = new DirectedGraph(Map("A" -> List("B", "C"), "B" -> List("A"), "C" -> List("A")))
      import zio.json._
      implicit def decoder: JsonDecoder[DirectedGraph[String]] = DeriveJsonDecoder.gen[DirectedGraph[String]]
      implicit def encoder: JsonEncoder[DirectedGraph[String]] = DeriveJsonEncoder.gen[DirectedGraph[String]]
    }
  }
}
