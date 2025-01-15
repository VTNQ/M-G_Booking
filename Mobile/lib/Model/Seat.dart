import 'dart:convert';
import 'package:decimal/decimal.dart';

class Seat {
  int id;
  String index;
  String type;
  Decimal price;
  int? status; // Nullable
  int? idFlight; // Nullable

  Seat({
    required this.id,
    required this.index,
    required this.type,
    required this.price,
    this.status,
    this.idFlight,
  });

  // Convert a SeatDTO instance to a Map
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'index': index,
      'type': type,
      'price': price.toString(), // Convert Decimal to String
      'status': status,
      'idFlight': idFlight,
    };
  }

  // Create a SeatDTO instance from a Map
  factory Seat.fromMap(Map<String, dynamic> map) {
    return Seat(
      id: map['id'],
      index: map['index'],
      type: map['type'],
      price: Decimal.parse(map['price'].toString()), // Ensure price is parsed correctly
      status: map['status'],
      idFlight: map['idFlight'],
    );
  }

  // Optional: Convert SeatDTO to JSON string
  String toJson() => json.encode(toMap());

  // Optional: Create SeatDTO from JSON string
  factory Seat.fromJson(String source) => Seat.fromMap(json.decode(source));
}