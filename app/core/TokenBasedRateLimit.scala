package core

import model.Rate


case class TokenBasedRateLimit(rate: Rate, requestBlockedTill: Long, rateLimitStatus: RateLimitRequestStatus) extends RateLimit {

  override def processRequest(nowSeconds: Long): RateLimitRequestStatus = {
    val time_passed = nowSeconds - rateLimitStatus.lastRequestAtInSeconds
    val updatedCurrentAllowedMessage = updateAllowedMessages(time_passed)

    if (updatedCurrentAllowedMessage <= 1.0) {
      RateLimitRequestStatus(false, nowSeconds, updatedCurrentAllowedMessage)
    } else {
      RateLimitRequestStatus(true, nowSeconds, updatedCurrentAllowedMessage - 1.0)
    }
  }

  private def updateAllowedMessages(time_passed: Long): Double = {
    val currentAllowedMessages = rateLimitStatus.currentAllowedMessages + time_passed * (rate.maxMessagesAllowed / rate.perSeconds)
    if (rateLimitStatus.currentAllowedMessages > rate.maxMessagesAllowed) {
      rate.maxMessagesAllowed
    } else {
      currentAllowedMessages
    }
  }
}
