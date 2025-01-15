import 'package:mobile/APIs/BaseUrl.dart';
import 'package:mobile/Model/RoomDetailHotel.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class RoomApi {
  static String RoomUrl=BaseUrl.baseUrl+"/room/";

  Future<List<RoomDetailHotel>> getRoomByHotelId(int id) async {
    final Uri uri=Uri.parse(RoomUrl+"GetRoomByHotel").replace(queryParameters: {
      'hotelId':id.toString()
    });
    var response = await http.get(
      uri,
      headers: {
        'Content-Type': 'application/json', // Set the content type to JSON
      },
    );
    print(response.body);
    if(response.statusCode==200){
      List<dynamic>dyn=jsonDecode(response.body);
      return dyn.map((e)=>RoomDetailHotel.fromMap(e)).toList();
    }else {
      throw Exception("Failed with status code: ${response.statusCode}");
    }
  }
}