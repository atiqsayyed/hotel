package services

import core.{RateLimit, RateLimitRequestStatus, TokenBasedRateLimit}
import model.Rate

class MemoryBasedRateLimitService(globalRate: Rate, rateMap: Map[String, Rate], suspendFor: Long) {

  private val tokenMap = collection.mutable.Map[String, RateLimit]()

  def validate(apiKey: String, currentTimeInMillis: Long) = {
    registerApiKey(apiKey)
    val rateLimit = tokenMap.get(apiKey).get
    if (isRequestStillBlocked(rateLimit, currentTimeInMillis)) {
      if (isRequestAllowed(apiKey, rateLimit, currentTimeInMillis)) {
        true
      }
      else {
        updateBlockedTime(apiKey, currentTimeInMillis)
        false
      }
    } else {
      false
    }
  }

  private def updateBlockedTime(apiKey: String, currentTimeInMillis: Long) = {
    val blockedTill = currentTimeInMillis + suspendFor
    val rate = rateMap.get(apiKey).get
    val rateLimit = tokenMap.get(apiKey).get
    val updatedRateLimit = TokenBasedRateLimit(rate, blockedTill, rateLimit.rateLimitStatus)
    tokenMap.put(apiKey, updatedRateLimit)
  }

  private def isRequestStillBlocked(rateLimit: RateLimit, currentTimeInMillis: Long): Boolean = {
    currentTimeInMillis >= rateLimit.blockedTill
  }

  private def isRequestAllowed(apiKey: String, rateLimit: RateLimit, currentTimeInMillis: Long): Boolean = {
    val rateLimitStatus: RateLimitRequestStatus = rateLimit.processRequest(currentTimeInMillis / 1000)
    val rate = rateMap.get(apiKey).get
    val updatedRateLimit = TokenBasedRateLimit(rate, rateLimit.blockedTill, rateLimitStatus)
    tokenMap.put(apiKey, updatedRateLimit)
    rateLimitStatus.isValid
  }

  private def registerApiKey(apiKey: String) = {
    if (!isApiKeyAlreadyRegistered(apiKey)) {
      if (isRateDefined(apiKey)) {
        val rate: Rate = rateMap.get(apiKey).get
        tokenMap.put(apiKey, TokenBasedRateLimit(rate, 0, RateLimitRequestStatus(true, 0, 0)))
      } else {
        tokenMap.put(apiKey, TokenBasedRateLimit(globalRate, 0, RateLimitRequestStatus(true, 0, 0)))
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
