package pl.fermich.analysis.indicator

import pl.fermich.analysis.data.{DailyPrice, Price}

class Oscillator(val prices: List[Price]) {

  def WILLIAMS(length: Int) = {
    val zipped = prices zip prices.drop(length)
    zipped.map(p => DailyPrice(p._1.date, (p._2.high - p._1.close) / (p._2.high - p._2.low)))
  }

}

class PriceOscillator(val prices: List[DailyPrice]) {

  def MOMENTUM(length: Int) = {
    val zipped = prices zip prices.drop(length)
    zipped.map(p => DailyPrice(p._1.date, p._1.value - p._2.value))
  }

  def ROC(length: Int) = {
    val zipped = prices zip prices.drop(length)
    zipped.map(p => DailyPrice(p._1.date, (p._1.value - p._2.value) / p._2.value))
  }
}