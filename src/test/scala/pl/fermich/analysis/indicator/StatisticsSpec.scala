package pl.fermich.analysis.indicator

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.indicator.IndicatorConverters._

@RunWith(classOf[JUnitRunner])
class StatisticsSpec extends FunSpec with GivenWhenThen {
  describe("A SD (Standard Deviation) operator") {
    it("should calculate correct standard deviation") {

      Given("period, prices")
      val period = 5
      val prices = List[Double](3.0, 3.0, 4.0, 5.0, 5.0)

      When("SD is applied")
      val sd = prices.SD(period)

      Then("standard deviation must be equal to proper value")
      assert(sd(0) == 1.0)
    }
  }
}
