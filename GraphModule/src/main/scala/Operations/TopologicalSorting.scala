package Operations

import Graphs.Graph

class TopologicalSorting {
  def topologicalSort[Vertex](graph: Graph[Vertex]): Set[Vertex] = {
    var visited = Set[Vertex]()
    var stack = List[Vertex]()

    def dfsVisit(vertex: Vertex): Unit = {
      if (!visited.contains(vertex)) {
        visited += vertex
        graph.neighbors(vertex).foreach(dfsVisit)
        stack = vertex :: stack
      }
    }

    graph.vertices.foreach{ vertex =>
      if (!visited.contains(vertex)) {
        dfsVisit(vertex)
      }
    }

    stack.toSet
  }
}
