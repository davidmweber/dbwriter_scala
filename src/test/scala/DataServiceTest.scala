import zio.*
import zio.test.{test, suite, ZIOSpecDefault, assertTrue}
import zio.test.Assertion.*
import zio.json.*
import zhttp.http.*
import io.getquill.context.ZioJdbc.DataSourceLayer
import dragrace.*
import java.time.LocalDateTime

import zio.test.Spec
import zio.test.TestEnvironment

object DbInit:
  import io.getquill.*
  import QuillContext.*
  def live = ZLayer.fromZIO(run(Queries.deleteAllSamples))

object DataServiceTest extends ZIOSpecDefault:

  val spec = suite("Playground tests")(
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

object SerializerTest extends ZIOSpecDefault:

  val spec = suite("Codec tests")(
    test("Encoders and decoders are doing their job") {
      val j = Sample(1, "test", LocalDateTime.of(2020, 1, 1, 0, 0, 0), None, None).toJson
      assertTrue(j.equals("""{"id":1,"name":"test","timestamp":"2020-01-01T00:00:00"}"""))
    },
    test("Decoder is doing its job") {
      val j = """{"NewSample":{"name":"test","timestamp":"2020-01-01T00:00:00"}}"""
      val n = j.fromJson[RequestModel]
      assertTrue(n.isRight)
      assertTrue(n.contains(NewSample("test", LocalDateTime.of(2020, 1, 1, 0, 0, 0), None, None)))
    }
  )
