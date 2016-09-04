package pl.fermich.analysis.strategy

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.data.DailyPrice

@RunWith(classOf[JUnitRunner])
class TradingStrategySpec extends FunSpec with GivenWhenThen {

  describe("A trading strategy returns prices for given dates") {
    it("should return correct prices for dates") {

      Given("implementation of trading strategy returning test prices")
      val strategy = new TradingStrategy {
        override def prices() = List(DailyPrice(20130222L, 30.0), DailyPrice(20130221L, 20.0), DailyPrice(20130220L, 10.0))
      }

      When("getting prices for given dates")
      val prices = strategy.getPricesForDates(List[Long](20130222L, 20130221L, 20130220L, 20130219L))

      Then("returns signals in preserved order")
      assert(prices.size === 3)
      prices(0) match { case DailyPrice(20130222L, 30.0) => }
      prices(1) match { case DailyPrice(20130221L, 20.0) => }
      prices(2) match { case DailyPrice(20130220L, 10.0) => }
    }
  }

}
