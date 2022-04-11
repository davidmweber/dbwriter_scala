package dragrace

import io.getquill.*
import zhttp.service.Server
import zio.*

import java.sql.SQLException
import java.util.Date
import javax.sql.DataSource

/** These are the queries that will be run against the database.
  */
/*
 * This is the database schema. Schema generation from the database is possible
 * in quill but it is not very mature.
 */

object ZhttpMain extends ZIOAppDefault:
  import io.getquill.context.ZioJdbc.DataSourceLayer

  override def run =
    Server
      .start(8090, Api.app)
      .provide(QuillContext.dataSourceLayer, DataService.live)
      .exitCode
