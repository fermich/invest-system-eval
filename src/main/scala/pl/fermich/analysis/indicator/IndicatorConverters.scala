package pl.fermich.analysis.indicator

import pl.fermich.analysis.data.{Price, DailyPrice}

object IndicatorConverters {
  
  implicit def doubleList2MovingAverage(numbers: List[Double]) = new MovingAverage(numbers)
  implicit def doubleList2Statistics(numbers: List[Double]) = new Statistics(numbers)

  implicit def priceList2PriceListOperations(numbers: List[DailyPrice]) = new PriceCurve(numbers)
  implicit def priceList2PriceOscillators(prices: List[DailyPrice]) = new PriceOscillator(prices)

  implicit def priceList2Oscillators(prices: List[Price]) = new Oscillator(prices)
  implicit def priceList2DirectionalMovement(prices: List[Price]) = new Directional(prices)

}