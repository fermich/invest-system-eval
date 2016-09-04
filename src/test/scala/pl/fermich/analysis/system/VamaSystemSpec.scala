package pl.fermich.analysis.system

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.data.{Price, Prices}
import pl.fermich.analysis.signal.TradingSignal
import pl.fermich.analysis.strategy.VamaTwoLineStrategy

@RunWith(classOf[JUnitRunner])
class VamaSystemSpec extends FunSpec with GivenWhenThen {

  describe("A trading system based on VAMA signals") {
    it("should generate two signals in descending order") {
      val strategy = VamaTwoLineStrategy(prices, 2 ,5)
      val system = new VamaTradingSystem(strategy)
      Given("price list")

      When("getting signals")
      val signals = system.getSignals()

      Then("The system returns buy and sell signals in descending order")
      signals.head match { case TradingSignal(20130327, 0) => }
      signals.last match { case TradingSignal(20130325, 1) => }
    }
  }

  describe("A trading system based on VAMA_MACD signals") {
    it("should generate two signals in descending order") {
      val strategy = VamaTwoLineStrategy(prices, 2 ,5)
      val system = new VamaTradingSystemWithMACD(strategy)
      Given("price list")

      When("getting signals")
      val signals = system.getSignals()

      Then("The system returns buy and sell signals in descending order")
      signals.head match { case TradingSignal(20130327, 0) => }
      signals.last match { case TradingSignal(20130325, 1) => }
    }
  }


  describe("A trading system based on VAMA_MACD and TRAILING STOP signals") {
      it("should generate two signals in descending order") {
        val strategy = VamaTwoLineStrategy(prices, 2 ,5)
        val system = new VamaWithTrailingStopSystem(strategy, 0.5)
        Given("price list")

        When("getting signals")
        val signals = system.getSignals()

        Then("The system returns buy and sell signals in descending order")
        signals.head match { case TradingSignal(20130326, 0) => }
        signals.last match { case TradingSignal(20130325, 1) => }
      }
  }

  val prices = Prices(List(
    Price("FOO", 20130327L, 0.0, 0.0, 0.0, 01.0, 1.0),
    Price("FOO", 20130326L, 0.0, 0.0, 0.0, 05.0, 1.0),
    Price("FOO", 20130325L, 0.0, 0.0, 0.0, 30.0, 1.0),
    Price("FOO", 20130324L, 0.0, 0.0, 0.0, 15.0, 1.0),
    Price("FOO", 20130323L, 0.0, 0.0, 0.0, 20.0, 1.0),
    Price("FOO", 20130322L, 0.0, 0.0, 0.0, 10.0, 1.0),
    Price("FOO", 20130321L, 0.0, 0.0, 0.0, 20.0, 1.0),
    Price("FOO", 20130320L, 0.0, 0.0, 0.0, 25.0, 1.0),
    Price("FOO", 20130319L, 0.0, 0.0, 0.0, 05.0, 1.0)))
}
