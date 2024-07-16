import zio.json._

object TestJson extends App {

  case class DirectedGraph[Vertex](adjList: Map[Vertex, List[Vertex]])

  object DirectedGraph {
    implicit val decoder: JsonDecoder[DirectedGraph[String]] = DeriveJsonDecoder.gen[DirectedGraph[String]]
    implicit val encoder: JsonEncoder[DirectedGraph[String]] = DeriveJsonEncoder.gen[DirectedGraph[String]]
  }

  val jsonString =
    """
      |{
      |  "type": "directed",
      |  "adjList": {
      |    "A": ["B", "C"],
      |    "B": ["C"],
      |    "C": ["A"],
      |    "D": ["C"]
      |  }
      |}
    """.stripMargin

  val result = jsonString.fromJson[DirectedGraph[String]]

  result match {
    case Left(error) => println(s"Failed to parse JSON: $error")
    case Right(graph) => println(s"Successfully parsed: $graph")
  }
}
