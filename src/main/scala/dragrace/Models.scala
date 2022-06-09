package dragrace
import java.time.LocalDateTime
import java.util.Date
import zio.json.*

sealed trait ResponseModel

sealed trait RequestModel

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

case class NewSample(
    name: String,
    timestamp: LocalDateTime,
    v0: Option[Float],
    v1: Option[Float]
) extends RequestModel

given JsonEncoder[Sample] = DeriveJsonEncoder.gen[Sample]
given JsonDecoder[NewSample] = DeriveJsonDecoder.gen[NewSample]
