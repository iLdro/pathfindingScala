import zio.Console.*
import Graphs.*
import Operations.*
import zio.*
import zio.json.*
import zio.nio.file.Files
import zio.nio.file.Path
import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import scala.jdk.CollectionConverters.*
import zio.{ZIO, UIO, RIO}

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
            graph => graphOperationsMenu(graph, graphType)
          )
        case "undirected" =>
          json.fromJson[UnDirectedGraph[String]].fold(
            err => printLine(s"Error parsing JSON for UnDirectedGraph[String]: $err").mapError(new Exception(_)),
            graph => graphOperationsMenu(graph, graphType)
          )
        case "weighted" =>
          json.fromJson[WeightedGraph[String]].fold(
            err => printLine(s"Error parsing JSON for WeightedGraph[String]: $err").mapError(new Exception(_)),
            graph => graphOperationsMenu(graph, graphType)
          ).orElse(
            json.fromJson[WeightedGraph[Int]].fold(
              err => printLine(s"Error parsing JSON for WeightedGraph[Int]: $err").mapError(new Exception(_)),
              graph => graphOperationsMenu(graph, graphType)
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

  def graphOperationsMenu[T](graph: Graph[T], graphType: String): ZIO[Any, Throwable, Unit] = {
    val menu = """
                 |1. Add Edge
                 |2. Remove Edge
                 |3. List Vertices
                 |4. List Edges
                 |5. DFS (all type of graph)
                 |6. BFS (all type of graph)
                 |7. Topological Sorting (Directed Graph)
                 |8. CycleDetection (Directed Graph)
                 |9. Floyd Algorithm (Weighted Graph)
                 |10. Dijkstra Algorithm (Weighted Graph)
                 |11. Exit
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

    def performDFS(graph: Graph[T]): ZIO[Any, Throwable, Unit] =
      for {
        _ <- printLine("Enter the starting vertex for DFS:")
        startVertexInput <- readLine
        startVertex = startVertexInput.asInstanceOf[T]
        dfsResult = new DFS().dfs(startVertex, graph, v => printLine(s"Visited vertex: $v"))
        _ <- printLine(s"DFS traversal result: ${dfsResult.mkString(", ")}")
      } yield ()

    def performBFS(graph: Graph[T]): ZIO[Any, Throwable, Unit] =
      for {
        _ <- printLine("Enter the starting vertex for BFS:")
        startVertexInput <- readLine
        startVertex = startVertexInput.asInstanceOf[T]
        dfsResult = new BFS().bfs(startVertex, graph)
        _ <- printLine(s"BFS traversal result: ${dfsResult.mkString(", ")}")
      } yield ()

    def performTopologicalSorting(graph: Graph[T]): ZIO[Any, Throwable, Unit] =
      if (graphType == "directed") {
        val topologicalSorter = new TopologicalSorting[T]
        val sortedList = topologicalSorter.topologicalSort(graph.asInstanceOf[DirectedGraph[T]])
        printLine(s"Topological sorting result: ${sortedList.mkString(", ")}")
      } else {
        printLine("Topological Sorting is only supported for directed graphs.")
      }

    def performCycleDetection(graph: Graph[T]): ZIO[Any, Throwable, Unit] =
      if (graphType == "directed") {
        val CycleDetector = new CycleDetection()
        val CycleResult = CycleDetector.hasCycle(graph)
        printLine(s"Cycle Detection result: ${CycleResult}")
      } else {
        printLine("Cycle Detection is only supported for directed graphs.")
      }

    def performFloydAlgorithm(graph: Graph[T]): ZIO[Any, Throwable, Unit] =
      graph match {
        case g: WeightedGraph[T] =>
          val floydWarshall = new FloydWarshallAlgorithm(g)
          val shortestPaths = floydWarshall.computeShortestPaths()
          printLine(s"Floyd Algorithm result: ${shortestPaths.map(_.mkString(", ")).mkString("\n")}")
        case _ =>
          printLine("Floyd-Warshall Algorithm is only supported for weighted graphs.")
      }

    def performDijkstraAlgorithm(graph: Graph[T]): ZIO[Any, Throwable, Unit] =
      graph match {
        case weightedGraph: WeightedGraph[T] =>
          for {
            _ <- printLine("Enter the source vertex for Dijkstra's algorithm:")
            sourceInput <- readLine
            source = sourceInput.asInstanceOf[T]
            dijkstra = new DijkstraAlgorithm(weightedGraph)
            (distances, predecessors) = dijkstra.shortestPath(source)
            _ <- printLine(s"Shortest distances from $source: ${distances.mkString(", ")}")
            _ <- printLine(s"Predecessors: ${predecessors.mkString(", ")}")
          } yield ()
        case _ =>
          printLine("Dijkstra's Algorithm is only supported for weighted graphs.")
      }


    def writeFile(path: String, content: String): ZIO[Any, Throwable, Unit] =
      for {
        path <- ZIO.attempt(zio.nio.file.Path(path))
        _ <- zio.nio.file.Files.writeBytes(path, zio.Chunk.fromArray(content.getBytes(StandardCharsets.UTF_8)))
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
          case "5" => performDFS(graph) *> loop(graph)
          case "6" => performBFS(graph) *> loop(graph)
          case "7" => performTopologicalSorting(graph) *> loop(graph)
          case "8" => performCycleDetection(graph) *> loop(graph)
          case "9" => performFloydAlgorithm(graph) *> loop(graph)
          case "10" => performDijkstraAlgorithm(graph) *> loop(graph)
          case "11" => ZIO.unit
          case _ => printLine("Invalid option, try again.") *> loop(graph)
        }
      }yield ()).catchAll(err => printLine(s"Error: $err") *> loop(graph))
    loop(graph)

}

}