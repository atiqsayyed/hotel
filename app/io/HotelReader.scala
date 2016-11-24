package io

import model.Hotel

trait HotelReader {
  def read:List[Hotel]
}
