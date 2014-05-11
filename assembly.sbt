import AssemblyKeys._

assemblySettings

mainClass in assembly := Some("org.falcon.main.Main")

outputPath in assembly := new java.io.File("./out/falcon.jar")
