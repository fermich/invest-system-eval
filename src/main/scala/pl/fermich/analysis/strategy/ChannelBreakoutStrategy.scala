package pl.fermich.analysis.strategy

import pl.fermich.analysis.data.{DailyPrice, MarketData, PriceMapper}
import pl.fermich.analysis.data.PriceConverters._
import pl.fermich.analysis.data.PriceExtractor._

import scala.annotation.tailrec

case class ChannelBreakoutStrategy(val marketData: MarketData, val entryLength: Int, val stopLength: Int) extends TradingStrategy {
  private val quotes = marketData.getData()
  require(entryLength <= quotes.size && stopLength <= quotes.size, "The Channel length exceeds price array size! Expected size <= " + quotes.size)
  private val onClosePrices = PriceMapper(quotes, PriceOnClose)

  def upperLimit(): List[DailyPrice] = findMaxLimit(onClosePrices.select(), List()).reverse

  def lowerLimit(): List[DailyPrice] = findMinLimit(onClosePrices.select(), List()).reverse

  override def prices(): List[DailyPrice] = onClosePrices.select

  @tailrec
  private def findMaxLimit(prices: List[DailyPrice], maxPrices: List[DailyPrice]): List[DailyPrice] = {
    if (prices.size > entryLength) {
      val upperLimit: DailyPrice = prices.tail.take(entryLength).maxBy(_.value)
      findMaxLimit(prices.tail, DailyPrice(prices.head.date, upperLimit.value) :: maxPrices)
    } else {
      maxPrices
    }
  }

  @tailrec
  private def findMinLimit(prices: List[DailyPrice], minPrices: List[DailyPrice]): List[DailyPrice] = {
    if (prices.size > stopLength) {
      val lowerLimit: DailyPrice = prices.tail.take(stopLength).minBy(_.value)
      findMinLimit(prices.tail, DailyPrice(prices.head.date, lowerLimit.value) :: minPrices)
    } else {
      minPrices
    }
  }

}
