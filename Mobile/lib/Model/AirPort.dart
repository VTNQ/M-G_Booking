class Airport {
  int? id;
  String? name;
  int? cityId;
  Airport({this.id, this.name});

  Airport.fromMap(Map<String, dynamic> map) {
    id = map["id"];
    name = map["name"];
  }
  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      "id": id,
      "name": name,
    };
  }
}