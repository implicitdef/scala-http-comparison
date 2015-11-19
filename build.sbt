lazy val root = (project in file(".")).
  settings(
    name := "scala-http-study",
    version := "0.0.1",
    scalaVersion := "2.11.7",
    scalacOptions ++= Seq("-unchecked", "-feature", "-deprecation") /*,
    libraryDependencies += "com.typesafe.play" % "play-ws_2.11" % "2.4.3"
    ...Add dependencies here...
    */
  )
