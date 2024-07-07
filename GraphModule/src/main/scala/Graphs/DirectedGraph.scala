package Graphs

case class DirectedGraph[Vertex](adjList: Map[Vertex, List[Vertex]]) extends Graph[Vertex] {
    override def vertices: Set[Vertex] = adjList.keySet

    override def edges: List[(Vertex, Vertex)] = adjList.map {
      case (node, neighbours) => {
        neighbours.map(neighbour => (node, neighbour))
      }
    }.flatten.toList

    override def neighbors(vertex: Vertex): List[Vertex] = {
      adjList.getOrElse(vertex, Nil)
    }

    override def addEdge(source: Vertex, destination: Vertex): Graph[Vertex] = {
      val newAdjList = adjList + (source -> (adjList(source) :+ destination))
      new DirectedGraph(newAdjList)
    }

    override def removeEdge(source: Vertex, destination: Vertex): Graph[Vertex] = {
      val newAdjList = adjList + (source -> adjList(source).filter(_ != destination))
      new DirectedGraph(newAdjList)
    }
}
