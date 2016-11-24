package io

import model.Hotel
import play.Play

import scala.io.Source
import java.io.File

class HotelFileInputReader(filePath: String) extends HotelInputReader {
  def read() = {
    val hotelCsv = Source.fromFile(filePath).getLines().toList.tail
    hotelCsv.map(csvRecord => {
      val hotelRecord = csvRecord.split(",")
      Hotel(hotelRecord(0), hotelRecord(1).toInt, hotelRecord(2), hotelRecord(3).toInt)
    })
  }
}