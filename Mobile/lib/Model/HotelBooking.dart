import 'dart:convert';

class HotelBooking {
  final String location;
  final String checkInDate;
  final String checkOutDate;
  final int roomsCount;

  HotelBooking({
    required this.location,
    required this.checkInDate,
    required this.checkOutDate,
    required this.roomsCount,
  });
  Map<String,dynamic> toJson(){
    return {
      'location': location,
      'check_in_date': checkInDate,
      'check_out_date': checkOutDate,
      'number_count': roomsCount,
    };
  }
}
