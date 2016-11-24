package controllers

import model.Order
import play.api.mvc.{Action, Controller}
import services.HotelService


class HotelController (hotelService: HotelService) extends Controller{

  def getHotels() = Action {
    request =>
      val city = request.getQueryString("city").getOrElse("Amsterdam")
      val order = request.getQueryString("order").getOrElse(Order.ASC)
      val key = request.getQueryString("key").getOrElse("")
      val hotels = hotelService.getHotelsByCity(city,order)

      Ok(views.html.search(hotels))
  }

  def index() = Action{
    Ok(views.html.index())
  }

}

