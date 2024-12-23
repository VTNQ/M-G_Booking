import 'package:mobile/APIs/BaseUrl.dart';
import 'package:http/http.dart' as http;

class AccountAPI{
  static String url=BaseUrl.baseUrl+"/account";
  static String registerUserUrl=BaseUrl.baseUrl+"/account/register/user";


  Future<bool> registerUser() async {
    var response = await http.post(Uri.parse(registerUserUrl));
    if (response.statusCode == 200) {
      return true;
    } else {
      return false;
    }
  }
}