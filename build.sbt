import Dependencies._

lazy val libs = (project in file("libs")).
  settings(
    inThisBuild(
      List(
        organization := "com.example",
        scalaVersion := "2.12.4",
        version      := "0.1.0-SNAPSHOT"
      )
    ),
    name := "libs",
    libraryDependencies ++= Seq(
        playJson,
        scalaTest % Test
    )
  )

lazy val root = (project in file("app")).
  settings(
    inThisBuild(
      List(
        organization := "com.example",
        scalaVersion := "2.12.4",
        version      := "0.1.0-SNAPSHOT"
      )
    ),
    name := "study-play-json",
    libraryDependencies ++= Seq(
        playJson,
        scalaTest % Test
    )
  ).dependsOn(libs)

