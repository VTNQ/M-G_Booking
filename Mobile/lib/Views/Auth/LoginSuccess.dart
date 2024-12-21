import 'package:flutter/material.dart';


class LoginSuccessScreen extends StatelessWidget {
  static String routeName = "/login_success";

  const LoginSuccessScreen({super.key});
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: const SizedBox(),
        title: const Text("Login Success"),
      ),
      body: Column(
        children: [
          const SizedBox(height: 16),
          Image.asset(
            "assets/images/success.png",
            height: MediaQuery.of(context).size.height * 0.4, //40%
          ),
          const SizedBox(height: 16),
          const Text(
            "Login Success",
            style: TextStyle(
              fontSize: 30,
              fontWeight: FontWeight.bold,
              color: Colors.black,
            ),
          ),
          const Spacer(),
          SizedBox(
            width: 400,
            child: ElevatedButton(onPressed: (){},style:ElevatedButton.styleFrom(backgroundColor: Colors.orange,shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20),
            ),padding: EdgeInsets.symmetric(vertical: 16)) , child:Text("Back To Home",style: TextStyle(color: Colors.white,fontSize: 16),)),
          ),
          const Spacer(),
        ],
      ),
    );
  }
}
