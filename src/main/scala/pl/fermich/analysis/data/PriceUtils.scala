package pl.fermich.analysis.data

object PriceUtils {

  def normalize(values: List[Double]): Option[List[Double]] = {
    val sum = values.reduceLeft(_ + _)
    if (sum != 0.0)
      Some(values.map(_ / sum))
    else
      None
  }

}