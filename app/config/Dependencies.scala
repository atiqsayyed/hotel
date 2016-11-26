package config

import io.HotelFileInputReader
import model.Rate
import play.api.Play
import services.{HotelService, InMemoryRateLimitService}
import play.api.Play.current

class Dependencies {
  val ONE_MINUTE_IN_MILLIS = 60000L
  lazy val HotelFilePath = Play.application.classloader.getResource(Properties.hotelDb).getPath
  lazy val hotelReader = new HotelFileInputReader(HotelFilePath)
  lazy val hotelService = new HotelService(hotelReader)
  val globalRate = Rate(Properties.maxGlobalAllowedMessage, Properties.perSecondsGlobal)
  val rateMap = Map(Properties.apiKey -> Rate(Properties.maxAllowedMessage,Properties.perSeconds))
  lazy val inMemoryRateLimitService = new InMemoryRateLimitService(globalRate, rateMap, Properties.suspensionTimeInMinutes * ONE_MINUTE_IN_MILLIS)
}

object DependenciesInstance extends Dependencies
