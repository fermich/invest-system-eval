package pl.fermich.analysis.signal

import pl.fermich.analysis.data.{DailyPrice}
import pl.fermich.analysis.signal.SignalConverters._


case class TrailingStopOrder(prices: List[DailyPrice], threshold: Double) extends ConditionalOrder {
  def generate(signals: List[TradingSignal]): List[TradingSignal] = {
    if (!signals.isEmpty) {
      val currentNextSignalPairs = signals.tail zip signals
      val startedWithBuySignal = currentNextSignalPairs.filter { case (curSignal: TradingSignal, nextSignal: TradingSignal) => {
        if (curSignal.signal == SignalType.BUY) true
        else false
      }}

      val trailingStops = startedWithBuySignal.flatMap{ case (buySignal, sellSignal) => {
        val dailyPrices = prices.filter(dailyPrice => { dailyPrice.date >= buySignal.date && dailyPrice.date <= sellSignal.date })
        dailyPrices TRAILING_STOP threshold
      }}
      (signals ::: trailingStops).sortBy(-_.date)
    } else {
      signals
    }
  }

}


trait ConditionalOrder {
  def generate(signals: List[TradingSignal]): List[TradingSignal]
}
