package dragrace

import zhttp.service.Server
import zio.*

// The entry point for this app
object ZhttpMain extends ZIOAppDefault:

  override def run =
    (for {
      _ <- SampleData.dbSetup
      _ <- Server.start(8090, Api.app)
    } yield ())
      .provide(QuillContext.dataSourceLayer, DataService.live)
      .exitCode
