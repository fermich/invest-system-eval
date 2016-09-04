package pl.fermich.analysis.signal

import pl.fermich.analysis.indicator.IndicatorConverters._
import pl.fermich.analysis.signal.SignalConverters._
import pl.fermich.analysis.strategy._
import pl.fermich.analysis.strategy.ChannelBreakoutStrategy
import pl.fermich.analysis.strategy.BollingerBandsStrategy
import pl.fermich.analysis.strategy.VamaTwoLineStrategy
import pl.fermich.analysis.data.DailyPrice


class VamaSignalGenerator(strategy: VamaTwoLineStrategy, signalFilter: TradingSignalFilter) extends TradingSignalGenerator {
  override def emit() = {
    val secLong: List[DailyPrice] = strategy.longLine()
    val secShort: List[DailyPrice] = strategy.shortLine()

    val signals = secShort INTERSECT secLong
    signalFilter.filter(signals)
  }
}

case class MacdSignalGenerator(strategy: MovingAverageTwoLineStrategy, signalFilter: TradingSignalFilter) extends TradingSignalGenerator {
  override def emit() = {
    val secLong: List[DailyPrice] = strategy.longLine()
    val secShort: List[DailyPrice] = strategy.shortLine()

    val signals = secShort CD_HISTOGRAM secLong

    signalFilter.filter(signals.toList)
  }
}

class BollingerBandsSignalGenerator(strategy: BollingerBandsStrategy, signalFilter: TradingSignalFilter) extends TradingSignalGenerator {
  override def emit() = {
    val upperBand: List[DailyPrice] = strategy.upperBand()
    val lowerBand: List[DailyPrice] = strategy.lowerBand()
    val prices: List[DailyPrice] = strategy.prices()

    val sellSignals = (prices CD_HISTOGRAM upperBand).filter(_.signal == SignalType.SELL)
    val buySignals = (prices CD_HISTOGRAM lowerBand).filter(_.signal == SignalType.BUY)

    val signals = (sellSignals ::: buySignals).sortBy(- _.date)
    signalFilter.filter(signals)
  }
}

class ChannelBreakoutSignalGenerator(strategy: ChannelBreakoutStrategy, signalFilter: TradingSignalFilter) extends TradingSignalGenerator {
  override def emit() = {
    val upperLimit: List[DailyPrice] = strategy.upperLimit()
    val lowerLimit: List[DailyPrice] = strategy.lowerLimit()
    val prices: List[DailyPrice] = strategy.prices()

    val sellSignals = {
      val lowPrices = lowerLimit zip prices
      lowPrices.flatMap{ case (lowPrice, price) =>
        if (lowPrice.value > price.value)
          Some(TradingSignal(price.date, SignalType.SELL))
        else None
      }
    }

    val buySignals =  {
      val upperPrices = upperLimit zip prices
      upperPrices.flatMap{ case (upperPrice, price) =>
        if (upperPrice.value < price.value)
          Some(TradingSignal(price.date, SignalType.BUY))
        else None
      }
    }

    val signals = (sellSignals ::: buySignals).sortBy(- _.date)
    signalFilter.filter(signals)
  }
}

class PdiNdiSignalGenerator(strategy: PdiNdiStrategy, signalFilter: TradingSignalFilter) extends TradingSignalGenerator {
  override def emit() = {
    val pdi: List[DailyPrice] = strategy.pdi()
    val ndi: List[DailyPrice] = strategy.ndi()

    val signals = pdi CD_HISTOGRAM ndi

    if (!signals.isEmpty) signalFilter.filter(signals)
    else signals
  }
}


trait TradingSignalGenerator {
  def emit(): List[TradingSignal]
}
