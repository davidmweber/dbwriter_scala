ThisBuild / scalaVersion := "3.1.2"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "co.za.monadic"
ThisBuild / organizationName := "example"

val zhttpVersion = "2.0.0-RC8"
val zhttpLogVersion = "2.0.0-RC9"
val zioVersion = "2.0.0-RC6"
val zioJsonVersion = "0.3.0-RC8"
val quillVersion = "4.0.0-RC1" // Actually protoquill!
val postgresJdbc = "42.3.2"

lazy val root = (project in file("."))
  .settings(
    name := "dbwriter_scala",
    libraryDependencies ++= Seq(
      "io.d11" %% "zhttp" % zhttpVersion,
      "io.d11" %% "zhttp-logging" % zhttpLogVersion,
      "io.getquill" %% "quill-jdbc-zio" % quillVersion,
      "org.postgresql" % "postgresql" % postgresJdbc,
      "dev.zio" %% "zio-json" % zioJsonVersion,
      "dev.zio" %% "zio" % zioVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.10",
      "dev.zio" %% "zio-test" % zioVersion % Test,
      "dev.zio" %% "zio-test-sbt" % zioVersion % Test,
      "dev.zio" %% "zio-test-magnolia" % zioVersion % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
