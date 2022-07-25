package dragrace
import java.time.LocalDateTime
import java.util.Date
import zio.json.*

trait Model

// Response models only have encoders
sealed trait ResponseModel extends Model

object ResponseModel:
  implicit val encoder: JsonEncoder[ResponseModel] = DeriveJsonEncoder.gen[ResponseModel]
  // This is needed because of a bug in Magnolia where it cannot handle nested classes
  implicit val sEncoder: JsonEncoder[Sample] = DeriveJsonEncoder.gen[Sample]

// The model of the database table
case class Sample(
    id: Long,
    name: String,
    timestamp: LocalDateTime,
    v0: Option[Float],
    v1: Option[Float]
) extends ResponseModel

object Sample:
  def apply(s: NewSample): Sample = Sample(0, s.name, s.timestamp, s.v0, s.v1)

case class SampleList(samples: List[Sample]) extends ResponseModel

case class NewEntityResponse(id: Long) extends ResponseModel

// Request models only have decoders
sealed trait RequestModel extends Model

object RequestModel:
  implicit val decoder: JsonDecoder[RequestModel] = DeriveJsonDecoder.gen[RequestModel]

case class NewSample(
    name: String,
    timestamp: LocalDateTime,
    v0: Option[Float],
    v1: Option[Float]
) extends RequestModel
