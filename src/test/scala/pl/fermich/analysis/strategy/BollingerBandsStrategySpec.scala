package pl.fermich.analysis.strategy

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.data.{DailyPrice, Price, Prices}


@RunWith(classOf[JUnitRunner])
class BollingerBandsStrategySpec extends FunSpec with GivenWhenThen {

  describe("A bollinger bands strategy uses upper and lower bands") {
    it("should return upper band") {
      Given("implementation of bollinger bands returning upper band")
      val strategy = new BollingerBandsStrategy(prices, length, sigma)

      When("upperBand() is called")
      val upperLimit = strategy.upperBand()

      Then("returns correct upper band")
      assert(upperLimit.size === 1)
      val expectedUpperPrice = avg + sigma * sd
      upperLimit(0) match { case DailyPrice(20130222L, expectedUpperPrice) => }
    }


    it("should return lower band") {
      Given("implementation of bollinger bands returning lower band")
      val strategy = new BollingerBandsStrategy(prices, length, sigma)

      When("lowerBand() is called")
      val lowerLimit = strategy.lowerBand()

      Then("returns correct lower band")
      assert(lowerLimit.size === 1)
      val expectedLowerPrice = avg - sigma * sd
      lowerLimit(0) match { case DailyPrice(20130222L, expectedLowerPrice) => }
    }
  }

  val length: Int = 5
  val sigma: Int = 2

  val avg: Double = 4.0
  val sd: Double = 1.0

  val prices = Prices(List(
    Price("FOO", 20130222L, 0.0, 0.0, 0.0, 3.0, 1.0),
    Price("FOO", 20130221L, 0.0, 0.0, 0.0, 3.0, 1.0),
    Price("FOO", 20130220L, 0.0, 0.0, 0.0, 4.0, 1.0),
    Price("FOO", 20130219L, 0.0, 0.0, 0.0, 5.0, 1.0),
    Price("FOO", 20130218L, 0.0, 0.0, 0.0, 5.0, 1.0))
  )

}
