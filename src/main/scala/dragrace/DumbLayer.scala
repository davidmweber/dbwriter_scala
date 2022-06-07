package dragrace
import zio.*

trait DumbLayer:
  def log(s: String): Task[Unit]

case class DumbLayerLive(prefix: String) extends DumbLayer:
  def log(s: String) = Console.printLine(s"$prefix: $s")

object DumbLayer:
  val live = ZLayer.fromFunction(DumbLayerLive(_))
