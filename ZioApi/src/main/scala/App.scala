import zio.Console.*
import Graphs.*
import zio.*
import zio.json.*
import zio.nio.file.Files
import zio.nio.file.Path
import izumi.reflect.dottyreflection.ReflectionUtil.*
import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import scala.jdk.CollectionConverters.*

sealed trait GraphType

case object Directed extends GraphType
case object Undirected extends GraphType
case object Weighted extends GraphType

object GraphType {
  implicit val decoder: JsonDecoder[GraphType] = DeriveJsonDecoder.gen[GraphType]
  implicit val encoder: JsonEncoder[GraphType] = DeriveJsonEncoder.gen[GraphType]
}
case class GraphTypeInfo(`type`: String)

object GraphTypeInfo {
  implicit val decoder: JsonDecoder[GraphTypeInfo] = DeriveJsonDecoder.gen[GraphTypeInfo]
  implicit val encoder: JsonEncoder[GraphTypeInfo] = DeriveJsonEncoder.gen[GraphTypeInfo]
}
object App extends ZIOAppDefault {

  def run: ZIO[Any, Throwable, Unit] =
    for {
      _ <- printLine("Enter the path to the JSON file:")
      path <- readLine
      json <- readFile(path)
      graphType <- ZIO.fromEither(parseGraphType(json))
      _ <- printLine(s"Graph type: $graphType")
      _ <- printLine(s"Read JSON content: $json") // Print the JSON content after reading
      _ <- graphType match {
        case "directed" =>
          val result = json.fromJson[DirectedGraph[String]]
          result.fold(
            err => printLine(s"Error parsing JSON for DirectedGraph[String]: $err").mapError(new Exception(_)),
            graph => graphOperationsMenu(graph)
          )
        case "undirected" =>
          json.fromJson[UnDirectedGraph[String]].fold(
            err => printLine(s"Error parsing JSON for UnDirectedGraph[String]: $err").mapError(new Exception(_)),
            graph => graphOperationsMenu(graph)
          )
        case "weighted" =>
          json.fromJson[WeightedGraph[String]].fold(
            err => printLine(s"Error parsing JSON for WeightedGraph[String]: $err").mapError(new Exception(_)),
            graph => graphOperationsMenu(graph)
          ).orElse(
            json.fromJson[WeightedGraph[Int]].fold(
              err => printLine(s"Error parsing JSON for WeightedGraph[Int]: $err").mapError(new Exception(_)),
              graph => graphOperationsMenu(graph)
            )
          )
        case _ => printLine(s"Unsupported graph type: $graphType").mapError(new Exception(_))
      }
    } yield ()

  def parseGraphType(json: String): Either[Throwable, String] =
    for {
      graphTypeInfo <- json.fromJson[GraphTypeInfo].left.map(new Exception(_))
      graphType = graphTypeInfo.`type`
      _ <- if (graphType == "directed" || graphType == "undirected" || graphType == "weighted") Right(()) else Left(new IllegalArgumentException(s"Unsupported graph type: $graphType"))
    } yield graphType

  def readFile(path: String): ZIO[Any, Throwable, String] =
    for {
      path <- ZIO.attempt(Path(path))
      bytes <- Files.readAllBytes(path)
      content = new String(bytes.toArray, StandardCharsets.UTF_8)
    } yield content

  def graphOperationsMenu[T](graph: Graph[T]): ZIO[Any, Throwable, Unit] = {
    val menu = """
                 |1. Add Edge
                 |2. Remove Edge
                 |3. List Vertices
                 |4. List Edges
                 |5. Save to JSON
                 |6. Exit
                 |Choose an option: """.stripMargin

    def addEdge(graph: Graph[T]): ZIO[Any, Throwable, Graph[T]] =
      for {
        _ <- printLine("Enter source and destination (and weight if applicable, separated by space):")
        input <- readLine
        data = input.split(" ")
        updatedGraph = graph match {
          case g: WeightedGraph[T] if data.length == 3 =>
            g.addEdge(data(0).asInstanceOf[T], WeightedEdge(data(1).asInstanceOf[T], data(2).toInt))
          case g: DirectedGraph[T] if data.length >= 2 =>
            g.addEdge(data(0).asInstanceOf[T], data(1).asInstanceOf[T])
          case g: UnDirectedGraph[T] if data.length >= 2 =>
            g.addEdge(data(0).asInstanceOf[T], data(1).asInstanceOf[T])
          case _ =>
            throw new IllegalArgumentException("Invalid input for the type of graph.")
        }
      } yield updatedGraph

    def removeEdge(graph: Graph[T]): ZIO[Any, Throwable, Graph[T]] =
      for {
        _ <- printLine("Enter source and destination to remove (separated by space):")
        input <- readLine
        data = input.split(" ")
        updatedGraph = graph.removeEdge(data(0).asInstanceOf[T], data(1).asInstanceOf[T])
      } yield updatedGraph

    def listVertices(graph: Graph[T]): ZIO[Any, Throwable, Unit] =
      printLine(s"Vertices: ${graph.vertices.mkString(", ")}")

    def listEdges(graph: Graph[T]): ZIO[Any, Throwable, Unit] =
      printLine(s"Edges: ${graph.edges.mkString(", ")}")

    def saveToJson(graph: Graph[T]): ZIO[Any, Throwable, Unit] =
      for {
        _ <- printLine("Enter the path to save the JSON file")
        path <- readLine
        json <- ZIO.succeed(graph.toJson)
        _ <- Files.writeBytes(Paths.get(path), Chunk.fromArray(json.getBytes(StandardCharsets.UTF_8)))
      } yield ()

    def loop(graph: Graph[T]): ZIO[Any, Throwable, Unit] =
      (for {
        _ <- printLine(menu)
        choice <- readLine
        _ <- choice match {
          case "1" => addEdge(graph).flatMap(loop)
          case "2" => removeEdge(graph).flatMap(loop)
          case "3" => listVertices(graph) *> loop(graph)
          case "4" => listEdges(graph) *> loop(graph)
          case "5" => saveToJson(graph) *> loop(graph)
          case "6" => ZIO.unit
          case _ => printLine("Invalid option, try again.") *> loop(graph)
        }
      }yield ()).catchAll(err => printLine(s"Error: $err") *> loop(graph))

    loop(graph)
}

}