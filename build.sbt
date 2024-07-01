ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .aggregate(graphModule)

lazy val graphModule = project in file("GraphModule")

