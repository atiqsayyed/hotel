package controllers

import java.util.Date

import config.DependenciesInstance._
import model.Order
import play.api.mvc.{Action, Controller}
import services.{HotelService, InMemoryRateLimitService}


class HotelController(hotelService: HotelService, inMemoryRateLimitService: InMemoryRateLimitService) extends Controller {

  def getHotels() = Action {
    request =>
      val city = request.getQueryString("city").getOrElse("Amsterdam")
      val order = request.getQueryString("order").getOrElse(Order.ASC)
      val key = request.getQueryString("key").getOrElse("")
      val currentTime = new Date().getTime
      if (inMemoryRateLimitService.validate(key, currentTime)) {
        val hotels = hotelService.getHotelsByCity(city, order)
        Ok(views.html.search(hotels))
      } else {
        TooManyRequest("You have exceeded maximum allowed usage. Please try after 5 minutes")
      }
  }

  def index() = Action {
    Ok(views.html.index())
  }

}

object HotelController extends HotelController(hotelService, inMemoryRateLimitService)

