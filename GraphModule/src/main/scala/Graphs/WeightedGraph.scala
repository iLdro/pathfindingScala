package Graphs

import zio.json._

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

object WeightedGraph {

  // Define encoder and decoder for WeightedEdge[String]
  implicit def weightedEdgeEncoderString: JsonEncoder[WeightedEdge[String]] = DeriveJsonEncoder.gen[WeightedEdge[String]]
  implicit def weightedEdgeDecoderString: JsonDecoder[WeightedEdge[String]] = DeriveJsonDecoder.gen[WeightedEdge[String]]

  // Define encoder and decoder for WeightedGraph[String]
  implicit def weightedGraphEncoderString: JsonEncoder[WeightedGraph[String]] = DeriveJsonEncoder.gen[WeightedGraph[String]]
  implicit def weightedGraphDecoderString: JsonDecoder[WeightedGraph[String]] = DeriveJsonDecoder.gen[WeightedGraph[String]]

  // Define encoder and decoder for WeightedEdge[Int]
  implicit def weightedEdgeEncoderInt: JsonEncoder[WeightedEdge[Int]] = DeriveJsonEncoder.gen[WeightedEdge[Int]]
  implicit def weightedEdgeDecoderInt: JsonDecoder[WeightedEdge[Int]] = DeriveJsonDecoder.gen[WeightedEdge[Int]]

  // Define encoder and decoder for WeightedGraph[Int]
  implicit def weightedGraphEncoderInt: JsonEncoder[WeightedGraph[Int]] = DeriveJsonEncoder.gen[WeightedGraph[Int]]
  implicit def weightedGraphDecoderInt: JsonDecoder[WeightedGraph[Int]] = DeriveJsonDecoder.gen[WeightedGraph[Int]]

}