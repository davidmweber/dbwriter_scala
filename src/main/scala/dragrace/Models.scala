package dragrace
import java.time.LocalDateTime
import java.util.Date

// The model of the database table
case class Samples(
    id: Int,
    name: String,
    timestamp: LocalDateTime,
    v0: Option[Float],
    v1: Option[Float]
)
