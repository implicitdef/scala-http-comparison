Basic sbt boilerplate, directory structure, and .gitignore for raw Scala (non-Play) projects

Unlike the `activator new` command from Typesafe's Activator, this uses the recommended structure for the build.sbt file, a.k.a. the "Multi-project .sbt build definition" (cf http://www.scala-sbt.org/0.13/tutorial/Basic-Def.html).

Steps to use it :

- Clone this project
- In `build.sbt`, change the name of the project and the various other settings
- In project/build.properties, you can change the sbt version if necessary
- Change the name of the root package "changeme" by renaming the folder src/main/scala/changeme and the package declaration (`package changeme`) in Main.scala
- You should be able to run the project `sbt run`, which should display "Hello world!".
