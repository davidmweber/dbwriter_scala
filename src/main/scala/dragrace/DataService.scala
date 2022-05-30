package dragrace

import io.getquill.*
import zio.*

import java.sql.SQLException
import javax.sql.DataSource

trait DataService:
  def getSample(id: Int): ZIO[DataSource, SQLException, Samples]
  def getSamples(): ZIO[DataSource, SQLException, List[Samples]]

// Map the data service functions over to queries using the
// context that will later be injected by ZIO
final case class DataServiceLive() extends DataService:
  import Queries.*
  import QuillContext.*
  def getSample(id: Int): ZIO[DataSource, SQLException, Samples] =
    run(sample(id)).map(_.head) // Need a better way to deal with not found

  def getSamples(): ZIO[DataSource, SQLException, List[Samples]] =
    run(samples)
end DataServiceLive

object DataService:
  val live = ZLayer.succeed(DataServiceLive())
