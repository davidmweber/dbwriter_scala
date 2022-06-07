package dragrace
import io.getquill.*
import zio._
import javax.sql.DataSource

object SampleData:

  import QuillContext.*

  private def getTestSamps(count: Int = 100): IndexedSeq[Samples] =
    val now = java.time.LocalDateTime.now()
    (0 until count).map(i =>
      println("fucker")
      Samples(i, "egt", now.plusSeconds(i), Some(i.toFloat), Some(i.toFloat))
    )

  // A "runnable" ZIO that will poplulate the database
  val dbSetup = transaction(for {
    ds <- ZIO.environment[DataSource]
    _ <- Console.printLine("Deleting samples")
    _ <- run(Queries.deleteAllSamples)
    _ <- Console.printLine("Creating new samples")
    _ <- run(Queries.insertSamples(getTestSamps(12)))
    _ <- Console.printLine("Completed sample generation")
  } yield ())
end SampleData
