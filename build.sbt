ThisBuild / scalaVersion := "3.1.3"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "co.za.monadic"
ThisBuild / organizationName := "Monadic Consulting"

val zhttpVersion = "2.0.0-RC10"
val zioVersion = "2.0.2"
val zioJsonVersion = "0.3.0-RC11"
val quillVersion = "4.3.0" // Actually protoquill!
val postgresJdbc = "42.3.2"

lazy val root = (project in file("."))
  .settings(
    name := "dbwriter_scala",
    libraryDependencies ++= Seq(
      "io.d11" %% "zhttp" % zhttpVersion,
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
