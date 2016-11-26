package services

import java.util.Date

import model.Rate
import org.scalatest.{Matchers, FunSpec}

class InMemoryRateLimitServiceTest extends FunSpec with Matchers{

  describe("In Memory Rate Limit Service Test"){
    val ONE_MINUTE_IN_MILLIS = 60000L
    val globalRate = Rate(1,10)
    val rateMap = Map("agoda" -> Rate(5,10))
    val inMemoryLimitService = new InMemoryRateLimitService(globalRate, rateMap,5 * ONE_MINUTE_IN_MILLIS)
    val currentTime = new Date().getTime

    it("should return true for first five request"){
      inMemoryLimitService.validate("agoda",currentTime) should be(true)
      inMemoryLimitService.validate("agoda",currentTime) should be(true)
      inMemoryLimitService.validate("agoda",currentTime) should be(true)
      inMemoryLimitService.validate("agoda",currentTime) should be(true)
      inMemoryLimitService.validate("agoda",currentTime) should be(true)
    }

    it("should block the request after max request limit is crossed "){
      inMemoryLimitService.validate("agoda",currentTime) should be(false)
    }

    it("should fail even after 10 seconds after getting suspended for 5 minutes"){
      inMemoryLimitService.validate("agoda", currentTime + 6000) should be (false)
    }
    it("should start working again after 5 minutes"){
      inMemoryLimitService.validate("agoda", currentTime + 5 * ONE_MINUTE_IN_MILLIS) should be(true)
    }

  }
}
