package dragrace

import zhttp.http.*
import zhttp.service.Server
import zio.*

import java.sql.SQLException
import java.util.Date
import javax.sql.DataSource


object ZhttpMain extends ZIOAppDefault:

  val versionLayer: ULayer[String] = ZLayer.succeed("2.3.1")

  val versionEffect: ZIO[String, Nothing, Response] = for
    v <- ZIO.service[String]
    r <- UIO(Response.text(s"Version: $v"))
  yield r

  def helloEffect(name: String): UIO[Response] =
    ZIO.succeed(Response.text(s"Hello World $name"))

  private val app = Http.collectZIO[Request] {
    case Method.GET -> !! / "hello" =>
      ZIO.succeed(Response.text("Hello World!"))
    case Method.GET -> !! / "hello" / name => helloEffect(name)
    case Method.GET -> !! / "version"      => versionEffect
  }

  override def run: URIO[Any, ExitCode] =
    Server.start(8090, app).provideLayer(versionLayer).exitCode
