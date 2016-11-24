package services

import model.Hotel
import org.scalatest.{FunSpec, Matchers}

class HotelServiceTest extends FunSpec with Matchers{

  describe("Hotel Service"){
    val hotels = List(Hotel("Bangkok",1,"Deluxe",	1000),
                      Hotel("Amsterdam",2,"Superior",	2200),
                      Hotel("Ashburn",3,"Sweet Suite",	1300),
                      Hotel("Amsterdam",4,"Deluxe",	2000),
                      Hotel("Ashburn",5,"Sweet Suite",	1200))

    val hotelService = new HotelService(hotels)

    it("should return hotels by city names"){
      val hotels:List[Hotel] = hotelService.getHotelsByCity("Amsterdam")
      hotels should be (List(Hotel("Amsterdam",2,"Superior",	2200),Hotel("Amsterdam",4,"Deluxe",	2000)))
    }

  }

}
