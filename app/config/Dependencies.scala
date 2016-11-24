package config

import io.HotelFileInputReader
import services.HotelService

class Dependencies {
  val HotelFilePath = this.getClass.getResource("/assets/hoteldb.csv").getPath
  val hotelReader = new HotelFileInputReader(HotelFilePath)
  val hotelService = new HotelService(hotelReader)
}

object DependenciesInstance extends Dependencies
