package pl.fermich.analysis.signal

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SignalFilterSpec extends FunSpec with GivenWhenThen {

  describe("FirstBuyFirstSellSignalFilter filter takes care of further false signals") {
    it("should discard duplicated signals") {

      val filter = new FirstBuyFirstSellSignalFilter{}
      Given("list with duplicated signals in descending order")

      When("filtering is performed")
      val filtered = filter.filter(signals)

      Then("The strategy returns interleaved buy and sell signals")
      filtered(0) match { case TradingSignal(20130329, 1) => }
      filtered(1) match { case TradingSignal(20130328, 0) => }
      filtered(2) match { case TradingSignal(20130327, 1) => }
      filtered(3) match { case TradingSignal(20130324, 0) => }
      filtered(4) match { case TradingSignal(20130321, 1) => }
    }
  }

  describe("LastBuyFirstSellSignalFilter filter takes care of previous and further weak signals") {
    it("should discard duplicated signals") {

      val filter = new LastBuyFirstSellSignalFilter{}
      Given("list with duplicated signals in descending order")

      When("filtering is performed")
      val filtered = filter.filter(signals)

      Then("The strategy returns interleaved buy and sell signals")
      filtered(0) match { case TradingSignal(20130328, 0) => }
      filtered(1) match { case TradingSignal(20130327, 1) => }
      filtered(2) match { case TradingSignal(20130324, 0) => }
      filtered(3) match { case TradingSignal(20130323, 1) => }
    }
  }

  describe("LastInTheSameSignalSequenceFilter filter takes care of previous false signals") {
    it("should discard duplicated signals") {

      val filter = new LastInTheSameSignalSequenceFilter{}
      Given("list with duplicated signals in descending order")

      When("filtering is performed")
      val filtered = filter.filter(signals)

      Then("The strategy returns interleaved buy and sell signals")
      filtered(0) match { case TradingSignal(20130329, 1) => }
      filtered(1) match { case TradingSignal(20130328, 0) => }
      filtered(2) match { case TradingSignal(20130327, 1) => }
      filtered(3) match { case TradingSignal(20130326, 0) => }
      filtered(4) match { case TradingSignal(20130323, 1) => }
    }
  }

  val signals = List[TradingSignal](
    TradingSignal(20130329, 1),
    TradingSignal(20130328, 0),
    TradingSignal(20130327, 1),
    TradingSignal(20130326, 0),
    TradingSignal(20130325, 0),
    TradingSignal(20130324, 0),
    TradingSignal(20130323, 1),
    TradingSignal(20130322, 1),
    TradingSignal(20130321, 1)
  )

}