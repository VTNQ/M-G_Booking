import 'dart:convert';
import 'package:http/http.dart' as http;

class PaypalServices {
  final String clientId = "Ae6v5M1SfUcvEDX1XZm_gC_0u_2PyujSRrwsbgOflWeM-n78u7LNYBlpcfzaicbfMxfqbvq3HeSXPP-2";
  final String clientSecret="EJtyYvh85z9CDt1lAXEQO72pvdaOuJzQSqUv6MCbiVyF5rVpQKBvNy8J2lJi8jCH2fbWvQyQ4JNcGKJv";
  final String sandboxApiUrl = "https://api.sandbox.paypal.com";

  Future<String> getAccessToken() async {
    final String basicAuth = 'Basic ' + base64Encode(utf8.encode('$clientId:'));
    final response = await http.post(
      Uri.parse('$sandboxApiUrl/v1/oauth2/token'),
      headers: <String, String>{
        'Authorization': basicAuth,
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    );
    if (response.statusCode == 200) {
      return jsonDecode(response.body)['access_token'];
    } else {
      throw Exception('Failed to get access token');
    }
  }

  Future<Map<String, dynamic>?> createPaypalPayment(
      Map<String, dynamic> transactions, String accessToken) async {
    var response = await http.post(
      Uri.parse("$sandboxApiUrl/v1/payments/payment"),
      body: json.encode(transactions),
      headers: {
        "Content-Type": "application/json",
        'Authorization': 'Bearer $accessToken',
      },
    );
    if (response.statusCode == 201) {
      return json.decode(response.body);
    }
    return null;
  }
}