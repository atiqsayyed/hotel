package config

import io.HotelFileReader
import services.HotelService

class Dependencies {
  val HotelFilePath = this.getClass.getResource("/assets/hoteldb.csv").getPath
  val hotelReader = new HotelFileReader(HotelFilePath)
  val hotelService = new HotelService(hotelReader.read())
}

object DependenciesInstance extends Dependencies
