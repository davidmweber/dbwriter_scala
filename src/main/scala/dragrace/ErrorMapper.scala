package dragrace
import zhttp.http.{Response, HttpError}
import java.sql.SQLException

object ErrorMapper:
  def mapError(error: Throwable): Response =
    error match {
      case e: SQLException => Response.fromHttpError(HttpError.InternalServerError(e.getMessage))
      case _               => Response.fromHttpError(HttpError.InternalServerError("Unknown error"))
    }
