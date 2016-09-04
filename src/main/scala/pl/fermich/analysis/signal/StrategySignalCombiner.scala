package pl.fermich.analysis.signal


case class CombinedStrategiesSignalGenerator(signalGenerators: List[StrategySignalCombiner], signalFilter: TradingSignalFilter) extends TradingSignalGenerator {
  def emit(): List[TradingSignal] =
    signalFilter.filter(signalGenerators
      .foldLeft[List[TradingSignal]](List())((prevSignals: List[TradingSignal], signalComposer: StrategySignalCombiner) =>
        unique(signalComposer.combine(prevSignals))
    ))

  def unique(signals: List[TradingSignal]): List[TradingSignal] = {
    signals.groupBy(_.date).mapValues((dupSignals: List[TradingSignal]) =>
      if (dupSignals.length > 1) dupSignals
        .find(_.signal == SignalType.SELL)
        .getOrElse(TradingSignal(dupSignals.head.date, SignalType.BUY))    //favor SELL signal if found
      else
        dupSignals.head
    ).values.toList.sortBy(-_.date)
  }
}


case class StrategySignalGenerator(signalCombiner: SignalCombiner, signalGenerator: TradingSignalGenerator) extends StrategySignalCombiner {
  def combine(signals: List[TradingSignal]): List[TradingSignal] =
    signalCombiner.merge(signals, signalGenerator.emit()).sortBy(-_.date)
}

case class OrderSignalGenerator(signalCombiner: SignalCombiner, orderSignals: ConditionalOrder) extends StrategySignalCombiner {
  def combine(signals: List[TradingSignal]): List[TradingSignal] =
    signalCombiner.merge(signals, orderSignals.generate(signals))
}


trait StrategySignalCombiner {
  def combine(signals: List[TradingSignal]): List[TradingSignal]
}
