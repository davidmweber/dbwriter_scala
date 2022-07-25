package dragrace
import zhttp.http.{Response, HttpError, Request}
import java.sql.SQLException
import zio.{ZIO, UIO}
import javax.sql.DataSource
import zio.json.*
import scala.util.Either

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

end RestMapper
