package pl.fermich.analysis.data

case class DailyPrice(
    date: Long,
    value: Double)


case class Price(
    sym: String, 
    date: Long, 
    open: Double, 
    high: Double, 
    low: Double, 
    close: Double, 
    vol: Double)

trait MarketData {
  def getData(): List[Price]
}

case class Prices(val prices: List[Price]) extends MarketData {
  override def getData() = prices
}
