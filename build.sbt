lazy val root = (project in file(".")).
  settings(
    name := "scala-http-study",
    version := "0.0.1",
    scalaVersion := "2.11.7",
    scalacOptions ++= Seq("-unchecked", "-feature", "-deprecation"),
    libraryDependencies += "com.typesafe.play" %% "play-ws" % "2.4.3",
    libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % "0.11.3",
    libraryDependencies += "io.spray" %% "spray-client" % "1.3.1"
  )
