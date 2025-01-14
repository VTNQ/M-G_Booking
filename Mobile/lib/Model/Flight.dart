import 'package:mobile/Model/HotelBooking.dart';

class Flight {
  int? id;
  int? from;
  int? to;
  String? departureTime;
  String? arrivalTime;
  double? price;
  int?  ticketCount;
  String? seatClass;


  Flight({
    this.id,
    this.from,
    this.to,
    this.departureTime,
    this.arrivalTime,
    this.price,
    this.ticketCount,
    this.seatClass,
  });

  factory Flight.fromJson(Map<String, dynamic> json) {
    return Flight(
      id: json['id'],
      from: json['from'],
      to: json['to'],
      departureTime: json['departureTime'],
      arrivalTime: json['arrivalTime'],
      price: json['price'].toDouble(),
      ticketCount: json['ticketCount'],
      seatClass: json['seatClass'],
    );
  }
  Flight.fromMap(Map<String, dynamic> map) {
    id= map["id"];
    from= map["from"];
    to= map["to"];
    departureTime= map["departureTime"];
    arrivalTime= map["arrivalTime"];
    price= map["price"];
    ticketCount= map["ticketCount"];
    seatClass= map["seatClass"];
  }
  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      "id": id,
      "from": from,
      "to": to,
      "departureTime": departureTime,
      "arrivalTime": arrivalTime,
      "price": price,
      "ticketCount": ticketCount,
      "seatClass": seatClass,
    };
  }


}