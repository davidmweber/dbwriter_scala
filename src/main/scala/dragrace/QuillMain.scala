package dragrace
import zio.*
import io.getquill.*
import zio.{ExitCode, IO, ULayer, URIO, ZIOAppDefault, ZLayer}

import java.io.IOException
import java.sql.SQLException
import java.util.Date
import javax.sql.DataSource


object QuillMain extends ZIOAppDefault:
  val myApp: ZIO[Console, IOException, Unit] =
    Console.printLine("Hello, World!")

  override def run = myApp
/*
object QuillContext extends PostgresZioJdbcContext(SnakeCase)

case class Sample(
                   id: Int,
                   name: String,
                   timestamp: Date,
                   v0: Option[Float],
                   v1: Option[Float]
                 )

object Queries:
  import QuillContext.lift
  inline def samples = quote { query[Sample] }
  inline def sample(id: Int) = quote {
    samples.filter(s => s.id == lift(id)).take(1)
  }

trait DataService:
  //def samples: IO[SQLException, List[Sample]] = query[Sample]
  def getSample(id: Int): IO[SQLException, Option[Sample]]
//def putSample(sample: Sample): IO[SQLException, Long]

object DataService:
  val live: Any = (DataServiceLive.apply _).toLayer[DataService]

case class DataServiceLive(source: DataSource) extends DataService:
  import QuillContext._
  val env: ULayer[DataSource] =
    ZLayer.succeed(source) // Make this into a formal ZLayer that has errors
  def getSample(id: Int): IO[SQLException, Option[Sample]] =
    run(Queries.sample(id)).provide(env).map(_.headOption)
//def putSample(sample: Sample): IO[SQLException, Long] = ???
*/