package pl.fermich.analysis.signal

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito

@RunWith(classOf[JUnitRunner])
class CombinedStrategiesSignalGeneratorSpec extends FunSpec with GivenWhenThen with MockitoSugar  {

  describe("A combined strategies generator joins signals using combiners") {
    val signals = List[TradingSignal](
      TradingSignal(20130330, 0),
      TradingSignal(20130327, 1),
      TradingSignal(20130326, 0),
      TradingSignal(20130321, 1)
    )

    val stopOrderSignals = List[TradingSignal](
      TradingSignal(20130330, 0),
      TradingSignal(20130327, 1),
      TradingSignal(20130325, 0),
      TradingSignal(20130322, 1)
    )

    it("should combine signals according to rules set by filters") {
      Given("signal generators, stop order signal generator")

      val signalGenerator1Mock = mock[TradingSignalGenerator]
      Mockito.when(signalGenerator1Mock.emit()).thenReturn(signals)

      val orderSignalsMock = mock[ConditionalOrder]
      Mockito.when(orderSignalsMock.generate(signals)).thenReturn(stopOrderSignals)

      val compoundSignals = CombinedStrategiesSignalGenerator(List(
        StrategySignalGenerator(RightJoin, signalGenerator1Mock),
        OrderSignalGenerator(RightInLeftJoin, orderSignalsMock)
      ), new LastBuyFirstSellSignalFilter{})

      When("emit is called")
      val compound = compoundSignals.emit()

      Then("Compound generator returns correct signals")
      compound(0) match { case TradingSignal(20130330, 0) => }
      compound(1) match { case TradingSignal(20130327, 1) => }
      compound(2) match { case TradingSignal(20130325, 0) => }
      compound(3) match { case TradingSignal(20130322, 1) => }
    }
  }

  describe("A combined strategies generator removes duplicated signals") {
    val generator1Signals = List[TradingSignal](
      TradingSignal(20130329, 1),
      TradingSignal(20130328, 0),
      TradingSignal(20130327, 1)
    )

    val generator2Signals = List[TradingSignal](
      TradingSignal(20130329, 0),
      TradingSignal(20130328, 1),
      TradingSignal(20130327, 1)
    )

    it("should remove buy signals when sell is present") {
      Given("two signal generators")

      val signalGenerator1Mock = mock[TradingSignalGenerator]
      Mockito.when(signalGenerator1Mock.emit()).thenReturn(generator1Signals)

      val signalsGenerator2Mock = mock[TradingSignalGenerator]
      Mockito.when(signalsGenerator2Mock.emit()).thenReturn(generator2Signals)

      val compoundSignals = CombinedStrategiesSignalGenerator(List(
        StrategySignalGenerator(Union, signalGenerator1Mock),
        StrategySignalGenerator(Union, signalsGenerator2Mock)
      ), new EmptyFilter {})

      When("emit is called")
      val compound = compoundSignals.emit()

      Then("Compound generator returns correct signals")
      compound(0) match { case TradingSignal(20130329, 0) => }
      compound(1) match { case TradingSignal(20130328, 0) => }
      compound(2) match { case TradingSignal(20130327, 1) => }
    }
  }

}
