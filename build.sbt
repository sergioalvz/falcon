name := "Falcon"

version := "1.0"

scalaVersion := "2.11.0"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= List(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.1",
  "com.github.scopt" %% "scopt" % "3.2.0"
)
