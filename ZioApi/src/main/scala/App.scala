import zio.Console.*
import Graphs.*
import zio.*
import zio.json.*
import zio.nio.file.Files
import zio.nio.file.Path

import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import scala.jdk.CollectionConverters.*

object App extends ZIOAppDefault {

  def run: ZIO[Any, Throwable, Unit] =
    for {
      _ <- printLine("Enter the path to the JSON file:")
      path <- readLine
      json <- readFile(path)
      _ <- json.fromJson[WeightedGraph[String]].fold(
        err => printLine(s"Error parsing JSON for String vertices: $err"),
        graph => graphOperationsMenu(graph)
      ).orElse(
        json.fromJson[WeightedGraph[Int]].fold(
          err => printLine(s"Error parsing JSON for Int vertices: $err"),
          graph => graphOperationsMenu(graph)
        )
      )
    } yield ()

  def readFile(path: String): ZIO[Any, Throwable, String] =
    for {
      path <- ZIO.attempt(Path(path))
      bytes <- Files.readAllBytes(path)
      content = new String(bytes.toArray, StandardCharsets.UTF_8)
    } yield content

  def graphOperationsMenu[T](graph: WeightedGraph[T]): ZIO[Any, Throwable, Unit] = {
    val menu = """
                 |1. Add Edge
                 |2. Remove Edge
                 |3. List Vertices
                 |4. List Edges
                 |5. Exit
                 |Choose an option: """.stripMargin

    def addEdge(graph: WeightedGraph[T]): ZIO[Any, Throwable, WeightedGraph[T]] =
      for {
        _ <- printLine("Enter source, destination, and weight (separated by space):")
        input <- readLine
        data = input.split(" ")
        updatedGraph = graph.addEdge(data(0).asInstanceOf[T], WeightedEdge(data(1).asInstanceOf[T], data(2).toInt))
      } yield updatedGraph

    def removeEdge(graph: WeightedGraph[T]): ZIO[Any, Throwable, WeightedGraph[T]] =
      for {
        _ <- printLine("Enter source and destination to remove (separated by space):")
        input <- readLine
        data = input.split(" ")
        updatedGraph = graph.removeEdge(data(0).asInstanceOf[T], data(1).asInstanceOf[T])
      } yield updatedGraph

    def listVertices(graph: WeightedGraph[T]): ZIO[Any, Throwable, Unit] =
      printLine(s"Vertices: ${graph.vertices.mkString(", ")}")

    def listEdges(graph: WeightedGraph[T]): ZIO[Any, Throwable, Unit] =
      printLine(s"Edges: ${graph.edges.mkString(", ")}")

    def loop(graph: WeightedGraph[T]): ZIO[Any, Throwable, Unit] =
      (for {
        _ <- printLine(menu)
        choice <- readLine
        _ <- choice match {
          case "1" => addEdge(graph).flatMap(loop)
          case "2" => removeEdge(graph).flatMap(loop)
          case "3" => listVertices(graph) *> loop(graph)
          case "4" => listEdges(graph) *> loop(graph)
          case "5" => ZIO.succeed(())
          case _ => printLine("Invalid option, try again.") *> loop(graph)
        }
      } yield ()).catchAll(err => printLine(s"Error: $err") *> loop(graph))

    loop(graph)
}

}