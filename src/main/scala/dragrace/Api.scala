package dragrace

import zhttp.http.*
import zio.*
import zio.json.*
import java.sql.SQLException
import javax.sql.DataSource
import scala.annotation.meta.getter
import ErrorMapper.mapError

trait Api:
  def app: Http[DataSource & DataService, Nothing, Request, Response]

case class ApiLive(ds: DataService) extends Api:

  given JsonEncoder[Samples] = DeriveJsonEncoder.gen[Samples]

  // Demonstrates using a straight up effect instead of hard coding
  // the function inside the routing app
  def helloEffect(name: String): UIO[Response] =
    ZIO.succeed(Response.text(s"Hello World $name"))

  def err(e: Throwable) = Http.fail(e)

  def sampleEffect(id: Int) =
    for {
      s <- ds.getSample(id.toInt)
      json = s.toJson
    } yield Response.json(json)

  def se(id: Int) =
    ds.getSample(id.toInt)
      .fold(
        f => mapError(f),
        s => Response.json(s.toJson) // Success
      )

  override val app = Http.collectZIO[Request] {
    case Method.GET -> !! / "hello"            => ZIO.succeed(Response.text("Hello World!"))
    case Method.GET -> !! / "hello" / name     => helloEffect(name)
    case Method.GET -> !! / "sample" / int(id) => se(id) //sampleEffect(id.toInt)
  }

object Api:
  val live = ZLayer.fromFunction(ds => ApiLive(ds))
