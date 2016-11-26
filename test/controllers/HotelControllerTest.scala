package controllers

import model.{Hotel, Order}
import org.scalatest.{Matchers, FunSpec}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mock.MockitoSugar
import play.api.test.FakeRequest
import org.mockito.Mockito._
import services.{MemoryBasedRateLimitService, HotelService}

class HotelControllerTest extends FunSpec with MockitoSugar with ScalaFutures with Matchers{

  describe("Hotel Controller"){

    it("should render home page"){
      new SetUp {
        val fakeRequest = FakeRequest("GET", "/")
        val response = controller.index()(fakeRequest).futureValue

        response.header.status should be(200)
      }
    }

    it("should render search page when request is valid"){
      new SetUp {
        val fakeRequest = FakeRequest("GET", "/search?city=Amsterdam&key=agoda")
        val response = controller.getHotels()(fakeRequest).futureValue

        response.header.status should be(200)
      }
    }

    it("should render TOO many requests status when request is not valid"){
      new SetUp {
        val fakeRequest = FakeRequest("GET", "/search?city=Amsterdam&key=agoda")
        when(inMemoryLimitServiceMock.validate(org.mockito.Matchers.eq("agoda"),org.mockito.Matchers.any[Long])).thenReturn(false)
        val response = controller.getHotels()(fakeRequest).futureValue

        response.header.status should be(429)
      }
    }
  }

  trait SetUp {
    val hotelServiceMock = mock[HotelService]
    val inMemoryLimitServiceMock = mock[MemoryBasedRateLimitService]
    val controller = new HotelController(hotelServiceMock,inMemoryLimitServiceMock)

    when(inMemoryLimitServiceMock.validate(org.mockito.Matchers.eq("agoda"),org.mockito.Matchers.any[Long])).thenReturn(true)
    when(hotelServiceMock.getHotelsByCity("Amsterdam",Order.ASC)).thenReturn(List(Hotel("Amsterdam",1,"Deluxe",1000)))
  }

}
