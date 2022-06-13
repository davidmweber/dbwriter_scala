package dragrace

import io.getquill.*
import zio.*

import java.sql.SQLException
import javax.sql.DataSource

trait DataService:
  def getSample(id: Long): ZIO[DataSource, SQLException, Sample]
  def getSamples: ZIO[DataSource, SQLException, SampleList]
  def addSample(s: NewSample): ZIO[DataSource, SQLException, Long]
  def delSample(id: Int): ZIO[DataSource, SQLException, Long]

// Map the data service functions over to queries using the
// context that will later be injected by ZIO
final case class DataServiceLive() extends DataService:
  import Queries.*
  import QuillContext.*

  def getSample(id: Long): ZIO[DataSource, SQLException, Sample] =
    run(sample(id)).map(_.head) // Need a better way to deal with not found

  def getSamples: ZIO[DataSource, SQLException, SampleList] =
    run(samples).map(SampleList.apply)

  def addSample(s: NewSample): ZIO[DataSource, SQLException, Long] =
    run(insertSample(Sample(s)))

  def delSample(id: Int): ZIO[DataSource, SQLException, Long] =
    run(deleteSample(id))

end DataServiceLive

object DataService: // extends Accessible[DataService]:
  val live = ZLayer.succeed(DataServiceLive())
//val live = DataServiceLive.apply.toLayer
