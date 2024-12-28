class Flight {
  String? id;
  String? from;
  String? to;
  String? departureTime;
  String? arrivalTime;
  double? price;

  Flight({
    this.id,
    this.from,
    this.to,
    this.departureTime,
    this.arrivalTime,
    this.price,
  });

  factory Flight.fromJson(Map<String, dynamic> json) {
    return Flight(
      id: json['id'],
      from: json['from'],
      to: json['to'],
      departureTime: json['departureTime'],
      arrivalTime: json['arrivalTime'],
      price: json['price'].toDouble(),
    );
  }
  Flight.fromMap(Map<String, dynamic> map) {
    id= map["id"];
    from= map["from"];
    to= map["to"];
    departureTime= map["departureTime"];
    arrivalTime= map["arrivalTime"];
    price= map["price"];
  }
  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      "id": id,
      "from": from,
      "to": to,
      "departureTime": departureTime,
      "arrivalTime": arrivalTime,
      "price": price,
    };
  }
}