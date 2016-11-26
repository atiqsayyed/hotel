package services

import core.{RateLimit, TokenBasedRateLimit}
import model.Rate

class InMemoryRateLimitService(globalRate: Rate, rateMap: Map[String, Rate], suspendFor: Long) {

  val tokenMap = collection.mutable.Map[String, RateLimit]()

  def validate(apiKey: String, currentTimeInMillis:Long) = {
    registerApiKey(apiKey)
    if (currentTimeInMillis >= tokenMap.get(apiKey).get.getBlockedTill) {
      if (tokenMap.get(apiKey).get.processMessage(currentTimeInMillis / 1000)) {
        true
      }
      else {
        val blockedTill = currentTimeInMillis + suspendFor
        tokenMap.get(apiKey).get.setBlockedTill(blockedTill)
        false
      }
    }else{
      false
    }
  }

  def registerApiKey(apiKey: String) = {
    if (!isApiKeyAlreadyRegistered(apiKey)) {
      if (isRateDefined(apiKey)) {
        tokenMap.put(apiKey, new TokenBasedRateLimit(rateMap.get(apiKey).get))
      } else {
        tokenMap.put(apiKey, new TokenBasedRateLimit(globalRate))
      }
    }
  }

  def isApiKeyAlreadyRegistered(apiKey: String) = {
    tokenMap.get(apiKey).isDefined
  }

  private def isRateDefined(apiKey: String): Boolean = {
    rateMap.get(apiKey).isDefined
  }

}
