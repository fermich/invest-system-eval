package pl.fermich.analysis.signal

import pl.fermich.analysis.data.DailyPrice
import collection.mutable.ListBuffer
import pl.fermich.analysis.indicator.IndicatorConverters._

case class SignalDetector(val priceData: List[DailyPrice]) {

  def CD_HISTOGRAM(secLong: List[DailyPrice]): List[TradingSignal] = {
    val signals = ListBuffer[TradingSignal]()
    val convs = priceData CONV_DIV secLong

    convs.reduceLeft((c1, c2) => {
      if (c1.value < 0 && c2.value >= 0) signals += TradingSignal(c1.date, SignalType.SELL)
      if (c1.value > 0 && c2.value <= 0) signals += TradingSignal(c1.date, SignalType.BUY)
      c2
    })
    signals.toList
  }

  def TRAILING_STOP(threshold: Double): Option[TradingSignal] = {
    val maxDailyPrices = priceData.scanRight(DailyPrice(0L, 0.0)){ case(currentPrice, lastMaxPrice) =>
      if(currentPrice.value < lastMaxPrice.value) DailyPrice(currentPrice.date, lastMaxPrice.value)
      else currentPrice
    }.init

    val rocs = priceData CONV_DIV_ROC maxDailyPrices
    val trailingStop = rocs.reverse.find(_.value.abs >= threshold)
    trailingStop match {
      case Some(trailingStop) => Some(TradingSignal(trailingStop.date, SignalType.SELL))
      case _ => None
    }
  }

  def BREAKOUT(upperPrices: List[DailyPrice]): List[TradingSignal] = {
    val signals = ListBuffer[TradingSignal]()
    val joined = priceData zip upperPrices

    joined.foreach{ case(price, upPrice) => {
      require(price.date == upPrice.date, "Inconsistent data! Wrong Dates during BREAKOUT search!")
      if (price.value < upPrice.value) signals += TradingSignal(upPrice.date, SignalType.BUY)
    }}
    signals.toList
  }

  def BREAKDOWN(lowerPrices: List[DailyPrice]): List[TradingSignal] = {
    val signals = ListBuffer[TradingSignal]()
    val joined = priceData zip lowerPrices

    joined.foreach{ case(price, lowPrice) => {
      require(price.date == lowPrice.date, "Inconsistent data! Wrong Dates during BREAKDOWN search!")
      if (price.value > lowPrice.value) signals += TradingSignal(lowPrice.date, SignalType.SELL)
    }}
    signals.toList
  }

}
