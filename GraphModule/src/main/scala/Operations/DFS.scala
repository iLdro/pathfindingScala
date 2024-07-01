package Operations

import Graphs.Graph

object DFS {
  def dfs[Vertex](start: Vertex, graph: Graph[Vertex]): Set[Vertex] = {
    def dfsVisit(vertex: Vertex, visited: Set[Vertex]): Set[Vertex] = {
      if (visited.contains(vertex)){
        visited
      } else {
        val newVisited = visited + vertex
        graph.neighbors(vertex).foldLeft(newVisited)((acc, neighbor) => dfsVisit(neighbor, acc))
      }
    }
    dfsVisit(start, Set())
  }
}