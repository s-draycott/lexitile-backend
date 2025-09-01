enablePlugins(PlayScala)

ThisBuild / scalaVersion := "2.13.16"
ThisBuild / version := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    name := "lexitile",
    libraryDependencies ++= Seq(
      guice,
      "com.typesafe.play" %% "play-json" % "2.10.7",
      "com.typesafe.play" %% "play-filters-helpers" % "2.9.8",
      "com.typesafe.play" %% "filters-helpers" % "2.8.22"
    )
  )
