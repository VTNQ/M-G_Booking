import 'dart:convert';

import 'package:mobile/Model/Hotel.dart';
import 'package:http/http.dart' as http;

import 'BaseUrl.dart';

class HotelAPI{
  static String HotelUrl=BaseUrl.baseUrl+"/Hotel/";

  Future<List<Hotel>> getHotelByCountryId() async{
    var response=await http.get(Uri.parse(HotelUrl));
    if(response.statusCode==200){
      var responseBody=jsonDecode(response.body);
      if(responseBody['status']==200){
        List<dynamic> data=responseBody['data'];
        return data.map((e)=>Hotel.fromMap(e)).toList();
      }else{
        throw Exception(responseBody['message']?? "Failed to fetch hotels.");
      }
    }else{
      throw Exception("Failed with status code: ${response.statusCode}");
    }
  }
}