package pl.fermich.analysis.indicator

import scala.collection.mutable.ListBuffer
import pl.fermich.analysis.signal.TradingSignal
import pl.fermich.analysis.signal.SignalType
import pl.fermich.analysis.data.DailyPrice

class PriceCurve(val leftLine: List[DailyPrice]) {
  
  def INTERSECT(rightLine: List[DailyPrice]) = {
    val lines = leftLine zip rightLine;

    val intersections = ListBuffer[TradingSignal]()
    lines.reduceLeft((c1, c2) => {
      if (!(c1._1.value > c1._2.value && c2._1.value > c2._2.value || c1._1.value < c1._2.value && c2._1.value < c2._2.value)) {
        if (c1._1.value > c1._2.value) intersections += TradingSignal(c1._1.date, SignalType.BUY)
        if (c1._1.value <= c1._2.value) intersections += TradingSignal(c1._1.date, SignalType.SELL)
      }
      c2 })
    intersections.toList
  }

  def CONV_DIV(rightLine: List[DailyPrice]): List[DailyPrice] = {
    val lines = leftLine zip rightLine
    lines.map(p => DailyPrice(p._1.date, p._1.value - p._2.value))
  }

  def CONV_DIV_ROC(rightLine: List[DailyPrice]): List[DailyPrice] = {
    val lines = leftLine zip rightLine
    lines.map(p => DailyPrice(p._1.date, (p._1.value - p._2.value) / p._2.value))
  }
}
