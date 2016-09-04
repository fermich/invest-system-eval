package pl.fermich.analysis.signal

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.data.{Price, Prices}
import pl.fermich.analysis.strategy.VamaTwoLineStrategy

@RunWith(classOf[JUnitRunner])
class VamaSignalGeneratorSpec extends FunSpec with GivenWhenThen {

  describe("A stop strategy based on VAMA") {
    it("should generate sell signal") {
      val strategy = VamaTwoLineStrategy(Prices(prices), short ,long)
      val stopGenerator = new VamaSignalGenerator(strategy, new StopSignalFilter{})

      Given("price list")

      When("emit is called")
      val stopSignals = stopGenerator.emit()

      Then("VAMA returns sell signal")
      stopSignals.foreach(_ match {
        case TradingSignal(20130327, 0) =>
      })
    }
  }

  describe("An entry strategy based on VAMA") {
    it("should generate buy signal") {
      val strategy = VamaTwoLineStrategy(Prices(prices), short ,long)
      val entryGenerator = new VamaSignalGenerator(strategy, new EntrySignalFilter{})

      Given("price list")

      When("emit is called")
      val entrySignals = entryGenerator.emit()

      Then("VAMA returns buy signal")
      entrySignals.foreach(_ match {
        case TradingSignal(20130325, 1) =>
      })
    }
  }

  val short = 2
  val long = 5
  val prices = List(
    Price("FOO", 20130327L, 0.0, 0.0, 0.0, 01.0, 1.0),
    Price("FOO", 20130326L, 0.0, 0.0, 0.0, 05.0, 1.0),
    Price("FOO", 20130325L, 0.0, 0.0, 0.0, 30.0, 1.0),
    Price("FOO", 20130324L, 0.0, 0.0, 0.0, 15.0, 1.0),
    Price("FOO", 20130323L, 0.0, 0.0, 0.0, 20.0, 1.0),
    Price("FOO", 20130322L, 0.0, 0.0, 0.0, 10.0, 1.0),
    Price("FOO", 20130321L, 0.0, 0.0, 0.0, 20.0, 1.0),
    Price("FOO", 20130320L, 0.0, 0.0, 0.0, 25.0, 1.0),
    Price("FOO", 20130319L, 0.0, 0.0, 0.0, 05.0, 1.0))

}
