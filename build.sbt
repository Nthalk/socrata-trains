organization := "com.nthalk.socrata"

version := "0.1.0"

name := "trains"

scalaVersion := "2.11.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

org.scalastyle.sbt.ScalastylePlugin.Settings

libraryDependencies ++= Seq(
  "org.scalamock" %% "scalamock-scalatest-support" % "3.1.1",
  "org.scalatest" %% "scalatest" % "2.1.3" % "test"
)

