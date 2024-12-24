class Country{
  int? id;
  String? name;
  Country({this.id, this.name});

  Country.fromMap(Map<String, dynamic> map) {
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