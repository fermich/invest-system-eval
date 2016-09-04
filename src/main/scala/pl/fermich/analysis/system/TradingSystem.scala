package pl.fermich.analysis.system

import pl.fermich.analysis.signal._
import pl.fermich.analysis.strategy.{PdiNdiStrategy, ChannelBreakoutStrategy, BollingerBandsStrategy, VamaTwoLineStrategy}


class VamaTradingSystem(strategy: VamaTwoLineStrategy) extends TradingSystemDualInOut {
  val entrySignals = new VamaSignalGenerator(strategy, new EntrySignalFilter{})
  val stopSignals = new VamaSignalGenerator(strategy, new StopSignalFilter{})
}

class VamaTradingSystemWithMACD(strategy: VamaTwoLineStrategy) extends TradingSystemSingleInOut {
  val signals = new MacdSignalGenerator(strategy, new FirstBuyFirstSellSignalFilter{})
}

class VamaWithTrailingStopSystem(strategy: VamaTwoLineStrategy, threshold: Double) extends TradingSystemSingleInOut {
  val signals = CombinedStrategiesSignalGenerator(List(
    StrategySignalGenerator(RightJoin, MacdSignalGenerator(strategy, new FirstBuyFirstSellSignalFilter{})),
    OrderSignalGenerator(RightInLeftJoin, TrailingStopOrder(strategy.prices(), threshold))
  ), new FirstBuyFirstSellSignalFilter{})
}

class BollingerBandsSystem(strategy: BollingerBandsStrategy) extends TradingSystemSingleInOut {
  val signals = new BollingerBandsSignalGenerator(strategy, new FirstBuyFirstSellSignalFilter{})
}

class ChannelBreakoutSystem(strategy: ChannelBreakoutStrategy) extends TradingSystemSingleInOut {
  val signals = new ChannelBreakoutSignalGenerator(strategy, new FirstBuyFirstSellSignalFilter{})
}

class PdiNdiSystem(strategy: PdiNdiStrategy) extends TradingSystemSingleInOut {
  val signals = new PdiNdiSignalGenerator(strategy, new FirstBuyFirstSellSignalFilter{})
}
