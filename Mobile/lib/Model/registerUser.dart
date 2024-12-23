class RegisterUser {
  String? fullName;
  String? email;
  String? accountType;
  String? password;
  String? phone;
  int? countryId;

  RegisterUser({
    this.fullName,
    this.email,
    this.accountType,
    this.password,
    this.phone,
    this.countryId,
  });

  Map<String, dynamic> toJson() {
    return {
      'fullName': fullName,
      'email': email,
      'accountType': accountType,
      'password': password,
      'phone': phone,
      'country_id': countryId,
    };
  }

  factory RegisterUser .fromJson(Map<String, dynamic> json) {
    return RegisterUser(
      fullName: json['fullName'],
      email: json['email'],
      accountType: json['accountType'],
      password: json['password'],
      phone: json['phone'],
      countryId: json['country_id'],
    );
  }
}
