class Airport {
  int? id;
  String? name;
  int? cityId;
  Airport({this.id, this.name, this.cityId});

  Airport.fromMap(Map<String, dynamic> map) {
    id = map["id"];
    name = map["name"];
    cityId = map["cityId"];
  }
  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      "id": id,
      "name": name,
      "cityId": cityId,
    };
  }
}