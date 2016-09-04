package pl.fermich.analysis.indicator

import org.apache.commons.math.stat.descriptive.moment.StandardDeviation

class Statistics(val vals: List[Double]) {

  //standard deviation
  def SD(period: Int) = {
    val sd = new StandardDeviation()
    val sds = (period to vals.size).map(i => {
      val periodVals = vals.slice(i - period, i)
      sd.evaluate(periodVals.toArray)
    })
    sds
  }

}
