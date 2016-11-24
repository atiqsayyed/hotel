package services

import io.HotelInputReader
import model.{Order, Hotel}
import org.scalatest.{FunSpec, Matchers}

class HotelServiceTest extends FunSpec with Matchers{

  describe("Hotel Service"){

    val hotelService = new HotelService(new HotelReaderStub())
    it("should return hotels by city names and sorted by price in ascending order"){
      val hotels:List[Hotel] = hotelService.getHotelsByCity("Amsterdam",Order.ASC)
      hotels should be (List(Hotel("Amsterdam",4,"Deluxe",	2000),
                             Hotel("Amsterdam",2,"Superior",	2200)))
    }

    it("should return hotels by city names and sorted by price in descending order"){
      val hotels:List[Hotel] = hotelService.getHotelsByCity("Ashburn",Order.DESC)
      hotels should be (List(Hotel("Ashburn",3,"Sweet Suite",	1300),
                             Hotel("Ashburn",5,"Sweet Suite",	1200)))
    }
  }

  class HotelReaderStub extends HotelInputReader{
    val hotels = List(Hotel("Bangkok",1,"Deluxe",	1000),
      Hotel("Amsterdam",2,"Superior",	2200),
      Hotel("Ashburn",3,"Sweet Suite",	1300),
      Hotel("Amsterdam",4,"Deluxe",	2000),
      Hotel("Ashburn",5,"Sweet Suite",	1200))

    override def read: List[Hotel] = hotels
  }

}
