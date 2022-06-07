import zio._
import zio.test.{test, _}
import zio.test.Assertion._

import dragrace.Samples
import dragrace.QuillContext
import dragrace.DataServiceLive
import io.getquill.context.ZioJdbc.DataSourceLayer
import dragrace.DataService
import dragrace.DumbLayer

object DataServiceTest extends ZIOSpecDefault {
  def spec = suite("Playground tests")(
    test("Stoopid test") {
      assertTrue(true)
    },
    test("Dumb logger") {
      for {
        l <- ZIO.service[DumbLayer]
        d <- l.log("Hello world")
        r <- TestConsole.output
      } yield assertTrue(r(0).equals("Prefix: Hello world\n"))
    }.provide(DumbLayer.live, ZLayer.succeed("Prefix")),
    test("Sample table CRUD") {
      val s = Samples(1, "test", java.time.LocalDateTime.now(), Some(0.1f), None)
      for {
        ds <- ZIO.service[DataService]
        _ <- ds.delSample(1)
        i <- ds.addSample(s)
        r <- ds.getSample(1)
      } yield assertTrue(s.equals(r))
    }.provide(DataService.live, DataSourceLayer.fromPrefix("database").orDie)
  )
}
