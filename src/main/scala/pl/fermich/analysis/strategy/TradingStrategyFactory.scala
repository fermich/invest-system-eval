package pl.fermich.analysis.strategy

import com.typesafe.scalalogging.slf4j.Logging
import pl.fermich.analysis.data.{MarketData, Price}

//does additional validation and logging
object TradingStrategyFactory extends Logging {

  def createSmaTwoLineStrategy(marketData: MarketData, short: Int, long: Int): Option[SmaTwoLineStrategy] = {
    if (validateDataSize(marketData.getData(), short) && validateDataSize(marketData.getData(), long))
      Some(SmaTwoLineStrategy(marketData, short, long))
    else None
  }

  def createEmaTwoLineStrategy(marketData: MarketData, short: Int, long: Int): Option[EmaTwoLineStrategy] = {
    if (validateDataSize(marketData.getData(), short) && validateDataSize(marketData.getData(), long))
      Some(EmaTwoLineStrategy(marketData, short, long))
    else None
  }

  def createVamaTwoLineStrategy(marketData: MarketData, short: Int, long: Int): Option[VamaTwoLineStrategy] = {
    if (validateDataSize(marketData.getData(), short) && validateDataSize(marketData.getData(), long))
      Some(VamaTwoLineStrategy(marketData, short, long))
    else None
  }

  def createBollingerBandsStrategy(marketData: MarketData, length: Int, k: Int): Option[BollingerBandsStrategy] = {
    if (validateDataSize(marketData.getData(), length))
      Some(BollingerBandsStrategy(marketData, length, k))
    else None
  }

  def createChannelBreakoutStrategy(marketData: MarketData, entryLength: Int, stopLength: Int): Option[ChannelBreakoutStrategy] = {
    if (validateDataSize(marketData.getData(), entryLength) && validateDataSize(marketData.getData(), stopLength))
      Some(ChannelBreakoutStrategy(marketData, entryLength, stopLength))
    else None
  }

  def createPdiNdiStrategy(marketData: MarketData, length: Int): Option[PdiNdiStrategy] = {
    if (validateDataSize(marketData.getData(), length + 1))
      Some(PdiNdiStrategy(marketData, length))
    else None
  }

  private def validateDataSize(quotes: List[Price], length: Int): Boolean = {
    if (quotes.length < length) { logger.error("Could not create trading strategy! Not enough data for analysis!"); return false }
    else return true
  }

}
