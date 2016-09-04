package pl.fermich.analysis.signal


object RightInLeftJoin extends SignalCombiner {
  override def merge(leftSignals: List[TradingSignal], rightSignals: List[TradingSignal]): List[TradingSignal] = {
    if (leftSignals.isEmpty) List()
    else {
      val buySellLeftSignals = (leftSignals.tail zip leftSignals).filter(_._1.signal == SignalType.BUY)
      val joined = buySellLeftSignals.flatMap{ case (leftBuySignal, leftSellSignal) => {
        val rightInLeftSignals = rightSignals.filter((rightSignal: TradingSignal) =>
          rightSignal.date >= leftBuySignal.date && rightSignal.date <= leftSellSignal.date).sortBy(-_.date)
        discardSingles(rightInLeftSignals)
      }}.distinct
      joined.sortBy(-_.date)
    }
  }

  def discardSingles(signals: List[TradingSignal]): List[TradingSignal] = {
    if (signals.length <= 1) List()
    else {
      val buySellSignals = (signals.tail zip signals)
        .filter((sig) => sig._1.signal == SignalType.BUY && sig._2.signal == SignalType.SELL)   //assumes correct BUY and SELL pairs sequence
      buySellSignals.flatMap(buySell => List(buySell._1, buySell._2))
    }
  }
}

object RightJoin extends SignalCombiner {
  override def merge(leftSignals: List[TradingSignal], rightSignals: List[TradingSignal]): List[TradingSignal] = rightSignals
}

object Union extends SignalCombiner {
  override def merge(leftSignals: List[TradingSignal], rightSignals: List[TradingSignal]): List[TradingSignal] =
    leftSignals ::: rightSignals
}


trait SignalCombiner {
  def merge(leftSignals: List[TradingSignal], rightSignals: List[TradingSignal]): List[TradingSignal]
}
