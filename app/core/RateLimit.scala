package core

trait RateLimit {
  def processMessage(nowSeconds: Long): Boolean
  def getBlockedTill: Long
  def setBlockedTill(blockedTill: Long)
}
