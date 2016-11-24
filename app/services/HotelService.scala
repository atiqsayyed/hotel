package services

import model.Hotel

class HotelService (hotels: List[Hotel]) {
  def getHotelsByCity(city:String) = {
    hotels.filter(_.city == city)
  }
//  lazy val hotels = reader.read()
}
