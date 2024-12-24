import 'package:flutter/material.dart';
import 'package:mobile/APIs/CountryAPi.dart';
import 'package:mobile/Model/Country.dart';
import 'package:mobile/Model/registerUser.dart';

class Register extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return RegisterPage();
  }
}

class RegisterPage extends State<Register> {
  final TextEditingController fullNameController = TextEditingController();
  final TextEditingController emailController = TextEditingController();
  final TextEditingController phoneController = TextEditingController();
  final TextEditingController accountTypeController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();
  final TextEditingController confirmPasswordController = TextEditingController();

  String? selectedCountry; // Quốc gia được chọn
  late Future<List<Country>> countries; // Danh sách quốc gia

  @override
  void initState() {
    super.initState();
    countries = CountryAPI().getCountries(); // Gọi API lấy danh sách quốc gia
  }

  void register() {
    var registerUser = RegisterUser(
      fullName: fullNameController.text,
      email: emailController.text,
      accountType: accountTypeController.text,
      password: passwordController.text,
      phone: phoneController.text,
      countryId: selectedCountry != null ? int.parse(selectedCountry!) : 0, // Lấy ID quốc gia
    );
    // Thực hiện xử lý đăng ký (gọi API hoặc lưu dữ liệu)
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SingleChildScrollView(
        reverse: true,
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Align(
                alignment: Alignment.topLeft,
                child: IconButton(
                  onPressed: () {
                    Navigator.pop(context);
                  },
                  icon: Icon(Icons.arrow_back),
                ),
              ),
              SizedBox(height: 40),
              Text(
                "Register Account",
                style: TextStyle(fontSize: 28, fontWeight: FontWeight.bold),
              ),
              SizedBox(height: 16),
              Text(
                "Complete your details or continue\nwith social media",
                textAlign: TextAlign.center,
                style: TextStyle(color: Colors.grey[600]),
              ),
              SizedBox(height: 16),
              TextField(
                controller: fullNameController,
                decoration: InputDecoration(
                  labelText: "Full Name",
                  prefixIcon: Icon(Icons.person),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
              ),
              SizedBox(height: 16),
              TextField(
                controller: emailController,
                decoration: InputDecoration(
                  labelText: "Email",
                  prefixIcon: Icon(Icons.email),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
              ),
              SizedBox(height: 16),
              TextField(
                controller: phoneController,
                decoration: InputDecoration(
                  labelText: "Phone",
                  prefixIcon: Icon(Icons.phone),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
              ),
              SizedBox(height: 16),
              FutureBuilder<List<Country>>(
                future: countries,
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return CircularProgressIndicator();
                  } else if (snapshot.hasError) {
                    return Text("Failed to load countries");
                  } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                    return Text("No countries available");
                  } else {
                    final countryList = snapshot.data!;
                    return DropdownButton<String>(
                      hint: Text("Select Country"),
                      value: selectedCountry,
                      onChanged: (String? newValue) {
                        setState(() {
                          selectedCountry = newValue;
                        });
                      },
                      items: countryList.map<DropdownMenuItem<String>>((Country country) {
                        return DropdownMenuItem<String>(
                          value: country.id.toString(), // Dùng ID làm giá trị
                          child: Text(country.name!),
                        );
                      }).toList(),
                    );
                  }
                },
              ),
              SizedBox(height: 16),
              TextField(
                controller: passwordController,
                obscureText: true,
                decoration: InputDecoration(
                  labelText: "Password",
                  prefixIcon: Icon(Icons.lock),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
              ),
              SizedBox(height: 16),
              TextField(
                controller: confirmPasswordController,
                obscureText: true,
                decoration: InputDecoration(
                  labelText: "Confirm Password",
                  prefixIcon: Icon(Icons.lock),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
              ),
              SizedBox(height: 20),
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: register,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.orange,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(8),
                    ),
                    padding: EdgeInsets.symmetric(vertical: 16),
                  ),
                  child: Text(
                    "Register",
                    style: TextStyle(color: Colors.white, fontSize: 16),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
