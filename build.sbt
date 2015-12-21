name := """raftology"""

version := "0.1"

scalaVersion := "2.11.6"

lazy val akka           = "com.typesafe.akka" %% "akka-actor" % "2.3.11"

lazy val akkaTest       = "com.typesafe.akka" %% "akka-testkit" % "2.3.11" % "test"

lazy val akkaClustering = "com.typesafe.akka" %% "akka-cluster" % "2.3.11"

lazy val akkaRemoting   = "com.typesafe.akka" %% "akka-remote" % "2.3.11"

lazy val scalaTest      = "org.scalatest" %% "scalatest" % "2.2.4" % "test"


libraryDependencies ++= Seq(
  akka,
  akkaTest,
  akkaClustering,
  akkaRemoting,
  scalaTest
)
