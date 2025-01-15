import 'dart:convert';

import 'package:mobile/APIs/BaseUrl.dart';
import 'package:mobile/Model/City.dart';
import 'package:http/http.dart' as http;
class CityAPI{
  Future<List<City>>SearchCity(String name) async{
    try{
      final Uri uri = Uri.parse(BaseUrl.baseUrl+ "/city/SearchHotelByCityOrHotel").replace(queryParameters: {
      'name':name
      });
      var response = await http.get(
        uri,
        headers: {
          'Content-Type': 'application/json', // Set the content type to JSON
        },
      );
      if (response.statusCode == 200) {
        List<dynamic> dyn = jsonDecode(response.body);

        return dyn.map((e) => City.fromMap(e)).toList();
      } else {
        throw Exception("Failed with status code: ${response.statusCode}");
      }
    }catch(ex){
      print(ex);
      return Future.value(null);
    }
  }
Future<List<City>>getAll(int id) async{
  try{
    var response=await http.get(Uri.parse(BaseUrl.baseUrl+"/city/FindCityByCountry/${id}"));
    if(response.statusCode==200){
      List<dynamic>dyn=jsonDecode(response.body);
      return dyn.map((e)=>City.fromMap(e)).toList();
    }else{
      throw Exception("Bad request");
    }
  }catch(ex){
    print(ex);
    return Future.value(null);
  }

}
}