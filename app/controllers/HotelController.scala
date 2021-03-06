package controllers

import java.util.Date

import config.DependenciesInstance._
import model.Order
import play.api.mvc.{Action, Controller}
import services.{HotelService, MemoryBasedRateLimitService}


class HotelController(hotelService: HotelService, memoryBasedLimitService: MemoryBasedRateLimitService) extends Controller {

  def getHotels() = Action {
    request =>
      val cityOpt = request.getQueryString("city")
      val keyOpt = request.getQueryString("key")
      val order = request.getQueryString("order").getOrElse(Order.ASC)

      (cityOpt, keyOpt) match {
        case (Some(city), Some(key)) => {
          val currentTime = new Date().getTime
          if (memoryBasedLimitService.validate(key, currentTime)) {
            val hotels = hotelService.getHotelsByCity(city, order)
            Ok(views.html.search(hotels))
          } else {
            TooManyRequest("You have exceeded maximum allowed usage. Please try after 5 minutes")
          }
        }
        case _ => {
          UnprocessableEntity("Request Parameters 'city' or 'key' missing!!")
        }
      }

  }

  def index() = Action {
    Ok(views.html.index())
  }

}

object HotelController extends HotelController(hotelService, inMemoryRateLimitService)

