package core

trait RateLimit {
  def processRequest(nowSeconds: Long): Boolean
  def getBlockedTill: Long
  def setBlockedTill(blockedTill: Long)
}
