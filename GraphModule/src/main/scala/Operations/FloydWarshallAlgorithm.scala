package Operations

import Graphs.WeightedGraph

class FloydWarshallAlgorithm[Vertex](graph: WeightedGraph[Vertex]) {
  private val vertices: IndexedSeq[Vertex] = graph.vertices.toIndexedSeq
  private val vertexIndex: Map[Vertex, Int] = vertices.zipWithIndex.toMap
  private val size: Int = vertices.size
  private var dist: Array[Array[Int]] = Array.fill(size, size)(Int.MaxValue)
  
  for (i <- 0 until size) {
    dist(i)(i) = 0
    for (edge <- graph.adjList.getOrElse(vertices(i), Nil)) {
      val j = vertexIndex(edge.destination)
      dist(i)(j) = edge.weight
    }
  }

  def computeShortestPaths(): Array[Array[Int]] = {
    for (k <- 0 until size; i <- 0 until size; j <- 0 until size) {
      if (dist(i)(k) < Int.MaxValue && dist(k)(j) < Int.MaxValue) {
        dist(i)(j) = Math.min(dist(i)(j), dist(i)(k) + dist(k)(j))
      }
    }
    dist
  }
}