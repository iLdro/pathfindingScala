package Operations

import Graphs.{WeightedEdge, WeightedGraph}
import org.scalatest.funsuite.AnyFunSuiteLike

class DijkstraAlgorithmTest extends AnyFunSuiteLike {

    val dijkstraAlgorithm = new DijkstraAlgorithm[Int](WeightedGraph(Map(1 -> List(WeightedEdge(2, 1), WeightedEdge(3, 4)),
      2 -> List(WeightedEdge(3, 2), WeightedEdge(4, 5)),
      3 -> List(WeightedEdge(4, 1)),
      4 -> List())))

    test("testShortestPath") {
      val (dist, prev) = dijkstraAlgorithm.shortestPath(1)
      assert(dist == Map(1 -> 0, 2 -> 1, 3 -> 3, 4 -> 4))
      assert(prev == Map(2 -> 1, 3 -> 2, 4 -> 3))
    }
}
