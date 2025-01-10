class Hotel{
  int? id;
  String? name;
  String? address;
  String? description;
  int? city_id;

  Hotel({
    required this.id,
    required this.name,
    required this.address,
    required this.description,
    required this.city_id,
  });

  Hotel.fromMap(Map<String,dynamic> map){
    id = map['id'];
    name = map['name'];
    address = map['address'];
    description = map['description'];
    city_id = map['city_id'];
  }
  Map<String,dynamic> toMap(){
    return <String,dynamic>{
      "id": id,
      "name": name,
      "address": address,
      "description": description,
      "city_id": city_id,
    };
  }
}