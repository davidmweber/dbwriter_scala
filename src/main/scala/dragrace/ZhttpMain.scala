package dragrace

import zhttp.service.Server
import zio._
import io.getquill.context.ZioJdbc.DataSourceLayer

// // The entry point for this app
object ZhttpMain extends ZIOAppDefault:
  override def run =
    (for {
      api <- ZIO.service[Api]
      _ <- SampleData.dbSetup
      _ <- Server.start(8090, api.app)
      _ <- Console.printLine("Done")
    } yield ())
      .provide(
        Api.live,
        DataService.live,
        DataSourceLayer.fromPrefix("database").orDie
      )
      .exitCode
end ZhttpMain
