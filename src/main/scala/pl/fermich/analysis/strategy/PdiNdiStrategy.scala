package pl.fermich.analysis.strategy

import pl.fermich.analysis.data.PriceExtractor._
import pl.fermich.analysis.data.PriceConverters._
import pl.fermich.analysis.indicator.IndicatorConverters._
import pl.fermich.analysis.data.{DailyPrice, MarketData, PriceMapper}


case class PdiNdiStrategy(val marketData: MarketData, val length: Int) extends TradingStrategy {
  private val quotes = marketData.getData()
  require(length <= quotes.size,"The Directional Indicator length exceeds price array size! Expected size <= " + quotes.size)

  val priceMapper = PriceMapper(quotes, PriceOnClose)

  def ndi(): List[DailyPrice] = mapToDailyIndicator(quotes NDI length)

  def pdi(): List[DailyPrice] = mapToDailyIndicator(quotes PDI length)

  private def mapToDailyIndicator(di: IndexedSeq[Double]) = {
    val quotesDi = priceMapper.dates zip di
    quotesDi.map{ case(date, di) => DailyPrice(date, di) }
  }

  override def prices(): List[DailyPrice] = priceMapper.select()
}
