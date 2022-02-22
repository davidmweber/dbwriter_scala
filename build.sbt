ThisBuild / scalaVersion := "3.1.1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "co.za.monadic"
ThisBuild / organizationName := "example"

val zhttpVersion = "2.0.0-RC3"
val zioVersion = "2.0.0-RC2"
val tapirVersion = "0.20.0-M10"
val quillVersion =  "3.17.0.Beta3.0-RC1" // Actually protoquill!

lazy val root = (project in file("."))
  .settings(
    name := "dbwriter_scala",
    libraryDependencies ++= Seq(
      "io.d11" %% "zhttp" % zhttpVersion,
      "io.getquill" %% "quill-jdbc-zio" % quillVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,
//      "org.postgresql" % "postgresql" % "42.3.1",
      "dev.zio" %% "zio-test" % zioVersion % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
