package dragrace

import zhttp.http.*
import zio.*
import zio.json.*
import java.sql.SQLException
import javax.sql.DataSource
import scala.annotation.meta.getter

type DataIO = ZIO[DataServiceLive & DataService, SQLException, Response]

object Api:

  given JsonEncoder[Samples] = DeriveJsonEncoder.gen[Samples]

  // Demonstrates using a straight up effect instead of hard coding
  // the function inside the routing app
  def helloEffect(name: String): UIO[Response] =
    ZIO.succeed(Response.text(s"Hello World $name"))

  def sampleEffect(id: Int) = ZIO
    .environment[DataServiceLive]
    .flatMap(dsl => dsl.get.getSample(id.toInt).map(cs => Response.json(cs.toJson)))

  val app = Http.collectZIO[Request] {
    case Method.GET -> !! / "hello"        => ZIO.succeed(Response.text("Hello World!"))
    case Method.GET -> !! / "hello" / name => helloEffect(name)
    case Method.GET -> !! / "sample" / id  => sampleEffect(id.toInt)
  }
end Api
