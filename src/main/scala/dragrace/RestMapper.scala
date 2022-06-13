package dragrace
import zhttp.http.{Response, HttpError, Request}
import java.sql.SQLException
import zio.{ZIO, UIO}
import javax.sql.DataSource
import zio.json.*
import scala.util.Either
import dragrace.given JsonDecoder[?]

object RestMapper:
  def mapError(error: Throwable): UIO[Response] =
    val r = Response.fromHttpError(
      error match
        case e: SQLException => HttpError.InternalServerError(e.getMessage)
        case _               => HttpError.InternalServerError("Unknown error")
    )
    return ZIO.succeed(r)

  def makeResponse(r: ZIO[DataSource, Throwable, ResponseModel]) =
    r.map((j: ResponseModel) => Response.json(j.toJson)).catchAll(mapError)

// def mapRequest[R <: RequestModel, T <: ResponseModel](
//     rz: ZIO[DataSource, Throwable, Request],
//     lambda: R => ZIO[DataSource, Throwable, T]
// ): UIO[Response] =
//   val params: Either[String, R] = for {
//     r <- rz
//     j <- r.bodyAsString
//   } yield j.fromJson[R]
//   params match
//     case Right(v) => lambda(v).flatMap(r => makeResponse(r))
//     case Left(e)  => ZIO.succeed(Response.fromHttpError(HttpError.BadRequest(e)))
end RestMapper
