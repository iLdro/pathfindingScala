package Operations

import Graphs.Graph

class CycleDetection {

  def hasCycle[Vertex](graph: Graph[Vertex]): Boolean = {
    var visited = Set[Vertex]()
    var stack = Set[Vertex]()

    def dfsVisit(vertex: Vertex): Boolean = {
      if (stack.contains(vertex)) {
        true
      } else if (!visited.contains(vertex)) {
        visited += vertex
        stack += vertex
        val hasCycle = graph.neighbors(vertex).exists(dfsVisit)
        stack -= vertex
        hasCycle
      } else {
        false
      }
    }

    graph.vertices.exists{ vertex =>
      if (!visited.contains(vertex)) {
        dfsVisit(vertex)
      } else {
        false
      }
    }
  }
  
  

}
