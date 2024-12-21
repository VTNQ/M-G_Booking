import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class OtpVerificationScreen extends StatefulWidget {
  const OtpVerificationScreen({Key? key}) : super(key: key);

  @override
  State<OtpVerificationScreen> createState() => _OtpVerificationScreenState();
}

class _OtpVerificationScreenState extends State<OtpVerificationScreen> {
  List<String> otp = ['', '', '', ''];
  List<FocusNode> focusNodes = List.generate(4, (_) => FocusNode());

  @override
  void dispose() {
    // Dispose the focus nodes when the screen is disposed
    for (var node in focusNodes) {
      node.dispose();
    }
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        // Optionally manage custom back button behavior here
        return true; // Allow back navigation
      },
      child: Scaffold(
        appBar: AppBar(
          title: const Text("OTP Verification"),
        ),
        body: Padding(
          padding: const EdgeInsets.all(20.0),
          child: Column(
            children: [
              const Text(
                "OTP Verification",
                style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 10),
              const Text(
                'We sent your code to +1 898 860',
                style: TextStyle(fontSize: 16),
              ),
              const SizedBox(height: 30),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: List.generate(4, (index) {
                  return SizedBox(
                    width: 50,
                    height: 50,
                    child: TextField(
                      focusNode: focusNodes[index],
                      onChanged: (value) {
                        setState(() {
                          otp[index] = value;
                        });

                        // Move to the next field if a digit is entered
                        if (value.length == 1 && index < 3) {
                          FocusScope.of(context).requestFocus(focusNodes[index + 1]);
                        }

                        // Move to the previous field if the current field is cleared (backspace)
                        if (value.isEmpty && index > 0) {
                          FocusScope.of(context).requestFocus(focusNodes[index - 1]);
                        }
                      },
                      textAlign: TextAlign.center,
                      keyboardType: TextInputType.number,
                      decoration: InputDecoration(
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                      ),
                    ),
                  );
                }),
              ),
              const SizedBox(height: 10),
              TextButton(
                onPressed: () {
                  // Resend OTP logic here
                },
                child: const Text('Resend OTP Code'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
