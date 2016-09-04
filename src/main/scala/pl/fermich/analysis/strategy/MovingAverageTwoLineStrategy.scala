package pl.fermich.analysis.strategy

import pl.fermich.analysis.data.{DailyPrice, MarketData, PriceMapper}
import pl.fermich.analysis.data.PriceConverters._
import pl.fermich.analysis.data.PriceExtractor._


trait MovingAverageTwoLineStrategy extends TradingStrategy {
  def shortLine(): List[DailyPrice]
  def longLine(): List[DailyPrice]
}


case class VamaTwoLineStrategy(val marketData: MarketData, val shortPeriod: Int, val longPeriod: Int) extends MovingAverageTwoLineStrategy {
  private val quotes = marketData.getData()
  require(shortPeriod <= quotes.size && longPeriod <= quotes.size,
    "The VAMA length exceeds price array size! Expected size <= " + quotes.size)

  val priceMapper = PriceMapper(quotes, PriceOnClose)

  def shortLine(): List[DailyPrice] = priceMapper VAMA shortPeriod

  def longLine(): List[DailyPrice] = priceMapper VAMA longPeriod

  override def prices(): List[DailyPrice] = priceMapper.select()
}



case class EmaTwoLineStrategy(val marketData: MarketData, val shortPeriod: Int, val longPeriod: Int) extends MovingAverageTwoLineStrategy {
  private val quotes = marketData.getData()
  require(shortPeriod <= quotes.size && longPeriod <= quotes.size,
    "The EMA length exceeds price array size! Expected size <= " + quotes.size)

  val priceMapper = PriceMapper(quotes, PriceOnClose)

  def shortLine(): List[DailyPrice] = priceMapper EMA shortPeriod

  def longLine(): List[DailyPrice] = priceMapper EMA longPeriod

  override def prices(): List[DailyPrice] = priceMapper.select()
}



case class SmaTwoLineStrategy(val marketData: MarketData, val shortPeriod: Int, val longPeriod: Int) extends MovingAverageTwoLineStrategy {
  private val quotes = marketData.getData()
  require(shortPeriod <= quotes.size && longPeriod <= quotes.size,
    "The EMA length exceeds price array size! Expected size <= " + quotes.size)

  val priceMapper = PriceMapper(quotes, PriceOnClose)

  def shortLine(): List[DailyPrice] = priceMapper SMA shortPeriod

  def longLine(): List[DailyPrice] = priceMapper SMA longPeriod

  override def prices(): List[DailyPrice] = priceMapper.select()
}
