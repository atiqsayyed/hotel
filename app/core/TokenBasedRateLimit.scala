package core

import model.Rate

class TokenBasedRateLimit(rate:Rate) extends RateLimit{
  private var lastRequestAtInSeconds = 0L
  private var requestBlockedTill = 0L
  private var currentAllowedMessages = 0.0

  override def processRequest(nowSeconds: Long): Boolean = {
    val time_passed: Long = nowSeconds - lastRequestAtInSeconds
    lastRequestAtInSeconds = nowSeconds
    updateAllowedMessages(time_passed)
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

  def updateAllowedMessages(time_passed: Long): Unit = {
    currentAllowedMessages += time_passed * (rate.maxMessagesAllowed / rate.perSeconds)
  }

  override def setBlockedTill(blockedUntill: Long): Unit = {
    requestBlockedTill = blockedUntill
  }

  override def getBlockedTill: Long = requestBlockedTill
}
