import 'package:mobile/APIs/BaseUrl.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:mobile/Model/AirPort.dart';

class AirPortAPI{
  static String AirPortUrl=BaseUrl.baseUrl+"/Airport";

  Future<List<Airport>> searchAirPort(String search) async {
    var response = await http.get(Uri.parse(AirPortUrl + "/SearchAirPort?$search"));
    if (response.statusCode == 200) {
      var responseBody = jsonDecode(response.body);
      if (responseBody['status'] == 200) {
        List<dynamic> data = responseBody['data'];
        return data.map((e) => Airport.fromMap(e)).toList();
      } else {
        throw Exception(responseBody['message'] ?? "Failed to fetch AirPort.");
      }
    } else {
      throw "Can't get AirPort";
    }
  }
}