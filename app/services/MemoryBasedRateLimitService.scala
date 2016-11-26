package services

import core.{RateLimit, TokenBasedRateLimit}
import model.Rate

class MemoryBasedRateLimitService(globalRate: Rate, rateMap: Map[String, Rate], suspendFor: Long) {

  private val tokenMap = collection.mutable.Map[String, RateLimit]()

  def validate(apiKey: String, currentTimeInMillis:Long) = {
    registerApiKey(apiKey)
    if (isRequestStillBlocked(apiKey, currentTimeInMillis)) {
      if (isRequestAllowed(apiKey, currentTimeInMillis)) {
        true
      }
      else {
        updateBlockedTime(apiKey, currentTimeInMillis)
        false
      }
    }else{
      false
    }
  }

  private def updateBlockedTime(apiKey: String, currentTimeInMillis: Long): Unit = {
    val blockedTill = currentTimeInMillis + suspendFor
    tokenMap.get(apiKey).get.setBlockedTill(blockedTill)
  }

  private def isRequestStillBlocked(apiKey: String, currentTimeInMillis: Long): Boolean = {
    currentTimeInMillis >= tokenMap.get(apiKey).get.getBlockedTill
  }

  private def isRequestAllowed(apiKey: String, currentTimeInMillis: Long): Boolean = {
    tokenMap.get(apiKey).get.processRequest(currentTimeInMillis / 1000)
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

  private def isApiKeyAlreadyRegistered(apiKey: String) = {
    tokenMap.get(apiKey).isDefined
  }

  private def isRateDefined(apiKey: String): Boolean = {
    rateMap.get(apiKey).isDefined
  }

}
