package Operations

import Graphs.{WeightedEdge, WeightedGraph}

import scala.collection.mutable

class DijkstraAlgorithm[Vertex](graph: WeightedGraph[Vertex])  {
  def shortestPath(source: Vertex): (Map[Vertex, Int], Map[Vertex, Vertex]) = {
    val dist = mutable.Map[Vertex, Int]().withDefaultValue(Int.MaxValue)
    val prev = mutable.Map[Vertex, Vertex]()
    val pq = mutable.PriorityQueue[(Int, Vertex)]()(Ordering.by(-_._1))

    dist(source) = 0
    pq.enqueue((0, source))

    while (pq.nonEmpty) {
      val (_, u) = pq.dequeue()

      graph.adjList.getOrElse(u, List()).foreach { case WeightedEdge(v, weight) =>
        val alt = dist(u) + weight
        if (alt < dist(v)) {
          dist(v) = alt
          prev(v) = u
          pq.enqueue((alt, v))
        }
      }
    }

    (dist.toMap, prev.toMap)
  }
}
