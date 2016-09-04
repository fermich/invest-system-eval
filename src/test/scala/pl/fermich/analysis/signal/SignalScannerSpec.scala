package pl.fermich.analysis.signal

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.signal.SignalConverters._
import pl.fermich.analysis.data.DailyPrice


@RunWith(classOf[JUnitRunner])
class SignalScannerSpec extends FunSpec with GivenWhenThen {

  describe("A signal scanner based on Convergence/Divergence Histogram") {
    it("should generate sequence of sell and buy signals") {

      Given("two price lists")
      val short = List[DailyPrice](
        DailyPrice(20130226L, 10.0), DailyPrice(20130225L, 20.0), DailyPrice(20130224L, 30.0), DailyPrice(20130223L, 40.0),
        DailyPrice(20130222L, 30.0), DailyPrice(20130221L, 20.0), DailyPrice(20130220L, 10.0))

      val long = List[DailyPrice](
        DailyPrice(20130226L, 40.0), DailyPrice(20130225L, 30.0), DailyPrice(20130224L, 20.0), DailyPrice(20130223L, 10.0),
        DailyPrice(20130222L, 20.0), DailyPrice(20130221L, 30.0), DailyPrice(20130220L, 40.0))

      When("Convergence/Divergence histogram is applied")
      val signals = short CD_HISTOGRAM long

      Then("Scanner returns signals in preserved order")
      signals(0) match { case TradingSignal(20130225, 0) => }
      signals(1) match { case TradingSignal(20130222, 1) => }
    }
  }


  describe("A breakout occurs when price breaks above a level of resistance") {
    it("should generate sequence of buy signals") {

      Given("resistance prices and prices to be scanned")
      val resistanceLevel = List[DailyPrice](DailyPrice(20130226, 20.0), DailyPrice(20130225, 20.0), DailyPrice(20130224, 20.0), DailyPrice(20130223, 20.0))
      val topPrices = List[DailyPrice](DailyPrice(20130226, 19.0), DailyPrice(20130225, 20.0), DailyPrice(20130224, 21.0), DailyPrice(20130223, 22.0))

      When("BREAKOUT is called")
      val signals = resistanceLevel BREAKOUT topPrices

      Then("BREAKOUT scanner returns exactly two buy signals")
      signals(0) match { case TradingSignal(20130224, 1) => }
      signals(1) match { case TradingSignal(20130223, 1) => }
    }
  }


  describe("A breakdown occurs when price breaks down a level of resistance") {
    it("should generate sequence of sell signals") {

      Given("resistance prices and prices to be scanned")
      val resistanceLevel = List[DailyPrice](DailyPrice(20130226, 21.0), DailyPrice(20130225, 21.0), DailyPrice(20130224, 21.0), DailyPrice(20130223, 21.0))
      val topPrices = List[DailyPrice](DailyPrice(20130226, 19.0), DailyPrice(20130225, 20.0), DailyPrice(20130224, 21.0), DailyPrice(20130223, 22.0))

      When("BREAKDOWN is called")
      val signals = resistanceLevel BREAKDOWN topPrices

      Then("BREAKDOWN scanner returns exactly two sell signals")
      signals(0) match { case TradingSignal(20130226, 0) => }
      signals(1) match { case TradingSignal(20130225, 0) => }
    }
  }


  describe("A trailing stop occurs when price exceeds a factor of change") {
    it("should generate stop signal") {

      Given("prices to be scanned")
      val prices = List[DailyPrice](
        DailyPrice(20130228, 10.0),
        DailyPrice(20130227, 18.0),
        DailyPrice(20130226, 19.0),
        DailyPrice(20130225, 20.0),
        DailyPrice(20130223, 18.0))

      When("TRAILING STOP is called")
      val signal = prices TRAILING_STOP 0.1

      Then("TRAILING STOP returns correct sell signal")
      signal match { case Some(TradingSignal(20130227, 0)) => }
    }
  }

}
