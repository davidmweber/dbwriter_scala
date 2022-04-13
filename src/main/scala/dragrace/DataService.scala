package dragrace

import io.getquill.*
import zio.*

import java.sql.SQLException
import javax.sql.DataSource

trait DataService:
  def getSample(id: Int): IO[SQLException, Samples]
  def getSamples(): IO[SQLException, List[Samples]]

object DataService:
  val live = (DataServiceLive.apply _).toLayer[DataService]

// Map the data service functions over to queries using the
// context that will later be injected by ZIO
final case class DataServiceLive(ds: DataSource) extends DataService:
  import Queries.*
  import QuillContext.*
  def getSample(id: Int): IO[SQLException, Samples] =
    run(sample(id)).map(_.head).provideService(ds) // Need a better way to deal with not found

  def getSamples(): IO[SQLException, List[Samples]] =
    run(samples).provideService(ds)

end DataServiceLive
