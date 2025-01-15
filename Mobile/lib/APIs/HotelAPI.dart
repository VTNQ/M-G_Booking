import 'dart:convert';

import 'package:mobile/Model/Hotel.dart';
import 'package:http/http.dart' as http;
import 'package:mobile/Model/ShowDetailHotel.dart';

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

  Future<ShowDetailHotel> getDetailHotel(int id) async {
    final Uri uri=Uri.parse(HotelUrl+"GetDetailHotel").replace(queryParameters: {
      'id':id.toString()
    });
    var response = await http.get(
      uri,
      headers: {
        'Content-Type': 'application/json',
      },
    );
    if(response.statusCode==200){
      var responseBody=jsonDecode(response.body);
      if(responseBody['status']==200){
        return ShowDetailHotel.fromMap(responseBody['data']);
      }else{
        throw Exception(responseBody['message'] ?? "Failed to fetch detail hotel.");
      }
    }else {
      throw Exception("Failed with status code: ${response.statusCode}");
    }
  }
}