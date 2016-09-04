package pl.fermich.analysis.system

import pl.fermich.analysis.signal._
import com.typesafe.scalalogging.slf4j.{Logging}


trait TradingSystem extends Logging {
  def getSignals(): List[TradingSignal]
}

trait TradingSystemDualInOut extends TradingSystem {
  val entrySignals: TradingSignalGenerator
  val stopSignals: TradingSignalGenerator

  override def getSignals(): List[TradingSignal] = {
    val signals = stopSignals.emit() ::: entrySignals.emit()
    val filter = new LastInTheSameSignalSequenceFilter{}
    filter.filter(signals.sortBy(- _.date))
  }
}

trait TradingSystemSingleInOut extends TradingSystem {
  val signals: TradingSignalGenerator

  override def getSignals(): List[TradingSignal] = {
    signals.emit()
  }
}
