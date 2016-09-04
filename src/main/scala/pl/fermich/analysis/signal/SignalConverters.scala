package pl.fermich.analysis.signal

import pl.fermich.analysis.data.DailyPrice

object SignalConverters {

  implicit def priceList2SignalDetector(prices: List[DailyPrice]) = new SignalDetector(prices)

}
