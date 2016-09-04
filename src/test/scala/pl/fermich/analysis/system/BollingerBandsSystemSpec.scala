package pl.fermich.analysis.system

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import pl.fermich.analysis.signal.{TradingSignal}
import pl.fermich.analysis.strategy.{BollingerBandsStrategy}
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import pl.fermich.analysis.data.{DailyPrice}

@RunWith(classOf[JUnitRunner])
class BollingerBandsSystemSpec extends FunSpec with GivenWhenThen with MockitoSugar {

  describe("A trading system based on classic bollinger bands") {
    it("should generate signals in descending order") {

      val strategy: BollingerBandsStrategy = mock[BollingerBandsStrategy]
      Mockito.when(strategy.prices()).thenReturn(prices)
      Mockito.when(strategy.upperBand()).thenReturn(upperBand)
      Mockito.when(strategy.lowerBand()).thenReturn(lowerBand)

      val system = new BollingerBandsSystem(strategy)
      Given("bollinger bands system")

      When("getting signals")
      val signals = system.getSignals()

      Then("The system returns signals in descending order")
      signals(0) match { case TradingSignal(20130329, 0) => }
      signals(1) match { case TradingSignal(20130327, 1) => }
      signals(2) match { case TradingSignal(20130325, 0) => }
    }
  }

  val prices = List[DailyPrice](
    DailyPrice(20130329, 7.0),
    DailyPrice(20130328, 8.0),
    DailyPrice(20130327, 6.0),
    DailyPrice(20130326, 4.0),
    DailyPrice(20130325, 5.0),
    DailyPrice(20130324, 7.0),
    DailyPrice(20130323, 3.0)
  )

  val upperBand = List[DailyPrice](
    DailyPrice(20130329, 8.0),
    DailyPrice(20130328, 7.0),
    DailyPrice(20130327, 7.0),
    DailyPrice(20130326, 7.0),
    DailyPrice(20130325, 6.0),
    DailyPrice(20130324, 5.0),
    DailyPrice(20130323, 4.0)
  )

  val lowerBand = List[DailyPrice](
    DailyPrice(20130329, 6.0),
    DailyPrice(20130328, 5.0),
    DailyPrice(20130327, 5.0),
    DailyPrice(20130326, 5.0),
    DailyPrice(20130325, 4.0),
    DailyPrice(20130324, 3.0),
    DailyPrice(20130323, 2.0)
  )
}
