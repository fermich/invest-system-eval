package pl.fermich.analysis.system

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.data.{Price, Prices}
import pl.fermich.analysis.signal.TradingSignal
import pl.fermich.analysis.strategy.ChannelBreakoutStrategy

@RunWith(classOf[JUnitRunner])
class ChannelBreakoutSystemSpec extends FunSpec with GivenWhenThen {

  describe("A trading system based on channel breakouts") {
    it("should generate buy and sell signals in descending order") {
      val strategy = ChannelBreakoutStrategy(prices, entryLength, stopLength)
      val system = new ChannelBreakoutSystem(strategy)
      Given("price list")

      When("getting signals")
      val signals = system.getSignals()

      Then("The system returns two pairs of buy and sell signals in descending order")
      signals(0) match { case TradingSignal(20130325, 0) => }
      signals(1) match { case TradingSignal(20130321, 1) => }
    }
  }

  val stopLength = 2
  val entryLength = 3

  val prices = Prices(List(
    Price("FOO", 20130327L, 0.0, 0.0, 0.0, 1.0, 1.0), // 6, 2  S
    Price("FOO", 20130326L, 0.0, 0.0, 0.0, 2.0, 1.0), // 7. 4  S
    Price("FOO", 20130325L, 0.0, 0.0, 0.0, 4.0, 1.0), // 7, 6  S
    Price("FOO", 20130324L, 0.0, 0.0, 0.0, 6.0, 1.0), // 7, 5
    Price("FOO", 20130323L, 0.0, 0.0, 0.0, 7.0, 1.0), // 5, 4  B
    Price("FOO", 20130322L, 0.0, 0.0, 0.0, 5.0, 1.0), // 4, 2  B
    Price("FOO", 20130321L, 0.0, 0.0, 0.0, 4.0, 1.0), // 3, 2  B
    Price("FOO", 20130320L, 0.0, 0.0, 0.0, 2.0, 1.0),
    Price("FOO", 20130319L, 0.0, 0.0, 0.0, 3.0, 1.0),
    Price("FOO", 20130318L, 0.0, 0.0, 0.0, 1.0, 1.0)))
}
