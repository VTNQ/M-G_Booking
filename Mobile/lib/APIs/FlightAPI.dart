import 'dart:convert';

import 'package:mobile/APIs/BaseUrl.dart';
import 'package:mobile/Model/Flight.dart';
import 'package:http/http.dart' as http;
import 'package:mobile/Model/ResultFlightDTO.dart';

class FlightAPI {
  static String FlightUrl = BaseUrl.baseUrl + "/Flight/";

  Future<List<Flight>> getFlights() async {
    var response = await http.get(Uri.parse(FlightUrl + "All"));
    if (response.statusCode == 200) {
      var responseBody = jsonDecode(response.body);
      if (responseBody['status'] == 200) {
        List<dynamic> data = responseBody['data'];
        return data.map((e) => Flight.fromMap(e)).toList();
      } else {
        throw Exception(responseBody['message'] ?? "Failed to fetch flights.");
      }
    } else {
      throw Exception("Failed with status code: ${response.statusCode}");
    }
  }

  Future<List<ResultFlightDTO>> getFlightDepartment(Flight flight) async {
    final Uri uri = Uri.parse(FlightUrl + "getFlight").replace(queryParameters: {
      'departureAirport': flight.from.toString(), // Ensure this is a string
      'arrivalAirport': flight.to.toString(), // Ensure this is a string
      'departureTime': flight.departureTime, // Ensure this is in the correct format
      'typeFlight': flight.seatClass, // Adjust based on your Flight model
      'numberPeopleRight': flight.ticketCount.toString(), // Convert to string if necessary
    });

    var response = await http.get(
      uri,
      headers: {
        'Content-Type': 'application/json', // Set the content type to JSON
      },
    );

    if (response.statusCode == 200) {
      List<dynamic> dyn = jsonDecode(response.body);
      return dyn.map((e) => ResultFlightDTO.(e)).toList();
    } else {
      throw Exception("Failed with status code: ${response.statusCode}");
    }
  }

}