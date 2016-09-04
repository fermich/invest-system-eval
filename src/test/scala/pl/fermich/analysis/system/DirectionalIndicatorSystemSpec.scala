package pl.fermich.analysis.system

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.data.{Price, Prices}
import pl.fermich.analysis.signal.TradingSignal
import pl.fermich.analysis.strategy.PdiNdiStrategy

@RunWith(classOf[JUnitRunner])
class DirectionalIndicatorSystemSpec extends FunSpec with GivenWhenThen {

  describe("The bulls have the edge when +DI is greater than -DI, while the bears have the edge when -DI is greater") {
    it("should emit signal when a trend is detected") {
      val system = new PdiNdiSystem(PdiNdiStrategy(prices, length))

      Given("prices to be tested")

      When("+DI, -DI trend detection started")
      val signals = system.getSignals()

      Then("+DI, -DI detects trends correctly")
      signals(0) match { case TradingSignal(20130324, 1) => }
      signals(1) match { case TradingSignal(20130318, 0) => }
    }

  }

  val prices = Prices(List[Price](
    Price("FOO", 20130326L, 0.0, 9.0, 5.0, 7.0, 0.0),
    Price("FOO", 20130325L, 0.0, 8.0, 4.0, 6.0, 0.0),
    Price("FOO", 20130324L, 0.0, 7.0, 3.0, 5.0, 0.0),
    Price("FOO", 20130323L, 0.0, 6.0, 2.0, 4.0, 0.0),
    Price("FOO", 20130319L, 0.0, 7.0, 3.0, 5.0, 0.0),
    Price("FOO", 20130318L, 0.0, 8.0, 4.0, 6.0, 0.0),
    Price("FOO", 20130317L, 0.0, 9.0, 5.0, 7.0, 0.0),
    Price("FOO", 20130316L, 0.0, 8.0, 4.0, 6.0, 0.0),
    Price("FOO", 20130315L, 0.0, 7.0, 3.0, 5.0, 0.0),
    Price("FOO", 20130314L, 0.0, 6.0, 2.0, 4.0, 0.0)))

  val length = 2
}
