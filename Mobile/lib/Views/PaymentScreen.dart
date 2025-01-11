import 'package:flutter/material.dart';
import 'package:mobile/APIs/PaypalService.dart';

class PaymentScreen extends StatelessWidget{
  final PaypalServices service = PaypalServices();

  void initPayment() async {
    String? accessToken = await service.getAccessToken();
    if(accessToken != null){
      print('Access Token: $accessToken');
      Map<String, dynamic> transactions = {
        'intent': 'sale',
        'payer': {'payment_method': 'paypal'},
        'transactions': [
          {
            'amount': {'total': '1.99', 'currency': 'USD'},
          }
        ],
        'redirect_urls': {
          'return_url': "https://example.com/success",
          'cancel_url': "https://example.com/cancel",
        }
      };
      var response = await service.createPaypalPayment(transactions, accessToken);
      if(response != null){
        print('Payment response: ${response}');
      }
    }
  }
  @override
  Widget build(BuildContext context){
    return Scaffold(
      appBar: AppBar(
        title: Text('Payment'),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: (){
            initPayment();
          },
          child: Text('Pay with Paypal'),
        ),
      ),
    );
  }
}