class City{
  int?id;
  String?name;
  City({this.id,this.name});
  City.fromMap(Map<String, dynamic> map) {
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