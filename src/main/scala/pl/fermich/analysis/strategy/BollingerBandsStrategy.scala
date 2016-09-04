package pl.fermich.analysis.strategy

import pl.fermich.analysis.data.{DailyPrice, MarketData, PriceMapper}
import pl.fermich.analysis.data.PriceConverters._
import pl.fermich.analysis.data.PriceExtractor._


case class BollingerBandsStrategy(val marketData: MarketData, val length: Int, val k: Int) extends TradingStrategy {
  private val quotes = marketData.getData()
  require(length <= quotes.size,"The Bollinger Bands length exceeds price array size! Expected size <= " + quotes.size)

  val onClosePrices = PriceMapper(quotes, PriceOnClose)
  val averages = onClosePrices SMA length
  val deviations = onClosePrices SD length
  val avgsDevs = averages zip deviations

  def upperBand(): List[DailyPrice] = {
    avgsDevs.map { case ((avgDate: Long, avgVal: Double), (devDate: Long, devVal: Double)) => {
      if (avgDate == devDate) DailyPrice(avgDate, avgVal + k * devVal) else sys.error("Data inconsistency! Wrong Dates!")
    }}
  }

  def lowerBand(): List[DailyPrice] = {
    avgsDevs.map { case ((avgDate: Long, avgVal: Double), (devDate: Long, devVal: Double)) => {
      if (avgDate == devDate) DailyPrice(avgDate, avgVal - k * devVal) else sys.error("Data inconsistency! Wrong Dates!")
    }}
  }

  override def prices(): List[DailyPrice] = onClosePrices.select()

  override def toString() = "BB " + length + " " + k
}
