import 'package:mobile/APIs/BaseUrl.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:mobile/Model/AirPort.dart';
import 'package:mobile/Model/CountryAirPort.dart';

class AirPortAPI{
  static String AirPortUrl=BaseUrl.baseUrl+"/AirPort";

  Future<List<CountryAirPort>> searchAirPort(String search) async {
    try{
      var response = await http.get(Uri.parse(AirPortUrl + "/SearchAirPort?search=$search"));
      if (response.statusCode == 200) {
        var responseBody = jsonDecode(response.body);
        if(responseBody is List){
          List<CountryAirPort> data = responseBody.map((e) => CountryAirPort.fromMap(e)).toList();
          return data;
        }else{
          throw "error";
        }
      } else {
        throw "Can't get AirPort";
      }
    }catch(e){
      throw e;
    }
  }

}