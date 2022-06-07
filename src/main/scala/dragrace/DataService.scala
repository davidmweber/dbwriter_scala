package dragrace

import io.getquill.*
import zio.*

import java.sql.SQLException
import javax.sql.DataSource
//import io.getquill.context.DatasourceContextInjectionMacro

object PgCtx extends PostgresZioJdbcContext(Literal)

trait DataService:
  def getSample(id: Int): ZIO[DataSource, SQLException, Samples]
  def getSamples(): ZIO[DataSource, SQLException, List[Samples]]
  def addSample(s: Samples): ZIO[DataSource, SQLException, Long]
  def delSample(id: Int): ZIO[DataSource, SQLException, Long]

// Map the data service functions over to queries using the
// context that will later be injected by ZIO
final case class DataServiceLive() extends DataService:
  import Queries.*
  import PgCtx.*

  def getSample(id: Int): ZIO[DataSource, SQLException, Samples] =
    run(sample(id)).map(_.head) // Need a better way to deal with not found

  def getSamples(): ZIO[DataSource, SQLException, List[Samples]] =
    run(samples)

  def addSample(s: Samples): ZIO[DataSource, SQLException, Long] =
    run(insertSample(s))

  def delSample(id: Int): ZIO[DataSource, SQLException, Long] =
    run(deleteSample(id))

end DataServiceLive

object DataService: // extends Accessible[DataService]:
  val live = ZLayer.succeed(DataServiceLive())
//val live = DataServiceLive.apply.toLayer
