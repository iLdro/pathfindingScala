package Graphs

case class WeightedEdge[Vertex](destination: Vertex, weight: Int)

case class WeightedGraph[Vertex](adjList: Map[Vertex, List[WeightedEdge[Vertex]]]) extends Graph[Vertex] {

  override def vertices: Set[Vertex] = adjList.keySet

  override def edges: List[(Vertex, Vertex)] = adjList.map {
    case (node, neighbours) => {
      neighbours.map(neighbour => (node, neighbour.destination))
    }
  }.flatten.toList

  override def neighbors(node: Vertex): List[Vertex] = {
    adjList.getOrElse(node, Nil).map(_.destination)
  }

  def addEdge(source: Vertex, weightedEdge: WeightedEdge[Vertex]): WeightedGraph[Vertex] = {
    val updatedEdges = adjList.getOrElse(source, Nil) :+ weightedEdge
    new WeightedGraph(adjList + (source -> updatedEdges))
  }

  override def addEdge(source: Vertex, destination: Vertex): Graph[Vertex] = addEdge(source, WeightedEdge(destination, 0))

  override def removeEdge(source: Vertex, destination: Vertex): WeightedGraph[Vertex] = {
    val updatedEdges = adjList.getOrElse(source, Nil).filterNot(_.destination == destination)
    new WeightedGraph(adjList + (source -> updatedEdges))
  }
}
