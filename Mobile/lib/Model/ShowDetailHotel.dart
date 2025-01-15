import 'package:html/parser.dart' as html;
import 'package:decimal/decimal.dart';

class ShowDetailHotel {
  int id;
  String name;
  String address;
  String city;
  String description;
  String country;
  Decimal price;

  ShowDetailHotel({
    required this.id,
    required this.name,
    required String address,
    required this.city,
    required String description,
    required this.country,
    required this.price,
  })  : this.address = _cleanHtml(address),
        this.description = _cleanHtml(description);

  static String _cleanHtml(String input) {
    if (input.isNotEmpty) {
      return html.parse(input).documentElement?.text ?? "";
    }
    return "";
  }

  // Convert the instance to a Map
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'name': name,
      'address': address,
      'city': city,
      'description': description,
      'country': country,
      'price': price // Convert Decimal to String
    };
  }

  // Create an instance from a Map
  factory ShowDetailHotel.fromMap(Map<String, dynamic> map) {
    return ShowDetailHotel(
      id: map['id'],
      name: map['name'],
      address: map['address'],
      city: map['city'],
      description: map['description'],
      country: map['country'],
      price: Decimal.parse(map['price']), // Convert String to Decimal
    );
  }
}