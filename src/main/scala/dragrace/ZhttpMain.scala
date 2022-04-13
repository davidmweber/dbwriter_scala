package dragrace

import io.getquill.*
import zhttp.service.Server
import zio.*
import zio.Console.printLine

import java.sql.SQLException
import java.util.Date
import javax.sql.DataSource

object CreateSampleData:
  import QuillContext.*

  val now = java.time.LocalDateTime.now()
  val samps = (0 until 10)
    .map(i => Samples(i, "egt", now.plusSeconds(i), Some(i.toFloat), Some(i.toFloat)))

  val dbSetup = transaction(for {
    _ <- run(Queries.deleteAllSamples)
    _ <- run(Queries.insertSamples(samps))
  } yield ())

end CreateSampleData

// The entry point for this app
object ZhttpMain extends ZIOAppDefault:

  override def run =
    (for {
      _ <- CreateSampleData.dbSetup
      _ <- Server.start(8090, Api.app)
    } yield ())
      .provide(QuillContext.dataSourceLayer, DataService.live)
      .exitCode
