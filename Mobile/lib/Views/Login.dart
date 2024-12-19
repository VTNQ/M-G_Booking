import 'package:flutter/material.dart';

class LoginPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return LoginPageView();
  }
}

class LoginPageView extends State<LoginPage> {
  var email = TextEditingController(text: "");
  var password = TextEditingController(text: "");

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Container(
        padding: EdgeInsets.only(bottom: 3),
        child: Center(
            child: SingleChildScrollView(
              reverse: true,
              child: Column(
                  children: [
                    Align(
                      alignment: Alignment.topLeft,
                      child: Padding(
                        padding: const EdgeInsets.all(3),
                        child: IconButton(onPressed: ()=>"", icon: Icon(Icons.arrow_back)),
                      ),
                    ),
                    Card(
                      color: Colors.white,
                      margin: EdgeInsets.only(bottom: 3),
                      child: Column(
                        children: [
                          Align(
                            alignment: Alignment.topCenter,
                            child: Padding(
                              padding: EdgeInsets.only(bottom: 3),
                              child: Text("Login",style: TextStyle(fontSize: 33)),
                            ),
                          ),
                          Align(
                            alignment: Alignment.center,
                            child: Padding(
                              padding: const EdgeInsets.only(top: 36),
                              child: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  TextField(
                                    decoration: InputDecoration(labelText: 'Email'),
                                  ),
                                  TextField(
                                    obscureText: true,
                                    decoration: InputDecoration(labelText: 'Mật khẩu'),
                                  ),
                                  ElevatedButton(
                                    onPressed: () {
                                      print('Đăng nhập');
                                    },
                                    child: Text('Đăng nhập'),
                                  ),
                                  TextButton(
                                    onPressed: () {
                                      print('Quên mật khẩu');
                                    },
                                    child: Text('Quên mật khẩu?'),
                                  ),
                                ],
                              ),
                            ),
                          )
                        ],
                      ),
                    )
                  ]
              ),
            )
        )
        // child: Column(
        //   mainAxisAlignment: MainAxisAlignment.center,
        //   children: [
        //     TextField(
        //       decoration: InputDecoration(labelText: 'Email'),
        //     ),
        //     TextField(
        //       obscureText: true,
        //       decoration: InputDecoration(labelText: 'Mật khẩu'),
        //     ),
        //     ElevatedButton(
        //       onPressed: () {
        //         print('Đăng nhập');
        //       },
        //       child: Text('Đăng nhập'),
        //     ),
        //     TextButton(
        //       onPressed: () {
        //         print('Quên mật khẩu');
        //       },
        //       child: Text('Quên mật khẩu?'),
        //     ),
        //   ],
        // ),
      ),
    );
  }
}