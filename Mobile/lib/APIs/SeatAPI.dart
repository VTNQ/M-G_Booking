import 'dart:convert';
import 'package:mobile/APIs/BaseUrl.dart';
import 'package:mobile/Model/Seat.dart';
import 'package:http/http.dart' as http;

class SeatAPI {
  static String SeatURL = BaseUrl.baseUrl+"/seat/";

  Future<List<Seat>> detailSeat(int id) async {
    final Uri uri = Uri.parse(SeatURL + "${id}");
    var response = await http.get(
      uri,
      headers: {
        'Content-Type': 'application/json',
      },
    );
    if (response.statusCode == 200) {
      List<dynamic> dyn = jsonDecode(response.body);
      return dyn.map((e) => Seat.fromMap(e)).toList();
    } else {
      throw Exception("Failed with status code: ${response.statusCode}");
    }
  }
}