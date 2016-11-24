package io

import model.Hotel

trait HotelInputReader {
  def read:List[Hotel]
}
