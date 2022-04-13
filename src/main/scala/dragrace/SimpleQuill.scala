package dragrace

package dragrace
import java.time.LocalDateTime
import java.util.Date
import zio.ZIOAppDefault
import io.getquill.*
import io.getquill.context.ZioJdbc.DataSourceLayer
import zio.Console.printLine

// The model of the database table
case class Samples(
    id: Int,
    name: String,
    timestamp: LocalDateTime,
    v0: Option[Float],
    v1: Option[Float]
)

object SimpleQuill extends ZIOAppDefault:

  object MyPostgresContext extends PostgresZioJdbcContext(Literal)
  import MyPostgresContext._

  val zioDS = DataSourceLayer.fromPrefix("database")

  val s = Samples(2, "egt", java.time.LocalDateTime.now(), Some(0.1f), Some(0.2f))
  val now = java.time.LocalDateTime.now()
  val samps = (0 until 10)
    .map(i =>
      Samples(i, "Exhaust gas temperature", now.plusSeconds(i), Some(i.toFloat), Some(i.toFloat))
    )

  inline def inserts(ss: Seq[Samples]) = quote {
    liftQuery(ss).foreach(s => query[Samples].insertValue(s))
  }

  override def run =
    val setup = transaction(for {
      _ <- MyPostgresContext.run(quote(query[Samples].delete))
      _ <- MyPostgresContext.run(inserts(samps))
    } yield ())
    setup
      .tap(r => zio.Task(println(r.toString)))
      .provideCustomLayer(zioDS)
      .orDie
