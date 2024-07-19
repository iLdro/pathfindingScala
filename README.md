# ZIO Graph Processing Application

## Overview

This Scala application leverages the ZIO library to process graph data from JSON files. It supports directed, undirected, and weighted graphs, providing functionalities to read graph types from JSON, perform graph operations, and interact with the user through a console-based menu.

## Features

- **Graph Type Detection**: Automatically detects the type of graph (directed, undirected, weighted) from a JSON file.
- **Graph Operations**: Supports adding and removing edges, listing vertices, and listing edges for the detected graph type.
- **Interactive Menu**: Provides an interactive console-based menu for performing graph operations.

## How to Run

1. Ensure you have Scala and sbt installed on your system.
2. Clone the repository to your local machine.
3. Navigate to the project directory and run `sbt run` to start the application.
4. Follow the on-screen prompts to interact with the application.

## Project Structure

- `App.scala`: The main entry point of the application, containing the ZIOAppDefault implementation and the interactive menu logic.
- `Graphs.scala`: Contains the definitions for the graph data structures and their operations.
- `GraphType.scala`: Defines the sealed trait and case objects for different graph types.
- `GraphTypeInfo.scala`: Contains the case class for parsing the graph type information from JSON.

## Dependencies

- **ZIO**: Used for effectful and asynchronous programming.
- **zio-json**: Utilized for parsing JSON data.
- **zio-nio**: Provides non-blocking I/O operations.

## Implementation Details
Our graph is defined by a trait of type Vertex. This is a generic type defining the type of the node.
In implementation for a directed or undirected unweighted graph, we use a Map of type Map[Vertex, Set[Vertex]] to represent the graph. The first parameter is the node we are on, the set of Vertex represents the nodes that are connected to the first parameter.
For the Weighted graph, we create a specific type called WeightedEdge which is a case class that contains the destination node and the weight of the edge. In this way it respects the implementation of the graph trait, with a custom type.

The graph possesses the following methods:
- addEdge: Adds an edge between two nodes.
- removeEdge: Removes an edge between two nodes.
- listVertices: Lists all the vertices in the graph.
- listEdges: Lists all the edges in the graph.

Every graph has their one Object having implicit methods to convert the graph to a specific type of graph. This is done to avoid the need to pass the type of graph as a parameter in the methods.
Fo each graph we have a implicit definition for the String type, and Int type. The node would ever be a String or an Int.
This implementation is done for the Json Conversion, Vertex could be every type possible when defined without the implicit conversion.

The GraphType trait is a sealed trait that defines the different types of graphs that can be detected from the JSON file. It has three case objects: Directed, Undirected, and Weighted.
By doing this, we can define the type of graph that we are working with and use it in the implementation of the graph.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
