package Graphs

import org.scalatest.funsuite.AnyFunSuiteLike
import Graphs.GraphViz.toDot

class GraphVizTest extends AnyFunSuiteLike {

  test("testToDotDirectedGraph") {
    val graph = DirectedGraph[Int](Map(1 -> List(2)))
    val expected =
      """digraph G {
        |1;
        |2;
        |1 -> 2;
        |}""".stripMargin
  }

  test("testToDotUnDirectedGraph") {
    val graph = UnDirectedGraph[Int](Map(1 -> List(2)))
    val expected =
      """graph G {
        |1;
        |2;
        |1 -- 2;
        |}""".stripMargin
  }

  test("testToDotWeightedGraph") {
    val graph = WeightedGraph[Int](Map(1 -> List(WeightedEdge(2, 3))))
    val expected =
      """digraph G {
        |1;
        |2;
        |1 -> 2 [label=3];
        |}""".stripMargin
  }

}
