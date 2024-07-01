package Graphs

import javax.print.attribute.standard.Destination

trait Graph[Vertex] {
  def vertices: Set[Vertex]
  def edges: List[(Vertex, Vertex)]
  def neighbors(vertex: Vertex): List[Vertex]
  def addEdge(source: Vertex, destination: Vertex): Graph[Vertex]
  def removeEdge(source: Vertex, destination: Vertex): Graph[Vertex]
}


