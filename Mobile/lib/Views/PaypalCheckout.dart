// cart_page.dart
import 'package:flutter/material.dart';
import 'package:flutter_paypal_payment/flutter_paypal_payment.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class CartPage extends StatefulWidget {
  @override
  _CartPageState createState() => _CartPageState();
}

class _CartPageState extends State<CartPage> {
  // Demo data - thay thế bằng data thật từ state management của bạn
  double totalAmount = 99.99; // Giả sử đây là tổng tiền giỏ hàng

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Giỏ hàng'),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          // Hiển thị thông tin giỏ hàng ở đây
          Text(
            'Tổng tiền: \$${totalAmount.toStringAsFixed(2)}',
            style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          ),
          SizedBox(height: 20),
          // Sử dụng CheckoutButton
          CheckoutButton(amount: totalAmount),
        ],
      ),
    );
  }
}

class CheckoutButton extends StatelessWidget {
  final double amount;

  const CheckoutButton({required this.amount});

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      onPressed: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => PaypalCheckoutPage(amount: amount),
          ),
        );
      },
      child: Text('Checkout with PayPal'),
    );
  }
}

class PaypalCheckoutPage extends StatelessWidget {
  final double amount;

  const PaypalCheckoutPage({required this.amount});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('PayPal Checkout'),
      ),
      body: Center(
        child: ElevatedButton(
          child: Text('Thanh toán với PayPal'),
          onPressed: () => _makePayment(context),
        ),
      ),
    );
  }

  void _makePayment(BuildContext context) async {
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (BuildContext context) => PaypalCheckoutView(
          sandboxMode: true,
          clientId: "Ae6v5M1SfUcvEDX1XZm_gC_0u_2PyujSRrwsbgOflWeM-n78u7LNYBlpcfzaicbfMxfqbvq3HeSXPP-2",
          secretKey: "EJtyYvh85z9CDt1lAXEQO72pvdaOuJzQSqUv6MCbiVyF5rVpQKBvNy8J2lJi8jCH2fbWvQyQ4JNcGKJv",
          transactions: [
            {
              "amount": {
                "total": amount.toString(),
                "currency": "USD",
                "details": {
                  "subtotal": amount.toString(),
                  "shipping": '0',
                  "shipping_discount": 0
                }
              },
              "description": "Pay",
            }
          ],
          note: "Liên hệ chúng tôi để được hỗ trợ.",
          onSuccess: (Map params) async {
            print("Success: $params");
            _onPaymentSuccess(context, params);
          },
          onError: (error) {
            print("Error: $error");
            _onPaymentError(context, error);
          },
          onCancel: (params) {
            print("Cancelled: $params");
            _onPaymentCancelled(context);
          },
        ),
      ),
    );
  }

  void _onPaymentSuccess(BuildContext context, Map params) {
    _verifyPaymentWithServer(params).then((_) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Thanh toán thành công!')),
      );
      Navigator.of(context).popUntil((route) => route.isFirst);
    });
  }

  Future<void> _verifyPaymentWithServer(Map params) async {
    try {
      final response = await http.post(
        Uri.parse('YOUR_API_ENDPOINT/verify-payment'),
        body: json.encode({
          'paymentId': params['paymentId'],
          'payerId': params['payerId'],
          'amount': amount,
        }),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode != 200) {
        throw Exception('Lỗi xác nhận thanh toán');
      }
    } catch (e) {
      print('Error verifying payment: $e');
      rethrow;
    }
  }

  void _onPaymentError(BuildContext context, error) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Lỗi thanh toán: $error')),
    );
  }

  void _onPaymentCancelled(BuildContext context) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Thanh toán đã bị hủy')),
    );
  }
}