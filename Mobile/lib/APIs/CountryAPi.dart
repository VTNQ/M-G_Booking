import 'dart:convert';

import 'package:mobile/APIs/BaseUrl.dart';
import 'package:mobile/Model/Country.dart';
import 'package:http/http.dart' as http;

class CountryAPI{
  static String CountryUrl=BaseUrl.baseUrl+"/country";

  Future<List<Country>> getCountries() async {
    var response = await http.get(Uri.parse(CountryUrl));
    if (response.statusCode == 200) {
      var data = jsonDecode(response.body);
      List<Country> countries = [];
      for (var item in data) {
        countries.add(Country.fromJson(item));
      }
      return countries;
    } else {
      return [];
    }
  }
}