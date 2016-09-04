package pl.fermich.analysis.strategy

import pl.fermich.analysis.data.DailyPrice


trait TradingStrategy {
  def prices(): List[DailyPrice]
  def getPricesForDates(dates: List[Long]): List[DailyPrice] = {
    dates.flatMap(date => prices().find(_.date == date))
  }
}
