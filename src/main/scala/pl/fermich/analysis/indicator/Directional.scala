package pl.fermich.analysis.indicator

import pl.fermich.analysis.data.Price
import pl.fermich.analysis.indicator.IndicatorConverters._


class Directional(val prices: List[Price]) {

  //average directional movement index
  def ADX(length: Int) = {
    val pdiNdi = PDI(length) zip NDI(length)
    val indicators: List[Double] = pdiNdi.map { case (pdi, ndi) => (pdi - ndi).abs / (pdi + ndi) }.toList
    (indicators EMA length).map(_ * 100.0)
  }

  //positive directional indicator
  def PDI(length: Int) = DI(PDM().EMA(length), length)

  //negative directional indicator
  def NDI(length: Int) = DI(NDM().EMA(length), length)


  def DI(smoothedDm: IndexedSeq[Double], length: Int) = {
    val dmAtr = smoothedDm zip ATR(length)
    dmAtr.map{ case (dm, atr) =>
      if (atr != 0.0) 100.0 * dm / atr
      else 0.0
    }
  }

  //positive directional movement
  def PDM() = {
    val pricesPrevPrices = prices zip prices.tail
    pricesPrevPrices.map{ case (current, previous) => {
      val upMove = current.high - previous.high
      val downMove = previous.low - current.low
      if (upMove > downMove && upMove > 0) upMove
      else 0.0
    }}
  }

  //negative directional movement
  def NDM() = {
    val pricesPrevPrices = prices zip prices.tail
    pricesPrevPrices.map{ case (current, previous) => {
      val upMove = current.high - previous.high
      val downMove = previous.low - current.low
      if (downMove > upMove && downMove > 0) downMove
      else 0.0
    }}
  }

  //average true range
  def ATR(length: Int) = TR SMA length

  //true range
  def TR() = {
    val pricesPrevPrices = prices zip prices.tail
    pricesPrevPrices.map { case (current, previous) =>
        List(current.high - current.low, (current.high - previous.close).abs, (current.low - previous.close).abs).max
    }
  }

}