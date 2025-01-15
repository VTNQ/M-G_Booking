class Hotel {
  int? id;
  String? name;
  String? city;
  String? country;
  String? imageUrl; // Changed to String for URL compatibility
  double price; // Non-nullable with a default value

  // Constructor
  Hotel({
    this.id,
    this.name,
    this.city,
    this.country,
    this.imageUrl, // Optional and nullable
    required this.price, // Required field
  });

  // Named constructor to create an instance from a Map
 factory Hotel.fromMap(Map<String, dynamic> map){
    return Hotel(
      id: map['id']??0,
      name: map['name']??'',
      city: map['city']??'',
      country: map['country']??'',
      imageUrl: map['image_url']??'',
      price: (map['price'] as num).toDouble()??0.0,
    );
  }

  // Method to convert an instance to a Map
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'name': name,
      'city': city,
      'country': country,
      'image_url': imageUrl, // Use the correct key for image URL
      'price': price,
    };
  }
}
