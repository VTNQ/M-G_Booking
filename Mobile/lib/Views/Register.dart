// import 'package:flutter/material.dart';
//
// class RegisterPage extends StatefulWidget {
//   @override
//   State<StatefulWidget> createState() {
//     return LoginPageView();
//   }
// }
//
// class LoginPageView extends State<RegisterPage> {
//   var email = TextEditingController(text: "");
//   var password = TextEditingController(text: "");
//   var confirmPassword=TextEditingController(text: "");
//   @override
//   void initState() {
//     super.initState();
//   }
//
//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       backgroundColor: Colors.white,
//       body: SingleChildScrollView(
//         reverse: true,
//         child: Padding(
//           padding: const EdgeInsets.all(16.0),
//           child: Column(
//             crossAxisAlignment: CrossAxisAlignment.center,
//             children: [
//               Align(
//                 alignment: Alignment.topLeft,
//                 child: IconButton(onPressed: ()=>(), icon:
//                 Icon(Icons.arrow_back)),
//               ),
//               SizedBox(height: 20,),
//               Text("Sign Up",
//               style: TextStyle(
//                 fontSize: 28,
//                 fontWeight: FontWeight.bold
//               ),),
//               SizedBox(height: 8,),
//               Text("Sign up with your email and password",
//               textAlign: TextAlign.center,
//                 style: TextStyle(color: Colors.grey[600]),
//               ),
//               Padding(padding: EdgeInsets.only(top: 33)),
//               SizedBox(height: 40,),
//               TextField(
//                 controller: email,
//                 decoration: InputDecoration(
//                   labelText: "Email",
//                   prefixIcon: Icon(Icons.email),
//                   border: OutlineInputBorder(
//                     borderRadius: BorderRadius.circular(8),
//                   ),
//                 ),
//               ),
//               SizedBox(height: 16,),
//               TextField(
//                 controller: password,
//                 obscureText: true,
//                 decoration: InputDecoration(
//                   labelText: "Password",
//                   prefixIcon: Icon(Icons.lock),
//                   border: OutlineInputBorder(
//                     borderRadius: BorderRadius.circular(8),
//                   ),
//                 ),
//               ),
//               SizedBox(height: 16,),
//               TextField(
//                 controller: confirmPassword,
//                 obscureText: true,
//                 decoration: InputDecoration(
//                   labelText: "Confirm Password",
//                   prefixIcon: Icon(Icons.lock),
//                   border: OutlineInputBorder(
//                     borderRadius: BorderRadius.circular(8),
//                   ),
//                 ),
//               ),
//               SizedBox(height: 16,),
//               Row(
//                 mainAxisAlignment: MainAxisAlignment.spaceBetween,
//                 children: [
//                   Row(
//                     children: [
//                       Checkbox(value: false, onChanged: (value){},),
//                       Text("Remember Me"),
//                     ],
//                   ),
//                   TextButton(onPressed: (){}, child: Text(
//                     "Forgot Password",
//                     style: TextStyle(color: Colors.orange),
//                   )),
//                 ],
//               ),
//               SizedBox(height: 20,),
//               SizedBox(
//                 width: double.infinity,
//                 child: ElevatedButton(onPressed: (){},style:ElevatedButton.styleFrom(backgroundColor: Colors.orange,shape: RoundedRectangleBorder(
//                   borderRadius: BorderRadius.circular(8),
//                 ),padding: EdgeInsets.symmetric(vertical: 16)) , child:Text("Continue",style: TextStyle(color: Colors.white,fontSize: 16),)),
//               )
//             ],
//           ),
//         ),
//       )
//     );
//   }
// }