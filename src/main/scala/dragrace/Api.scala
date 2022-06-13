package dragrace

import zhttp.http.*
import zio.*
import zio.json.*
import java.sql.SQLException
import javax.sql.DataSource
import scala.annotation.meta.getter
import RestMapper.makeResponse
import javax.xml.crypto.Data

trait Api:
  def app: Http[DataSource, Nothing, Request, Response]

case class ApiLive(ds: DataService) extends Api:

  def helloEffect(name: String): UIO[String] = ZIO.succeed(s"Hello World $name")

  def getSamples = ds.getSamples
  def getSample(id: Int) = ds.getSample(id)
  def addSample(s: NewSample) = ds.addSample(s)

  override val app = Http.collectZIO[Request] {
    case Method.GET -> !! / "hello"            => ZIO.succeed(Response.text("Hello World!"))
    case Method.GET -> !! / "hello" / name     => helloEffect(name).map(Response.text)
    case Method.GET -> !! / "sample" / int(id) => makeResponse(getSample(id))
    case Method.GET -> !! / "samples"          => makeResponse(getSamples)
    //case Method.POST -> !! / "sample"         => mapRequest[NewSample, NewSampleResponse]()
  }

// TODO: This really does not need to be a layer. Separate the routing out from  the API functions
// and try to leverage zhttp better for the codecs and stuff.
object Api:
  val live = ZLayer.fromFunction(ds => ApiLive(ds))
