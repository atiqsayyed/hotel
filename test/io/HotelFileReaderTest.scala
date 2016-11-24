package io

import model.Hotel
import org.scalatest.{FunSpec, Matchers}
import play.api.Play
import play.api.test.{FakeApplication, Helpers}
import java.io.File
import play.api.Play.current

class HotelFileReaderTest extends FunSpec with Matchers{

  describe("Hotel File Reader"){

    it("should read File and return hotels"){
      Helpers.running(FakeApplication()) {
        val filePath = play.api.Environment.simple().classLoader.getResource("test.csv").getPath
        val reader = new HotelFileReader(filePath)
        val hotels = reader.read()

        hotels.size should be(4)

        hotels(0) should be(Hotel("Bangkok",1,"Deluxe",1000))
        hotels(2) should be(Hotel("Ashburn",3,"Sweet Suite",1300))
      }
    }
  }
}
