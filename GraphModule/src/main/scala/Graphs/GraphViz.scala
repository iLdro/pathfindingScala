package Graphs

object GraphViz {
  extension [Vertex](graph: DirectedGraph[Vertex])
    def toDot: String = {
      val vertices = graph.vertices.map(v => s"""$v;""").mkString("\n")
      val edges = graph.adjList.flatMap { case (source, destinations) => destinations.map(destination => s"""$source -> $destination;""") }.mkString("\n")
      s"""digraph G {
         |$vertices
         |$edges
         |}""".stripMargin
    }
    
    
  extension [Vertex](graph : UnDirectedGraph[Vertex])
    def toDot: String = {
      val vertices = graph.vertices.map(v => s"""$v;""").mkString("\n")
      val edges = graph.edges.flatMap { case (source, destination) =>
        if (source.hashCode() <= destination.hashCode()) Some(s"""$source -- $destination;""")
        else None
      }.toSet.mkString("\n")
      s"""graph G {
         |$vertices
         |$edges
         |}""".stripMargin
    }

  extension [Vertex](graph : WeightedGraph[Vertex])
    def toDot: String = {
      val allVertices = graph.vertices ++ graph.adjList.values.flatten.map(_.destination).toSet
      val verticesStr = allVertices.map(v => s"$v;").mkString("\n")
      val edgesStr = graph.adjList.flatMap { case (source, destinations) =>
        destinations.map(destination => s"$source -> ${destination.destination} [label=${destination.weight}];")
      }.mkString("\n")
      s"""digraph G {
         |$verticesStr
         |$edgesStr
         |}""".stripMargin
    }
}
