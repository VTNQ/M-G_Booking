import 'dart:convert';
import 'package:intl/intl.dart';

class ResultFlightDTO {
  String imageUrl;
  int id;
  int idFlight;
  String nameCity;
  String nameArrivalAirport;
  String timeArrival;
  String dateDepart;
  String dateArrival;
  Duration duration; // Duration can be represented as a Duration object
  String durationString; // Duration as a formatted string
  String nameAirport;
  String timeDepart;
  String nameAirline;
  DateTime arrivalTime;
  DateTime departureTime;
  double price; // Use double for price instead of BigDecimal

  ResultFlightDTO({
    required this.imageUrl,
    required this.id,
    required this.idFlight,
    required this.nameCity,
    required this.nameArrivalAirport,
    required this.timeArrival,
    required this.dateDepart,
    required this.dateArrival,
    required this.duration,
    required this.durationString,
    required this.nameAirport,
    required this.timeDepart,
    required this.nameAirline,
    required this.arrivalTime,
    required this.departureTime,
    required this.price,
  });

  // Method to format Duration as HH:mm
  static String formatDuration(Duration duration) {
    return duration.inHours.toString().padLeft(2, '0') + ':' + (duration.inMinutes % 60).toString().padLeft(2, '0');
  }

  // Factory method to create an instance from a Map
  factory ResultFlightDTO.fromMap(Map<String, dynamic> map) {
    Duration duration = Duration(
      hours: map['durationHours'] ?? 0,
      minutes: map['durationMinutes'] ?? 0,
    );

    return ResultFlightDTO(
      imageUrl: map['imageUrl']??'',
      id: map['id']??0,
      idFlight: map['idFlight']??0,
      nameCity: map['nameCity']??'',
      nameArrivalAirport: map['nameArrivalAirport']??'',
      timeArrival: map['timeArrival']??'',
      dateDepart: map['dateDepart']??'',
      dateArrival: map['dateArrival']??'',
      duration: duration,
      durationString: formatDuration(duration),
      nameAirport: map['nameAirport']??'',
      timeDepart: map['timeDepart']??'',
      nameAirline: map['nameAirline']??'',
      arrivalTime: map['arrivalTime'] != null ? DateTime.parse(map['arrivalTime']) : DateTime.now(), // Parse or default to current time
      departureTime: map['departureTime'] != null ? DateTime.parse(map['departureTime']) : DateTime.now(),
      price: (map['price'] as num).toDouble()??0.0, // Ensure price is a double
    );
  }

  // Method to convert an instance to a Map
  Map<String, dynamic> toMap() {
    return {
      'imageUrl': imageUrl,
      'id': id,
      'idFlight': idFlight,
      'nameCity': nameCity,
      'nameArrivalAirport': nameArrivalAirport,
      'timeArrival': timeArrival,
      'dateDepart': dateDepart,
      'dateArrival': dateArrival,
      'durationHours': duration.inHours,
      'durationMinutes': duration.inMinutes % 60,
      'nameAirport': nameAirport,
      'timeDepart': timeDepart,
      'nameAirline': nameAirline,
      'arrivalTime': arrivalTime.toIso8601String(),
      'departureTime': departureTime.toIso8601String(),
      'price': price,
    };
  }

}