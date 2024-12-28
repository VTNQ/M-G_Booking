import 'package:mobile/APIs/BaseUrl.dart';
import 'package:http/http.dart' as http;

class AccountAPI{
  static String AccountUrl=BaseUrl.baseUrl+"/account";
  static String registerUserUrl=AccountUrl+"/register/user";
  static String forgetPasswordUrl=AccountUrl+"/forgetPassword";


  Future<bool> registerUser() async {
    var response = await http.post(Uri.parse(registerUserUrl));
    if (response.statusCode == 200) {
      return true;
    } else {
      return false;
    }
  }

  Future<bool> forgetPassword() async{
    var response=await http.post(Uri.parse(forgetPasswordUrl));
    if(response.statusCode==200){
      return true;
    }else{
      return false;
    }
  }
}