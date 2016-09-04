package pl.fermich.analysis.indicator

import org.apache.commons.math.stat.descriptive.moment.{Mean, StandardDeviation}
import pl.fermich.analysis.data.PriceUtils

/**
 * They do their best work when the market is in a trending phase
 */
class MovingAverage(val vals: List[Double]) {

  //Simple Moving Average
  def SMA(period: Int): List[Double] = {
    require(period <= vals.size, "The period exceeds array size! Expected <= " + vals.size + " found: " + period)
    val m = new Mean
    var avgm: Double = m.evaluate(vals.toArray, 0, period)
    val first = avgm
    var amaVals = (period until vals.size).map(i => {
        avgm = (avgm * period - vals(i - period) + vals(i)) / period
        avgm
    })
    amaVals = first +: amaVals
    amaVals.toList
  }

  //Volume Adjusted Moving Average (sometimes called Equivolume charting- wykresy słupkowe z wolumenem)
  def VAMA(volume: List[Double], period: Int) = {
    EMA(volume, period)
  }

  //Exponential Moving Average
  def EMA(period: Int): IndexedSeq[Double] = {
    val weights = (1 to vals.size).map(_.toDouble).toList.reverse
    EMA(weights, period)
  }

  //Exponential Moving Average with custom weights
  def EMA(weights: List[Double], period: Int): IndexedSeq[Double] = {
    require(period <= vals.size && period <= weights.size,
        "The period exceeds arrays size! Expected <= " + vals.size + " found: " + period)
    val m = new Mean
  	val ema = (period to vals.size).map(i => {
      PriceUtils.normalize(weights.slice(i - period, i)) match {
        case Some(normalizedWeights) => {
          val periodVals = vals.slice(i - period, i)
          m.evaluate(periodVals.toArray, normalizedWeights.toArray)
        }
        case None => 0.0
      }
	  })
	  ema
  }
  
}
