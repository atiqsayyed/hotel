package controllers

import config.DependenciesInstance._

object HotelControllerInstance extends HotelController(hotelService)