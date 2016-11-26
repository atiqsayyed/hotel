name := """hotel"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatest" % "scalatest_2.11" % "2.2.5" % "test",
  "org.mockito" % "mockito-all" % "1.9.5" % "test"
)

