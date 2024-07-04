package Operations

import Graphs.Graph

class BFS {
  def bfs[Vertex](start: Vertex, graph: Graph[Vertex]): Set[Vertex] = {
    def bfsVisit(vertices: Set[Vertex], visited: Set[Vertex]): Set[Vertex] = {
      if (vertices.isEmpty) visited
      else {
        val newVertices = vertices.flatMap(graph.neighbors) -- visited
        bfsVisit(newVertices, visited ++ newVertices)
      }
    }
    bfsVisit(Set(start), Set(start))
  }

}
