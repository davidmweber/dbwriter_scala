import zio._
import zio.test.{test, suite, ZIOSpecDefault, assertTrue}
import zio.test.Assertion._

import dragrace.*
import io.getquill.context.ZioJdbc.DataSourceLayer

object DataServiceTest extends ZIOSpecDefault {

  def spec = suite("Playground tests")(
    test("Stoopid test") {
      assertTrue(true)
    },
    test("Sample table CRUD") {
      val s = NewSample("test", java.time.LocalDateTime.now(), Some(0.1f), None)
      for {
        ds <- ZIO.service[DataService]
        i <- ds.addSample(s)
        r <- ds.getSample(i)
      } yield assertTrue(Sample(s).copy(id = i).equals(r))
    }
  ).provide(DataService.live, DataSourceLayer.fromPrefix("database").orDie)
}
