package pl.fermich.analysis.indicator

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.indicator.IndicatorConverters._
import pl.fermich.analysis.data.{Price, DailyPrice}

@RunWith(classOf[JUnitRunner])
class OscillatorSpec extends FunSpec with GivenWhenThen {

  describe("The MOMENTUM oscillator") {
    it("shows the difference between today's closing price and the close N days ago") {

      Given("price list")

      When("MOMENTUM is applied on the list")
      val momentum: List[DailyPrice] = dailyPrices MOMENTUM 2

      Then("returned differences are equal to correct values")
      momentum(0) match { case DailyPrice(20130326, 16.0) => }
      momentum(1) match { case DailyPrice(20130325, 12.0) => }
      momentum(2) match { case DailyPrice(20130324, 8.0) => }
      momentum(3) match { case DailyPrice(20130323, 8.0) => }
    }
  }

  describe("The ROC oscillator") {
    it("shows the rate of change between today's closing price and the close N days ago") {

      Given("price list")

      When("ROC is applied on the list")
      val roc: List[DailyPrice] = dailyPrices ROC 2

      Then("returned rates are equal to correct values")
      roc(0) match { case DailyPrice(20130326, 1.0) => }
      roc(1) match { case DailyPrice(20130325, 1.0) => }
      roc(2) match { case DailyPrice(20130324, 1.0) => }
      roc(3) match { case DailyPrice(20130323, 2.0) => }
    }
  }

  describe("The WILLIAMS %R oscillator") {
    it("shows the current closing price in relation to the high and low of the past N days") {

      Given("price list")

      When("WILLIAMS is applied on the list")
      val williams: List[DailyPrice] = prices WILLIAMS 2

      Then("returned relations are equal to correct values")
      williams(0) match { case DailyPrice(20130326, 1.0) => }
      williams(1) match { case DailyPrice(20130325, 1.0) => }
      williams(2) match { case DailyPrice(20130324, 1.0) => }
    }
  }

  val prices = List(
    //sym, date, open, high, low, close, vol
    Price("FOO", 20130326L, 0.0, 9.0, 7.0, 5.0, 0.0),
    Price("FOO", 20130325L, 0.0, 8.0, 6.0, 4.0, 0.0),
    Price("FOO", 20130324L, 0.0, 7.0, 5.0, 3.0, 0.0),
    Price("FOO", 20130323L, 0.0, 6.0, 4.0, 2.0, 0.0),
    Price("FOO", 20130322L, 0.0, 5.0, 3.0, 1.0, 0.0))

  val dailyPrices = List(
    DailyPrice(20130326L, 32.0),
    DailyPrice(20130325L, 24.0),
    DailyPrice(20130324L, 16.0),
    DailyPrice(20130323L, 12.0),
    DailyPrice(20130322L, 08.0),
    DailyPrice(20130321L, 04.0))

}
