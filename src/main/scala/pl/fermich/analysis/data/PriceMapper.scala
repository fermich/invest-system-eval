package pl.fermich.analysis.data

import pl.fermich.analysis.indicator.IndicatorConverters._

object PriceExtractor {
  val PriceOnClose = (q: Price) => q.close
  val Volume = (q: Price) => q.vol
}

case class PriceMapper(prices: List[Price], extractor: Price => Double) {
  val dates = prices.map(_.date)
  val data = extract()

  def select() = dates zip data
  def extract() = prices.map(extractor)
  def closePrices() = prices.map(q => DailyPrice(q.date, q.close))

  def SMA(period: Int) = {
    val smoothed = data SMA period
    dates zip smoothed
  }

  def VAMA(period: Int) = {
    val volume = prices.map(_.vol)
    val smoothed = data VAMA(volume, period)
    dates zip smoothed
  }

  def VAMA(volume: List[Double], period: Int) = {
    val smoothed = data VAMA(volume, period)
    dates zip smoothed
  }

  def EMA(period: Int) = {
    val smoothed = data EMA(period)
    dates zip smoothed
  }

  def EMA(weights: List[Double], period: Int) = {
    val smoothed = data EMA(weights, period)
    dates zip smoothed
  }

  def SD(period: Int) = {
    val deviations = data SD(period)
    dates zip deviations
  }

}
