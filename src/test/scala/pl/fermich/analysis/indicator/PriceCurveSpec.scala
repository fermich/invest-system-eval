package pl.fermich.analysis.indicator

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.indicator.IndicatorConverters._
import pl.fermich.analysis.data.DailyPrice
import pl.fermich.analysis.signal.TradingSignal

@RunWith(classOf[JUnitRunner])
class PriceCurveSpec extends FunSpec with GivenWhenThen {
  describe("An INTERSECT() operator") {
    it("should return Buy and Sell signals") {
      
      Given("two price lists with dates")
      val short = List[DailyPrice](DailyPrice(20130227L, 5.0), DailyPrice(20130226L, 10.0), DailyPrice(20130225L, 20.0), DailyPrice(20130224L, 30.0), DailyPrice(20130223L, 40.0),
          DailyPrice(20130222L, 30.0), DailyPrice(20130221L, 20.0), DailyPrice(20130220L, 10.0))
      val long = List[DailyPrice](DailyPrice(20130226L, 45.0), DailyPrice(20130226L, 40.0), DailyPrice(20130225L, 30.0), DailyPrice(20130224L, 20.0), DailyPrice(20130223L, 10.0),
          DailyPrice(20130222L, 20.0), DailyPrice(20130221L, 30.0), DailyPrice(20130220L, 40.0), DailyPrice(20130220L, 45.0))
      
      When("INTERSECT is applied on price lists")
      val intersections = short INTERSECT long
      
      Then("Buy and Sell signals should be received")
      intersections.foreach(_ match {
        case TradingSignal(20130225, 0) =>
        case TradingSignal(20130222, 1) =>
      })
    }
  }

  describe("Conv/Div indicator") {
    it("should return convergences and divergences between smoothed prices") {

      Given("two lists reflecting MAs")
      val short = List[DailyPrice](DailyPrice(20130226L, 10.0), DailyPrice(20130225L, 20.0), DailyPrice(20130224L, 30.0), DailyPrice(20130223L, 40.0),
        DailyPrice(20130222L, 30.0), DailyPrice(20130221L, 20.0), DailyPrice(20130220L, 10.0))
      val long = List[DailyPrice](DailyPrice(20130226L, 40.0), DailyPrice(20130225L, 30.0), DailyPrice(20130224L, 20.0), DailyPrice(20130223L, 10.0),
        DailyPrice(20130222L, 20.0), DailyPrice(20130221L, 30.0), DailyPrice(20130220L, 40.0))

      When("CONV_DIV is applied on MAs")
      val covdiv = short CONV_DIV long

      Then("Specified convergences and divergences should be returned")
      covdiv.foreach(_ match {
        case DailyPrice(20130226, -30.0) =>
        case DailyPrice(20130225, -10.0) =>
        case DailyPrice(20130224, 10.0) =>
        case DailyPrice(20130223, 30.0) =>
        case DailyPrice(20130222, 10.0) =>
        case DailyPrice(20130221, -10.0) =>
        case DailyPrice(20130220, -30.0) =>
      })
    }
  }

}