package pl.fermich.analysis.signal


trait TradingSignalFilter {
  def filter(signals: List[TradingSignal]): List[TradingSignal]
}

trait EntrySignalFilter extends TradingSignalFilter {
  override def filter(signals: List[TradingSignal]) = {
    signals.filter(_.signal == SignalType.BUY)
  }
}

trait StopSignalFilter extends TradingSignalFilter {
  override def filter(signals: List[TradingSignal]) = {
    signals.filter(_.signal == SignalType.SELL)
  }
}

trait EmptyFilter extends TradingSignalFilter {
  override def filter(signals: List[TradingSignal]) = signals
}

case class BuySignalsSinceDateFilter(val since: Long) extends TradingSignalFilter {
  override def filter(signals: List[TradingSignal]) =
    signals.filter(s => s.date > since && s.signal == SignalType.BUY)
}

trait FirstBuyFirstSellSignalFilter extends TradingSignalFilter {
  override def filter(signals: List[TradingSignal]) = {
    if (signals isEmpty) signals
    else {
      val sigs = signals.scanRight(signals.last)((s1, s2) =>
        if (s1.signal == s2.signal) s2 else s1
      )
      sigs.distinct.sortBy(- _.date)
    }
  }
}

trait LastBuyFirstSellSignalFilter extends TradingSignalFilter {
  override def filter(signals: List[TradingSignal]) =
    if (signals isEmpty) signals
    else {
      val curPrevSignals = signals zip signals.tail
      curPrevSignals.flatMap{ case(current, previous) =>
        if (previous.signal == SignalType.BUY && current.signal == SignalType.SELL) List(current, previous)
        else None
      }.distinct.sortBy(- _.date)
    }
}

trait LastInTheSameSignalSequenceFilter extends TradingSignalFilter {
  override def filter(signals: List[TradingSignal]) = {
    val sigs = signals.tail.scanLeft(signals.head)((s1, s2) =>
      if (s1.signal == s2.signal) s1 else s2
    )
    sigs.distinct
  }
}
