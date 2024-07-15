import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .aggregate(graphModule, commandLine)
  .settings(
    // Add common settings here if needed
  )

lazy val graphModule = (project in file("GraphModule")).settings(
  name := "GraphModule",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test,
  libraryDependencies += "dev.zio" %% "zio-json" % "0.7.1"
)

lazy val commandLine = (project in file("ZioApi")).dependsOn(graphModule).settings(
  name := "ZioApi",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test,
  libraryDependencies += "dev.zio" %% "zio" % "2.1.5",
  libraryDependencies += "dev.zio" %% "zio-nio" % "2.0.2"
)