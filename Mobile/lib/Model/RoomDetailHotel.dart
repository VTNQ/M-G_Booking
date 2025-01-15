import 'package:decimal/decimal.dart';
import 'package:html/parser.dart' as html;

class RoomDetailHotel {
  int id;
  String type;
  Decimal price;
  String imageUrl;
  int occupancy;
  List<AmenitiesList> amenitiesLists;

  RoomDetailHotel({
    required this.id,
    required this.type,
    required this.price,
    required this.occupancy,
    required this.imageUrl,
    required this.amenitiesLists,
  });

  // Convert the instance to a Map
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'type': type,
      'price': price.toString(), // Convert Decimal to String
      'occupancy': occupancy,
      'imageUrl': imageUrl,
      'amenitiesLists': amenitiesLists.map((amenity) => amenity.toMap()).toList(),
    };
  }

  // Create an instance from a Map
  factory RoomDetailHotel.fromMap(Map<String, dynamic> map) {
    return RoomDetailHotel(
      id: map['id']??0,
      type: map['type']??'',
      price: Decimal.parse(map['price'].toString()), // Convert String to Decimal
      occupancy: map['occupancy']??0,
      imageUrl: map['imageUrl']??'',
      amenitiesLists: List<AmenitiesList>.from(
        map['amenitiesLists'].map((amenity) => AmenitiesList.fromMap(amenity))??[],
      ),
    );
  }
}

class AmenitiesList {
  String name;

  AmenitiesList({required this.name});

  // Convert the instance to a Map
  Map<String, dynamic> toMap() {
    return {
      'name': name,
    };
  }

  // Create an instance from a Map
  factory AmenitiesList.fromMap(Map<String, dynamic> map) {
    return AmenitiesList(
      name: map['name'],
    );
  }
}