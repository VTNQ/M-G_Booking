import 'dart:convert';

import 'package:mobile/Model/Hotel.dart';
import 'package:http/http.dart' as http;

import 'BaseUrl.dart';

class HotelAPI{
  static String HotelUrl=BaseUrl.baseUrl+"/hotel/";

  Future<List<Hotel>> getHotelByCountryId(int id,int quantityRoom) async {
  final Uri uri=Uri.parse(HotelUrl+"SearchHotel").replace(queryParameters: {
    'idCity':id.toString(),
    'QuantityRoom':quantityRoom.toString()
  });
  var response = await http.get(
    uri,
    headers: {
      'Content-Type': 'application/json', // Set the content type to JSON
    },
  );
  if(response.statusCode==200){
    List<dynamic>dyn=jsonDecode(response.body);
    return dyn.map((e)=>Hotel.fromMap(e)).toList();
  }else {
    throw Exception("Failed with status code: ${response.statusCode}");
  }
  }
}