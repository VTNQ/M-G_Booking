import 'package:flutter/material.dart';
import 'package:flutter_paypal_payment/flutter_paypal_payment.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class PaymentPage extends StatelessWidget {
  final String? hotelName;
  final String? checkInDate;
  final String? checkOutDate;
  final int numberOfGuests;
  final String? roomType;
  final double? hotelPrice;

  final String airlineName;
  final String departureDate;
  final String? returnDate;
  final String departureTime;
  final String? arrivalTime;
  final double flightPrice;

  const PaymentPage({
    Key? key,
    this.hotelName,
    this.checkInDate,
    this.checkOutDate,
    required this.numberOfGuests,
    this.roomType,
    this.hotelPrice,
    required this.airlineName,
    required this.departureDate,
    this.returnDate,
    required this.departureTime,
    this.arrivalTime,
    required this.flightPrice,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    double totalAmount = hotelPrice! + flightPrice;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Payment Details'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Hotel Booking Details
            Card(
              elevation: 4,
              margin: const EdgeInsets.only(bottom: 16),
              child: Padding(
                padding: const EdgeInsets.all(12),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      'Hotel Booking',
                      style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 8),
                    Text('Hotel Name: $hotelName'),
                    Text('Check-in Date: $checkInDate'),
                    Text('Check-out Date: $checkOutDate'),
                    Text('Number of Guests: $numberOfGuests'),
                    Text('Room Type: $roomType'),
                    Text('Price: \$${hotelPrice?.toStringAsFixed(2)}'),
                  ],
                ),
              ),
            ),

            // Flight Booking Details
            Card(
              elevation: 4,
              margin: const EdgeInsets.only(bottom: 16),
              child: Padding(
                padding: const EdgeInsets.all(12),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      'Flight Booking',
                      style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 8),
                    Text('Airline: $airlineName'),
                    Text('Departure Date: $departureDate'),
                    Text('Return Date: $returnDate'),
                    Text('Departure Time: $departureTime'),
                    Text('Arrival Time: $arrivalTime'),
                    Text('Price: \$${flightPrice.toStringAsFixed(2)}'),
                  ],
                ),
              ),
            ),

            // Total Amount
            Card(
              elevation: 4,
              margin: const EdgeInsets.only(bottom: 16),
              child: Padding(
                padding: const EdgeInsets.all(12),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      'Total Amount',
                      style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 8),
                    Text('Total Hotel Cost: \$${hotelPrice?.toStringAsFixed(2)}'),
                    Text('Total Flight Cost: \$${flightPrice.toStringAsFixed(2)}'),
                    const Divider(),
                    Text('Grand Total: \$${totalAmount.toStringAsFixed(2)}',
                        style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
                  ],
                ),
              ),
            ),

            // PayPal Payment Button
            Center(
              child: ElevatedButton(
                onPressed: () {
                  _makePayment(context, totalAmount);
                },
                child: const Text('Pay with PayPal'),
              ),
            ),
          ],
        ),
      ),
    );
  }

  void _makePayment(BuildContext context, double amount) async {
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (BuildContext context ) => PaypalCheckoutPage(amount: amount),
      ),
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
          onPressed: () => _initiatePaypalPayment(context),
        ),
      ),
    );
  }

  void _initiatePaypalPayment(BuildContext context) async {
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (BuildContext context) => PaypalCheckoutView(
          sandboxMode: true,
          clientId: "YOUR_CLIENT_ID",
          secretKey: "YOUR_SECRET_KEY",
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
              "description": "Pay for booking",
            }
          ],
          note: "Contact us for support.",
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
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Thanh toán thành công!')),
    );
    Navigator.of(context).popUntil((route) => route.isFirst);
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