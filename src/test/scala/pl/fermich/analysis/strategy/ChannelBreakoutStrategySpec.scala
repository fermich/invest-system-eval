package pl.fermich.analysis.strategy

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.data.{DailyPrice, Price, Prices}

@RunWith(classOf[JUnitRunner])
class ChannelBreakoutStrategySpec extends FunSpec with GivenWhenThen {

  describe("A channel breakout returns max and min prices for last N days") {
    it("should return upper prices for last N days") {
      Given("implementation of channel breakout returning max prices")
      val strategy = new ChannelBreakoutStrategy(prices, channelLength, 0)

      When("upperLimit is called")
      val upperLimit = strategy.upperLimit()

      Then("returns maximum prices for last N days")
      assert(upperLimit.size === 7)
      upperLimit(0) match { case DailyPrice(20130222L, 22.0) => }
      upperLimit(1) match { case DailyPrice(20130221L, 21.0) => }
      upperLimit(2) match { case DailyPrice(20130220L, 22.0) => }
      upperLimit(3) match { case DailyPrice(20130219L, 22.0) => }
      upperLimit(4) match { case DailyPrice(20130218L, 22.0) => }
      upperLimit(5) match { case DailyPrice(20130217L, 21.0) => }
      upperLimit(6) match { case DailyPrice(20130216L, 22.0) => }
    }

    it("should return lower prices for last N days") {
      Given("implementation of channel breakout returning min prices")
      val strategy = new ChannelBreakoutStrategy(prices, 0, channelLength)

      When("upperLimit is called")
      val lowerLimit = strategy.lowerLimit()

      Then("returns minimum prices for last N days")
      assert(lowerLimit.size === 7)
      lowerLimit(0) match { case DailyPrice(20130222L, 20.0) => }
      lowerLimit(1) match { case DailyPrice(20130221L, 20.0) => }
      lowerLimit(2) match { case DailyPrice(20130220L, 20.0) => }
      lowerLimit(3) match { case DailyPrice(20130219L, 20.0) => }
      lowerLimit(4) match { case DailyPrice(20130218L, 20.0) => }
      lowerLimit(5) match { case DailyPrice(20130217L, 20.0) => }
      lowerLimit(6) match { case DailyPrice(20130216L, 20.0) => }
    }
  }

  val channelLength = 3

  val prices = Prices(List(
    Price("FOO", 20130222L, 0.0, 0.0, 0.0, 30.0, 1.0),

    Price("FOO", 20130221L, 0.0, 0.0, 0.0, 22.0, 1.0),
    Price("FOO", 20130220L, 0.0, 0.0, 0.0, 21.0, 1.0),
    Price("FOO", 20130219L, 0.0, 0.0, 0.0, 20.0, 1.0),

    Price("FOO", 20130218L, 0.0, 0.0, 0.0, 20.0, 1.0),
    Price("FOO", 20130217L, 0.0, 0.0, 0.0, 22.0, 1.0),
    Price("FOO", 20130216L, 0.0, 0.0, 0.0, 21.0, 1.0),

    Price("FOO", 20130215L, 0.0, 0.0, 0.0, 20.0, 1.0),
    Price("FOO", 20130214L, 0.0, 0.0, 0.0, 21.0, 1.0),
    Price("FOO", 20130213L, 0.0, 0.0, 0.0, 22.0, 1.0))
  )

}
