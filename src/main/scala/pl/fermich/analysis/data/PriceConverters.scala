package pl.fermich.analysis.data

object PriceConverters {

  implicit def tupleList2DailyPriceList(tuples: List[Tuple2[Long, Double]]): List[DailyPrice] = {
    tuples.map(t => DailyPrice(t._1, t._2))
  }

}