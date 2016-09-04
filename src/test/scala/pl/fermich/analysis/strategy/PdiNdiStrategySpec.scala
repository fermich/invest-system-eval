package pl.fermich.analysis.strategy

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.data.{DailyPrice, Price, Prices}


@RunWith(classOf[JUnitRunner])
class PdiNdiStrategySpec extends FunSpec with GivenWhenThen {

  describe("+DI, -DI are used to determine whether a security is in trending or not") {
    it("should return +DI indicators") {
      Given("prices")
      val strategy = new PdiNdiStrategy(prices, length)

      When("pdi is applied")
      val pdi = strategy.pdi()

      Then("returns correct +DI values")
      pdi(0) match { case DailyPrice(20130326L, 25.0) => }
      pdi(1) match { case DailyPrice(20130325L, 25.0) => }
      pdi(3) match { case DailyPrice(20130323L, 0.0) => }
      pdi(4) match { case DailyPrice(20130319L, 0.0) => }
    }
  }

  it("should return -DI indicators") {
    Given("prices")
    val strategy = new PdiNdiStrategy(prices, length)

    When("ndi is applied")
    val ndi = strategy.ndi()

    Then("returns correct -DI values")
    ndi(0) match { case DailyPrice(20130326L, 0.0) => }
    ndi(1) match { case DailyPrice(20130325L, 0.0) => }
    ndi(3) match { case DailyPrice(20130323L, 25.0) => }
    ndi(4) match { case DailyPrice(20130319L, 25.0) => }
  }


  val length: Int = 2

  val prices = Prices(List(
  Price("FOO", 20130326L, 0.0, 9.0, 5.0, 7.0, 0.0),
  Price("FOO", 20130325L, 0.0, 8.0, 4.0, 6.0, 0.0),
  Price("FOO", 20130324L, 0.0, 7.0, 3.0, 5.0, 0.0),
  Price("FOO", 20130323L, 0.0, 6.0, 2.0, 4.0, 0.0),
  Price("FOO", 20130319L, 0.0, 7.0, 3.0, 5.0, 0.0),
  Price("FOO", 20130318L, 0.0, 8.0, 4.0, 6.0, 0.0),
  Price("FOO", 20130317L, 0.0, 9.0, 5.0, 7.0, 0.0)))

}
