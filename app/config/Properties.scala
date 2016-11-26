package config

import play.api.Play
import play.api.Play.current

object Properties {

  val apiKey = getConfigFromPropertyFile("apiKey").getOrElse("")
  val globalRate = getConfigFromPropertyFile("globalRate").getOrElse("10").toInt
  val rate = getConfigFromPropertyFile("rate")
  val maxGlobalAllowedMessage = getConfigFromPropertyFile("maxGlobalAllowedMessage").getOrElse("10").toInt
  val maxAllowedMessage = getConfigFromPropertyFile("maxAllowedMessage").getOrElse("10").toInt
  val perSecondsGlobal = getConfigFromPropertyFile("perSecondsGlobal").getOrElse("1").toInt
  val perSeconds = getConfigFromPropertyFile("perSeconds").getOrElse("3").toInt
  val suspensionTimeInMinutes = getConfigFromPropertyFile("suspensionTimeInMinutes").getOrElse("5").toInt
  val hotelDb = getConfigFromPropertyFile("hotelDb").getOrElse("")

  private def getConfigFromPropertyFile(propName: String) = {
    Play.configuration.getString(propName)
  }
}


