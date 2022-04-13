package dragrace
import QuillContext.*
import io.getquill.*

object SampleData:

  private def getTestSamps(count: Int = 100): IndexedSeq[Samples] =
    val now = java.time.LocalDateTime.now()
    (0 until count).map(i =>
      Samples(i, "egt", now.plusSeconds(i), Some(i.toFloat), Some(i.toFloat))
    )

  // A "runnable" ZIO that will poplulate the database
  val dbSetup = transaction(for {
    _ <- run(Queries.deleteAllSamples)
    _ <- run(Queries.insertSamples(getTestSamps(12)))
  } yield ())

end SampleData
