package Graphs

object GraphViz {
  extension [Vertex](graph : DirectedGraph[Vertex])
    def toDot: String = {
      val vertices = graph.vertices.map(v => s"""$v;""").mkString("\n")
      val edges = graph.edges.map { case (source, destination) => s"""$source -> $destination;""" }.mkString("\n")
      s"""digraph G {
         |$vertices
         |$edges
         |}""".stripMargin
    }
    
  extension [Vertex](graph : UnDirectedGraph[Vertex])
    def toDot: String = {
      val vertices = graph.vertices.map(v => s"""$v;""").mkString("\n")
      val edges = graph.edges.map { case (source, destination) => s"""$source -- $destination;""" }.mkString("\n")
      s"""graph G {
         |$vertices
         |$edges
         |}""".stripMargin
    }
    
  extension [Vertex](graph : WeightedGraph[Vertex])
    def toDot: String = {
      val vertices = graph.vertices.map(v => s"""$v;""").mkString("\n")
      val edges = graph.adjList.flatMap { case (source, destinations) => destinations.map { case WeightedEdge(destination, weight) => s"""$source -> $destination [label=$weight];""" } }.mkString("\n")
      s"""digraph G {
         |$vertices
         |$edges
         |}""".stripMargin
    }
    

}
