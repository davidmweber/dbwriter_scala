package dragrace
import java.time.LocalDateTime
import java.util.Date
import zio.json.*

trait Model

sealed trait ResponseModel extends Model

object ResponseModel
given JsonEncoder[ResponseModel] = DeriveJsonEncoder.gen[ResponseModel]

sealed trait RequestModel extends Model
given JsonDecoder[RequestModel] = DeriveJsonDecoder.gen[RequestModel]

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

case class NewSample(
    name: String,
    timestamp: LocalDateTime,
    v0: Option[Float],
    v1: Option[Float]
) extends RequestModel

// Cannot instantiate an encoder for List[Stample] without an explicit Sample encoder
given JsonEncoder[Sample] = DeriveJsonEncoder.gen[Sample]
given JsonDecoder[NewSample] = DeriveJsonDecoder.gen[NewSample]

object Foo:
  def decode(s: String) = """{}""".fromJson[NewSample]
