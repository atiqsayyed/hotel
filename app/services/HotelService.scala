package services
import io.HotelInputReader
import model.Order

class HotelService(hotelInputReader: HotelInputReader) {
  lazy val hotels = hotelInputReader.read

  def getHotelsByCity(city:String, order: String) = {
    val filteredHotels = hotels.filter(_.city.toLowerCase == city.toLowerCase)
    if(order == Order.ASC){
      filteredHotels.sortBy(_.price)
    }else{
      filteredHotels.sortBy(-_.price)
    }
  }
}
