package Graphs

case class UnDirectedGraph[Vertex](adjList: Map[Vertex, List[Vertex]]) extends DirectedGraph[Vertex](adjList) {
  override def addEdge(source: Vertex, destination: Vertex): Graph[Vertex] = {
    val updatedSourceAdjList = adjList + (source -> (adjList.getOrElse(source, Nil) :+ destination))
    val updatedDestAdjList = updatedSourceAdjList + (destination -> (updatedSourceAdjList.getOrElse(destination, Nil) :+ source))
    UnDirectedGraph(updatedDestAdjList)
  }

  override def removeEdge(source: Vertex, destination: Vertex): Graph[Vertex] = {
    val updatedSourceAdjList = adjList + (source -> adjList.getOrElse(source, Nil).filter(_ != destination))
    val updatedDestAdjList = updatedSourceAdjList + (destination -> updatedSourceAdjList.getOrElse(destination, Nil).filter(_ != source))
    UnDirectedGraph(updatedDestAdjList)
  }
}