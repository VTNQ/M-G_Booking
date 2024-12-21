import 'package:flutter/material.dart';

class Register extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return RegisterPage();
  }
}

class RegisterPage extends State<Register> {
  var email = TextEditingController(text: "");
  var password = TextEditingController(text: "");
  var confirmPassword=TextEditingController(text: "");
  @override
  void initState() {
    super.initState();
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
                  child: IconButton(onPressed: (){}, icon:
                  Icon(Icons.arrow_back)),
                ),
                SizedBox(height: 20,),
                Text("Register Account",
                  style: TextStyle(
                      fontSize: 28,
                      fontWeight: FontWeight.bold
                  ),),
                SizedBox(height: 8,),
                Text("Complete your details or continue\n with social medias",
                  textAlign: TextAlign.center,
                  style: TextStyle(color: Colors.grey[600]),
                ),
                SizedBox(height: 40,),
                TextField(
                  controller: email,
                  decoration: InputDecoration(
                    labelText: "Email",
                    prefixIcon: Icon(Icons.email),
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(8),
                    ),
                  ),
                ),
                SizedBox(height: 16,),
                TextField(
                  controller: password,
                  obscureText: true,
                  decoration: InputDecoration(
                    labelText: "Password",
                    prefixIcon: Icon(Icons.lock),
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(8),
                    ),
                  ),
                ),
                SizedBox(height: 16,),
                TextField(
                  controller: confirmPassword,
                  obscureText: true,
                  decoration: InputDecoration(
                    labelText: "Confirm Password",
                    prefixIcon: Icon(Icons.lock),
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(8),
                    ),
                  ),
                ),
                SizedBox(height: 16,),

                SizedBox(height: 20,),
                SizedBox(
                  width: double.infinity,
                  child: ElevatedButton(onPressed: (){},style:ElevatedButton.styleFrom(backgroundColor: Colors.orange,shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),padding: EdgeInsets.symmetric(vertical: 16)) , child:Text("Register",style: TextStyle(color: Colors.white,fontSize: 16),)),
                )
              ],
            ),
          ),
        )
    );
  }
}