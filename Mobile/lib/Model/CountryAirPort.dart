import 'package:mobile/Model/AirPort.dart';

class CountryAirPort {
  String? country;
  List<Airport>? airports;
  CountryAirPort({required this.country,required this.airports});

  CountryAirPort.fromMap(Map<String, dynamic> map) {
    country = map["country"];
    if(map["aiportDTOS"] == null){
      airports = [];
    }else{
      airports = map["aiportDTOS"].map<Airport>((e) => Airport.fromMap(e)).toList();
    }
  }

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      "country": country,
      "aiportDTOS": airports!.map((e) => e.toMap()).toList(),
    };
  }
}