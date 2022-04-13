package dragrace

import io.getquill.{PostgresZioJdbcContext, Literal}
import io.getquill.context.ZioJdbc.DataSourceLayer
import zio.ULayer
import javax.sql.DataSource

// This just reads the config file in resources/reference.conf and looks under
// the "database" key for the connection details.
object QuillContext extends PostgresZioJdbcContext(Literal):
  val dataSourceLayer = DataSourceLayer.fromPrefix("database").orDie
