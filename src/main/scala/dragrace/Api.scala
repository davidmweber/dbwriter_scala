package dragrace

import zhttp.http.*
import zio.*
import zio.json.*
import java.sql.SQLException

type DataIO = ZIO[DataService, SQLException, Response]

object Api:

  given JsonEncoder[Samples] = DeriveJsonEncoder.gen[Samples]

  // Demonstrates using a straight up effect instead of hard coding
  // the function inside the routing app
  def helloEffect(name: String): UIO[Response] =
    ZIO.succeed(Response.text(s"Hello World $name"))

  def sampleEffect(id: Int): DataIO = ZIO
    .environment[DataService]
    .flatMap(dsl =>
      dsl.get
        .getSample(id.toInt)
        .map(cs => Response.json(cs.toJson))
    )

  val app = Http.collectZIO[Request] {
    case Method.GET -> !! / "hello"        => ZIO.succeed(Response.text("Hello World!"))
    case Method.GET -> !! / "hello" / name => helloEffect(name)
    case Method.GET -> !! / "sample" / id  => sampleEffect(id.toInt)
  }
end Api
