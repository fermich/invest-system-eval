package pl.fermich.analysis.indicator

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.indicator.IndicatorConverters._

@RunWith(classOf[JUnitRunner])
class MovingAverageSpec extends FunSpec with GivenWhenThen {

  describe("The Simple Moving Average indicator") {
    it("should return sequence of average prices") {

      Given("period, prices")
      val period = 4
      val prices = List[Double](04.0, 08.0, 12.0, 16.0, 20.0, 24.0, 28.0, 32.0)

      When("SMA is applied")
      val sma = prices.SMA(period)

      Then("the prices must be smoothed according to period")
      assert(sma(0) == 10.0)
      assert(sma(1) == 14.0)
      assert(sma(2) == 18.0)
      assert(sma(3) == 22.0)
      assert(sma(4) == 26.0)
    }
  }

  describe("The Volume Adjusted Moving Average indicator") {
    it("should return volume weighted prices") {
      
      Given("period, prices, volume")
      val period = 4
      val prices = List[Double](03.0, 04.0, 05.0, 04.0, 04.0, 05.0, 04.0, 03.0)
      val volume = List[Double](10.0, 20.0, 30.0, 40.0, 10.0, 20.0, 30.0, 40.0)
      
      When("VAMA is applied")
      val vama = prices.VAMA(volume, period)
      
      Then("the prices must be smoothed according to period and volume")
      assert(vama(0) == 4.2)
      assert(vama(1) == 4.3)
      assert(vama(2) == 4.5)
      assert(vama(3) == 4.2)
      assert(vama(4) == 3.8)
    }
  }
  
  
  describe("The Exponential Moving Average indicator") {
    it("should weight current prices more heavily than past prices") {
      
      Given("period, prices, weights")
      val period = 4
      val prices =  List[Double](3.0, 4.0, 5.0, 4.0, 13.0, 09.0, 08.0, 06.0)
      val weights = List[Double](1.0, 2.0, 3.0, 4.0, 11.0, 12.0, 13.0, 14.0)      
      
      When("EMA is applied")
      val ema = prices.EMA(weights, period)
      assert(ema(0) == 4.2)
      assert(ema(1) == 9.1)
      assert(ema(2) == 9.4)
      assert(ema(3) == 9.275)
      assert(ema(4) == 8.78)
      
      Then("the prices must be smoothed according to period and weights")

    }
  }  
}