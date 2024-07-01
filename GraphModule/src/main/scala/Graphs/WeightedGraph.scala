package Graphs

case class WeightedEdge[Vertex](destination: Vertex, weight: Int)

class WeightedGraph[Vertex](adjList: Map[Vertex, List[WeightedEdge[Vertex]]]) extends Graph[Vertex] {

  override def vertices: Set[Vertex] = adjList.keySet

  override def edges: List[(Vertex, Vertex)] = adjList.flatMap {
    case (node, edges) =>
      edges.map(edge => (node, edge.destination)) ++
        // Add missing edges where the destination is not a key in the adjacency list
        edges.collect {
          case WeightedEdge(destination, _) if !adjList.contains(destination) =>
            (destination, node)
        }
  }.toList.distinct

  override def neighbors(node: Vertex): List[Vertex] = {
    adjList.getOrElse(node, Nil).map(_.destination)
  }

  def addEdge(source: Vertex, weightedEdge: WeightedEdge[Vertex]): Graph[Vertex] = {
    val newAdjList = adjList + (source -> (adjList.getOrElse(source, Nil) :+ weightedEdge))
    new WeightedGraph(newAdjList)
  }

  override def addEdge(source: Vertex, destination: Vertex): Graph[Vertex] = addEdge(source, WeightedEdge(destination, 0))

  override def removeEdge(source: Vertex, destination: Vertex): Graph[Vertex] = {
    val newAdjList = adjList + (source -> adjList.getOrElse(source, Nil).filter(_.destination != destination))
    new WeightedGraph(newAdjList)
  }
}
