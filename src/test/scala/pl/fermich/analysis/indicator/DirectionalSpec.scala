package pl.fermich.analysis.indicator

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.indicator.IndicatorConverters._
import pl.fermich.analysis.data.Price

@RunWith(classOf[JUnitRunner])
class DirectionalSpec extends FunSpec with GivenWhenThen {

  val trPrices = List(
    //sym, date, open, high, low, close, vol
    Price("FOO", 20130326L, 0.0, 9.0, 5.0, 7.0, 0.0),
    Price("FOO", 20130325L, 0.0, 8.0, 6.0, 6.0, 0.0),
    Price("FOO", 20130324L, 0.0, 6.0, 4.0, 5.0, 0.0),
    Price("FOO", 20130323L, 0.0, 7.0, 2.0, 7.0, 0.0),
    Price("FOO", 20130322L, 0.0, 5.0, 1.0, 3.0, 0.0))

  describe("A True Range") {
    it("should calculate price volatility") {

      Given("prices")

      When("TR is applied")
      val tr = trPrices TR()

      Then("calculated volatility must be equal to expected values")
      //max[high - low, abs(high - closePrev), abs(low - closePrev)]
      assert(tr(0) == 4.0)   // max[4.0, 3.0, 1.0] = 4.0
      assert(tr(1) == 3.0)   // max[2.0, 3.0, 1.0] = 3.0
      assert(tr(2) == 3.0)   // max[2.0, 1.0, 3.0] = 3.0
      assert(tr(3) == 5.0)   // max[5.0, 4.0, 2.0] = 5.0
    }
  }

  describe("Average True Range") {
    it("should smooth price volatility") {

      Given("price volatility")

      When("ATR is applied")
      val atr = trPrices ATR 2

      Then("smoothed volatility must be equal to expected values")
      assert(atr(0) == 3.5)
      assert(atr(1) == 3.0)
      assert(atr(2) == 4.0)
    }
  }


  val dmPrices = List(
    //sym, date, open, high, low, close, vol
    Price("FOO", 20130326L, 0.0, 9.0, 5.0, 7.0, 0.0),
    Price("FOO", 20130325L, 0.0, 7.0, 6.0, 6.0, 0.0),
    Price("FOO", 20130324L, 0.0, 6.0, 4.0, 5.0, 0.0),
    Price("FOO", 20130323L, 0.0, 7.0, 2.0, 7.0, 0.0),
    Price("FOO", 20130322L, 0.0, 5.0, 5.0, 5.0, 0.0))

  describe("Positive Directional Movement") {
    it("yields the current high minus the prior high if it is greater than the prior low minus the current low or the 0.0 otherwise") {

      Given("prices")

      When("PDM is applied")
      val pdm = dmPrices PDM()

      Then("directional movement must be equal to expected values")
      assert(pdm(0) == 2.0)
      assert(pdm(1) == 1.0)
      assert(pdm(2) == 0.0)
      assert(pdm(3) == 0.0)
    }
  }

  describe("Negative Directional Movement") {
    it("yields the prior low minus the current low if it is greater than the current high minus the prior high or the 0.0 otherwise") {

      Given("prices")

      When("NDM is applied")
      val pdm = dmPrices NDM()

      Then("directional movement must be equal to expected values")
      assert(pdm(0) == 0.0)
      assert(pdm(1) == 0.0)
      assert(pdm(2) == 0.0)
      assert(pdm(3) == 3.0)
    }
  }


  val pdiPrices = List(
    //sym, date, open, high, low, close, vol
    Price("FOO", 20130326L, 0.0, 9.0, 5.0, 7.0, 0.0),
    Price("FOO", 20130325L, 0.0, 8.0, 4.0, 6.0, 0.0),
    Price("FOO", 20130324L, 0.0, 7.0, 3.0, 5.0, 0.0),
    Price("FOO", 20130323L, 0.0, 6.0, 2.0, 4.0, 0.0),
    Price("FOO", 20130322L, 0.0, 5.0, 1.0, 3.0, 0.0))

  describe("Positive Directional Indicator is used to detect direction of a trend") {
    it("Should divide smoothed PDM by ATR") {

      Given("prices")

      When("PDI is applied")
      val pdi = pdiPrices PDI 2

      Then("directional indicator must be equal to expected values")
      assert(pdi(0) == 25.0)
      assert(pdi(1) == 25.0)
      assert(pdi(2) == 25.0)
    }
  }


  val ndiPrices = List(
    //sym, date, open, high, low, close, vol
    Price("FOO", 20130321L, 0.0, 5.0, 1.0, 3.0, 0.0),
    Price("FOO", 20130320L, 0.0, 6.0, 2.0, 4.0, 0.0),
    Price("FOO", 20130319L, 0.0, 7.0, 3.0, 5.0, 0.0),
    Price("FOO", 20130318L, 0.0, 8.0, 4.0, 6.0, 0.0),
    Price("FOO", 20130317L, 0.0, 9.0, 5.0, 7.0, 0.0))

  describe("Negative Directional Indicator is used to detect direction of a trend") {
    it("Should divide smoothed NDM by ATR") {

      Given("prices")

      When("NDI is applied")
      val ndi = ndiPrices NDI 2

      Then("directional indicator must be equal to expected values")
      assert(ndi(0) == 25.0)
      assert(ndi(1) == 25.0)
      assert(ndi(2) == 25.0)
    }
  }


  val adxPrices = pdiPrices ++ ndiPrices
  describe("Average Directional Index is used to measure strength or weakness of a trend") {
    it("Should construct correct ADX line") {

      Given("prices")

      When("ADX is applied")
      val adx = adxPrices ADX 2

      Then("ADX values are as expected")
      assert(adx(0) == 100.0)
      assert(adx(1) == 100.0)
      assert(adx(2) == 100.0)
      assert(adx(3) == 100.0)
      assert(adx(4) == 100.0)
      assert(adx(5) == 100.0)
      assert(adx(6) == 100.0)
    }
  }

}