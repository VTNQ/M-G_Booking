import 'dart:convert';

import 'package:mobile/APIs/BaseUrl.dart';
import 'package:mobile/Model/Country.dart';
import 'package:http/http.dart' as http;

class CountryAPI{
  static String CountryUrl=BaseUrl.baseUrl+"/Country/All";

  Future<List<Country>> getCountries() async {
    var response = await http.get(Uri.parse(CountryUrl));
    if (response.statusCode == 200) {
      var responseBody = jsonDecode(response.body);
      if (responseBody['status'] == 200) {
        List<dynamic> data = responseBody['data'];
        return data.map((e) => Country.fromMap(e)).toList();
      } else {
        throw Exception(responseBody['message'] ?? "Failed to fetch countries.");
      }
    } else {
      throw Exception("Failed with status code: ${response.statusCode}");
    }
  }
}