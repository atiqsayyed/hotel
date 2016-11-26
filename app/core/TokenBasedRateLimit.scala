package core

import model.Rate

class TokenBasedRateLimit(rate:Rate) extends RateLimit{
  var lastCheckedAtInSeconds = 0L
  // epoch time
  var blockedTill = 0L
  var currentAllowedMessages = 0.0

  override def processMessage(nowSeconds: Long): Boolean = {
    val time_passed: Long = nowSeconds - lastCheckedAtInSeconds
    lastCheckedAtInSeconds = nowSeconds
    currentAllowedMessages += time_passed * (rate.maxMessagesAllowed / rate.perSeconds)
    if (currentAllowedMessages > rate.maxMessagesAllowed) {
      currentAllowedMessages = rate.maxMessagesAllowed
    }
    if (currentAllowedMessages < 1.0) {
      false
    }else{
      currentAllowedMessages -= 1.0
      true
    }
  }

  override def setBlockedTill(blockedUntill: Long): Unit = {
    blockedTill = blockedUntill
  }

  override def getBlockedTill: Long = blockedTill
}
