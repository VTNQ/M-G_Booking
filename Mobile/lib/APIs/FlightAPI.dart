import 'dart:convert';

import 'package:mobile/APIs/BaseUrl.dart';
import 'package:mobile/Model/Flight.dart';
import 'package:http/http.dart' as http;

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
}