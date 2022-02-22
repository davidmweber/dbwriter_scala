package dragrace
import zio.*
import io.getquill.*
import io.getquill.context.ZioJdbc.DataSourceLayer
import zio.{ExitCode, IO, ULayer, URIO, ZIOAppDefault, ZLayer}

import java.io.IOException
import java.sql.SQLException
import java.util.Date
import javax.sql.DataSource

object QuillContext extends PostgresZioJdbcContext(SnakeCase)

object Queries:

  import QuillContext.lift

  inline def samples() = quote {
    query[Samples]
  }

  inline def sample(id: Int) = quote {
    samples().filter(s => s.id == lift(id)).take(1)
  }

case class Samples(
                    id: Int,
                    name: String,
                    timestamp: Date,
                    v0: Option[Float],
                    v1: Option[Float]
                  )

object QuillMain extends ZIOAppDefault :

  import QuillContext._

  private val zioDS = DataSourceLayer.fromPrefix("database")

  private val app = QuillContext.run(Queries.sample(2))
    .provideLayer(zioDS)

  def run =
    for {
      foo <- app
      _ <- Console.printLine(foo.toString)
    }
    yield ()
