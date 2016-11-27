package core

case class RateLimitRequestStatus(isValid:Boolean,lastRequestAtInSeconds:Long, currentAllowedMessages:Double)

trait RateLimit {
  def processRequest(nowSeconds: Long): RateLimitRequestStatus
  def requestBlockedTill: Long
  def rateLimitStatus:RateLimitRequestStatus
}
