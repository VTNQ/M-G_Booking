import 'package:flutter/material.dart';


import '../../../constants.dart';


class SignUpForm extends StatefulWidget {
  const SignUpForm({super.key});

  @override
  _SignUpFormState createState() => _SignUpFormState();
}

class _SignUpFormState extends State<SignUpForm> {
  final _formKey = GlobalKey<FormState>();
  String? email;
  String? password;
  bool _rememberMe = false;
  String? conform_password;
  bool remember = false;
  final List<String?> errors = [];

  void addError({String? error}) {
    if (!errors.contains(error)) {
      setState(() {
        errors.add(error);
      });
    }
  }

  void removeError({String? error}) {
    if (errors.contains(error)) {
      setState(() {
        errors.remove(error);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: Column(
        children: [
          TextFormField(
            obscureText: false, // Set to false for email field
            onSaved: (newValue) => email = newValue, // Save the value for email or other fields
            onChanged: (value) {
              if (value.isNotEmpty) {
                removeError(error: kEmailNullError); // Replace with appropriate error checks
              }
              email = value;
            },
            validator: (value) {
              if (value!.isEmpty) {
                addError(error: kEmailNullError); // Replace with the relevant error for email
                return "";
              }
              return null;
            },
            decoration: InputDecoration(
              labelText: "Email", // Label for the text field
              hintText: "Enter your email", // Hint text for guidance
              floatingLabelBehavior: FloatingLabelBehavior.always, // Always float the label
              prefixIcon: Icon(Icons.email, color: Colors.grey), // Envelope icon on the left side
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(16), // Rounded corners for a smoother look
                borderSide: BorderSide(color: Colors.grey.shade400, width: 1.5), // Border color and width
              ),
              focusedBorder: OutlineInputBorder(
                borderRadius: BorderRadius.circular(16), // Rounded corners when focused
                borderSide: BorderSide(color: Colors.blue, width: 2), // Focused border color
              ),
              enabledBorder: OutlineInputBorder(
                borderRadius: BorderRadius.circular(16), // Rounded corners when enabled
                borderSide: BorderSide(color: Colors.grey.shade400, width: 1.5), // Border color when enabled
              ),
              contentPadding: EdgeInsets.symmetric(vertical: 16, horizontal: 20), // Better padding for comfort
              hintStyle: TextStyle(color: Colors.grey.shade600), // Lighter color for hint text
              labelStyle: TextStyle(
                color: Colors.grey.shade700, // Darker color for the label text
                fontWeight: FontWeight.w500, // Slightly bold label
              ),
            ),
          ),
          const SizedBox(height: 20),
          TextFormField(
            obscureText: true, // Set to true for password field
            onSaved: (newValue) => password = newValue!, // Save the password value
            onChanged: (value) {
              if (value.isNotEmpty) {
                removeError(error: kPassNullError); // Replace with appropriate error check for password
              }
              password = value;
            },
            validator: (value) {
              if (value!.isEmpty) {
                addError(error: kPassNullError); // Replace with the relevant error for password
                return "";
              }
              // Optional: Additional password validation (e.g., length, special characters)
              if (value.length < 6) {
                addError(error: kPassNullError); // Custom error for short password
                return "";
              }
              return null;
            },
            decoration: InputDecoration(
              labelText: "Password", // Label for the text field
              hintText: "Enter your password", // Hint text for guidance
              floatingLabelBehavior: FloatingLabelBehavior.always, // Always float the label
              prefixIcon: Icon(Icons.lock, color: Colors.grey), // Lock icon on the left side
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(16), // Rounded corners for a smoother look
                borderSide: BorderSide(color: Colors.grey.shade400, width: 1.5), // Border color and width
              ),
              focusedBorder: OutlineInputBorder(
                borderRadius: BorderRadius.circular(16), // Rounded corners when focused
                borderSide: BorderSide(color: Colors.blue, width: 2), // Focused border color
              ),
              enabledBorder: OutlineInputBorder(
                borderRadius: BorderRadius.circular(16), // Rounded corners when enabled
                borderSide: BorderSide(color: Colors.grey.shade400, width: 1.5), // Border color when enabled
              ),
              contentPadding: EdgeInsets.symmetric(vertical: 16, horizontal: 20), // Better padding for comfort
              hintStyle: TextStyle(color: Colors.grey.shade600), // Lighter color for hint text
              labelStyle: TextStyle(
                color: Colors.grey.shade700, // Darker color for the label text
                fontWeight: FontWeight.w500, // Slightly bold label
              ),
            ),
          ),


          const SizedBox(height: 20),
          TextFormField(
            obscureText: true, // Set to true for password field
            onSaved: (newValue) => conform_password = newValue!, // Save the password value
            onChanged: (value) {
              if (value.isNotEmpty) {
                removeError(error: kPassNullError); // Replace with appropriate error check for password
              }
              conform_password = value;
            },
            validator: (value) {
              if (value!.isEmpty) {
                addError(error: kPassNullError); // Replace with the relevant error for password
                return "";
              }
              // Optional: Additional password validation (e.g., length, special characters)
              if (value.length < 6) {
                addError(error: kPassNullError); // Custom error for short password
                return "";
              }
              return null;
            },
            decoration: InputDecoration(
              labelText: "Confirm Password", // Label for the text field
              hintText: "Re-enter your password", // Hint text for guidance
              floatingLabelBehavior: FloatingLabelBehavior.always, // Always float the label
              prefixIcon: Icon(Icons.lock, color: Colors.grey), // Lock icon on the left side
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(16), // Rounded corners for a smoother look
                borderSide: BorderSide(color: Colors.grey.shade400, width: 1.5), // Border color and width
              ),
              focusedBorder: OutlineInputBorder(
                borderRadius: BorderRadius.circular(16), // Rounded corners when focused
                borderSide: BorderSide(color: Colors.blue, width: 2), // Focused border color
              ),
              enabledBorder: OutlineInputBorder(
                borderRadius: BorderRadius.circular(16), // Rounded corners when enabled
                borderSide: BorderSide(color: Colors.grey.shade400, width: 1.5), // Border color when enabled
              ),
              contentPadding: EdgeInsets.symmetric(vertical: 16, horizontal: 20), // Better padding for comfort
              hintStyle: TextStyle(color: Colors.grey.shade600), // Lighter color for hint text
              labelStyle: TextStyle(
                color: Colors.grey.shade700, // Darker color for the label text
                fontWeight: FontWeight.w500, // Slightly bold label
              ),
            ),
          ),

          const SizedBox(height: 20),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween, // Canh lề các phần tử trong Row
            children: [
              Row( // Nhóm Checkbox và Text "Remember me"
                children: [
                  Checkbox(
                    value: _rememberMe, // Thay thế bằng biến trạng thái của bạn
                    onChanged: (value) {
                      setState(() {
                        _rememberMe = value!;
                      });
                    },
                  ),
                  const Text("Remember me"),
                ],
              ),
              TextButton( // Sử dụng TextButton cho "Forgot Password"
                onPressed: () {
                  // Xử lý khi người dùng nhấn vào "Forgot Password"
                  // Ví dụ: điều hướng đến màn hình quên mật khẩu
                },
                child: const Text(
                  "Forgot Password",
                  style: TextStyle(
                    color: Colors.grey, // Điều chỉnh màu sắc nếu cần
                    decoration: TextDecoration.underline, // Thêm gạch chân
                  ),
                ),
              ),
            ],
          ),
          ElevatedButton(
            onPressed: () {
              // Your existing onPressed function
            },
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.orange, // Bạn có thể thay đổi màu nền nếu muốn
              textStyle: const TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                  color: Colors.white // Đặt màu chữ thành trắng
              ),
              padding: const EdgeInsets.symmetric(horizontal: 100, vertical: 22), // Tăng padding ngang để nút rộng hơn
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(25.0),
              ),
            ),

            child: Text(
              "Continue",
              style: TextStyle(
                color: Colors.white, // Text color
              ),
            ),
          )
        ],
      ),
    );
  }
}
