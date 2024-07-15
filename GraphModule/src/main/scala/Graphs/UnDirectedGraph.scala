package Graphs

case class UnDirectedGraph[Vertex](adjList: Map[Vertex, List[Vertex]]) extends Graph[Vertex] {
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

object UnDirectedGraph {

  import zio.json._

  implicit def decoderString: JsonDecoder[UnDirectedGraph[String]] = DeriveJsonDecoder.gen[UnDirectedGraph[String]]

  implicit def encoderString: JsonEncoder[UnDirectedGraph[String]] = DeriveJsonEncoder.gen[UnDirectedGraph[String]]

  implicit def decoderInt: JsonDecoder[UnDirectedGraph[Int]] = DeriveJsonDecoder.gen[UnDirectedGraph[Int]]

  implicit def encoderInt: JsonEncoder[UnDirectedGraph[Int]] = DeriveJsonEncoder.gen[UnDirectedGraph[Int]]
}
