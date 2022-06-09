package dragrace
import io.getquill.*
import zio._
import javax.sql.DataSource
import fansi.ErrorMode.Throw

object SampleData:

  import Queries.*
  import QuillContext.*

  private def getTestSamps(count: Int = 100): IndexedSeq[Sample] =

    val now = java.time.LocalDateTime.now()
    (0 until count).map(i => Sample(i, "egt", now.plusSeconds(i), Some(i.toFloat), Some(i.toFloat)))

  // Create a bunch of fake data. You could also run a database migration here
  val dbSetup = transaction(
    for {
      _ <- run(deleteAllSamples)
      _ <- run(insertSamples(getTestSamps(12)))
    } yield ()
  )
end SampleData
