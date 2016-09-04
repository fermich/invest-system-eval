package pl.fermich.analysis.signal

object SignalType extends Enumeration {
  def BUY = 1
  def SELL = 0
}

case class TradingSignal(val date: Long, val signal: Int) { }
