package CommandLine

import zio._
import zio.json._
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets
import Graphs._


object App {

  def run(args : List[String]) = {
    var command = args.headOption.getOrElse("help")

    command match
      case "Help" => println("Commands: Help, AddEdge, RemoveEdge, ShowGraph, SaveGraph, LoadGraph")
      case "AddEdge" => 
        val source = args(1)
        val destination = args(2)
        val graph = Graphs.graph.addEdge(source, destination)
        println(graph)
      case "RemoveEdge" =>
        val source = args(1)
        val destination = args(2)
        val graph = Graphs.graph.removeEdge(source, destination)
        println(graph)
      case "ShowGraph" => println(Graphs.graph)
      case "SaveGraph" =>
        val json = Graphs.graph.toJson
        Files.write(Paths.get("graph.json"), json.getBytes(StandardCharsets.UTF_8))
      case "LoadGraph" =>
        val json = new String(Files.readAllBytes(Paths.get("graph.json")), StandardCharsets.UTF_8)
        val graph = json.fromJson[Graph[String]]
        println(graph)
      case _ => println("Invalid command")
  }

}
