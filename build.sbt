import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .aggregate(graphModule)

lazy val graphModule = (project in file("GraphModule")).settings(
  name := "GraphModule",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test,
  libraryDependencies += "dev.zio" %% "zio-json" % "0.7.1"
)

//libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test
