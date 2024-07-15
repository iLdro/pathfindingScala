package Operations

import Graphs.{WeightedEdge, WeightedGraph}
import org.scalatest.funsuite.AnyFunSuite

class FloydWarshallAlgorithmTest extends AnyFunSuite {
  test("FloydWarshallAlgorithm computes shortest paths correctly") {
    val graph = WeightedGraph[Int](Map(
      0 -> List(WeightedEdge(1, 5), WeightedEdge(3, 10)),
      1 -> List(WeightedEdge(2, 3)),
      2 -> List(WeightedEdge(3, 1)),
      3 -> Nil
    ))

    val algorithm = new FloydWarshallAlgorithm(graph)
    val shortestPaths = algorithm.computeShortestPaths()
    assert(shortestPaths(0)(2) === 8)
    assert(shortestPaths(0)(3) === 9)
    assert(shortestPaths(1)(3) === 4)
  }
}