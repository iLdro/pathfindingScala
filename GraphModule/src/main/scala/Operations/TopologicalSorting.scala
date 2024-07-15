package Operations

import Graphs.Graph

class TopologicalSorting[Vertex] {

  def topologicalSort(graph: Graph[Vertex]): List[Vertex] = {
    var visited = Set[Vertex]()
    var stack = List[Vertex]()

    def dfs(vertex: Vertex): Unit = {
      visited += vertex
      for (neighbor <- graph.neighbors(vertex)) {
        if (!visited.contains(neighbor)) {
          dfs(neighbor)
        }
      }
      stack = vertex :: stack
    }

    for (vertex <- graph.vertices) {
      if (!visited.contains(vertex)) {
        dfs(vertex)
      }
    }

    stack
  }
}