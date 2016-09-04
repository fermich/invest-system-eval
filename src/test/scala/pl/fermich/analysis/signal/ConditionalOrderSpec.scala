package pl.fermich.analysis.signal

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.data.{DailyPrice, Price}

@RunWith(classOf[JUnitRunner])
class ConditionalOrderSpec extends FunSpec with GivenWhenThen {

  describe("A TRAILING STOP order") {
    it("should generate additional stop signals") {
      Given("price list, threshold")
      val trailingStop = TrailingStopOrder(prices, 0.25)

      When("previously generated signals are passed to generate()")
      val stopSignals = trailingStop.generate(signals)

      Then("trailing stop generates correct stop signals")
      stopSignals(0) match { case TradingSignal(20130327, 0) => }
      stopSignals(1) match { case TradingSignal(20130326, 0) => }
      stopSignals(2) match { case TradingSignal(20130324, 1) => }
      stopSignals(3) match { case TradingSignal(20130323, 0) => }
      stopSignals(4) match { case TradingSignal(20130322, 0) => }
      stopSignals(5) match { case TradingSignal(20130319, 1) => }
    }
  }

  val signals = List[TradingSignal](
    TradingSignal(20130327, SignalType.SELL),
    TradingSignal(20130324, SignalType.BUY),
    TradingSignal(20130323, SignalType.SELL),
    TradingSignal(20130319, SignalType.BUY)
  )

  val prices = List(
    DailyPrice(20130327L, 01.0),
    DailyPrice(20130326L, 05.0),
    DailyPrice(20130325L, 30.0),
    DailyPrice(20130324L, 15.0),
    DailyPrice(20130323L, 20.0),
    DailyPrice(20130322L, 10.0),
    DailyPrice(20130321L, 20.0),
    DailyPrice(20130320L, 25.0),
    DailyPrice(20130319L, 05.0))

}
