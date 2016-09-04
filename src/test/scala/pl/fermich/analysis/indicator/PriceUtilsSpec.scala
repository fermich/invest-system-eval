package pl.fermich.analysis.indicator

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.data.PriceUtils

@RunWith(classOf[JUnitRunner])
class PriceUtilsSpec extends FunSpec with GivenWhenThen {
  describe("A normalize() function") {

    it("should divide prices by their sum") {
      Given("doubles list with at least one non zero element")
      val values = List[Double](10.0, 20.0, 30.0, 40.0)
      
      When("normalized() is called on the list")
      Then("the values must be normalized")

      PriceUtils.normalize(values) match {
        case Some(normalized) => {
          assert(normalized(0) == 0.1)
          assert(normalized(1) == 0.2)
          assert(normalized(2) == 0.3)
          assert(normalized(3) == 0.4)
        }
        case None => fail()
      }
    }

    it("should return nothing on zeroed list") {
      Given("double list with zeros")
      val values = List[Double](0.0, 0.0, 0.0, 0.0)

      When("normalized() is called on the list")
      Then("None should be returned")

      PriceUtils.normalize(values) match {
        case None =>
        case _ => fail()
      }
    }
  }
}