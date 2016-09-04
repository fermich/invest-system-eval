package pl.fermich.analysis.signal

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SignalCombinerSpec extends FunSpec with GivenWhenThen {

  describe("RightInLeftJoin operator") {
    it("should return right signals found in boundaries of lefts") {
      Given("left and right signals")

      When("merge is called")
      val joined = RightInLeftJoin.merge(leftSignals, rightSignals)

      Then("signals as a result of merging should be as ")
      joined(0) match { case TradingSignal(20130328, 0) => }
      joined(1) match { case TradingSignal(20130327, 1) => }
      joined(2) match { case TradingSignal(20130315, 0) => }
      joined(3) match { case TradingSignal(20130310, 1) => }
    }
  }

  val leftSignals = List(
    TradingSignal(20130328, SignalType.SELL),
    TradingSignal(20130326, SignalType.BUY),

    TradingSignal(20130321, SignalType.SELL),
    TradingSignal(20130310, SignalType.BUY)
  )

  val rightSignals = List(
    TradingSignal(20130328, SignalType.SELL),
    TradingSignal(20130327, SignalType.BUY),
    TradingSignal(20130326, SignalType.SELL),

    TradingSignal(20130324, SignalType.BUY),
    TradingSignal(20130323, SignalType.SELL),

    TradingSignal(20130321, SignalType.BUY),
    TradingSignal(20130315, SignalType.SELL),
    TradingSignal(20130310, SignalType.BUY)
  )
}
