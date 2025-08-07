import 'package:flutter/material.dart';
import 'package:mobile/APIs/SeatAPI.dart';
import 'package:mobile/Model/HotelBooking.dart';
import 'package:mobile/Model/Seat.dart';
import 'package:mobile/Views/ListHotel.dart';
import 'package:mobile/Views/PaymentPage.dart';

enum SeatClass {
  business,
  firstClass,
  economy,
}

class SeatSelectionPage extends StatefulWidget {
  final int idFlight;
  final PaymentPage paymentPage;
  final HotelBooking? hotelBooking;

  const SeatSelectionPage({
    super.key,
    required this.idFlight,
    required this.paymentPage,
    this.hotelBooking,
  });

  @override
  State<SeatSelectionPage> createState() => _SeatSelectionPageState();
}

class _SeatSelectionPageState extends State<SeatSelectionPage> {
  List<String> selectedSeats = [];
  late Future<List<Seat>> seatClassDB;
  final List<String> rows = ['A', 'B', 'C', 'D', 'E', 'F'];
  int totalRows = 0;

  // Map seat types to colors
  Color getSeatColor(String type, bool isSelected) {
    if (isSelected) return Colors.blue;

    switch (type.toLowerCase()) {
      case 'business':
        return Colors.green.shade100;
      case 'first':
        return Colors.red.shade100;
      case 'economy':
        return Colors.grey.shade200;
      default:
        return Colors.grey.shade200;
    }
  }

  @override
  void initState() {
    super.initState();
    seatClassDB = fetchSeats();
  }

  Future<List<Seat>> fetchSeats() async {
    try {
      List<Seat> seats = await SeatAPI().detailSeat(widget.idFlight);
      totalRows = (seats.length/6).ceil();
      return seats;
    } catch (e) {
      print('Error fetching seats: $e');
      return [];
    }
  }

  Widget buildSeat(Seat seat) {
    bool isSelected = selectedSeats.contains(seat.index);

    return GestureDetector(
      onTap: () {
        if (seat.status == 1) {  // Assuming 1 means available
          setState(() {
            if (isSelected) {
              selectedSeats.remove(seat.index);
            } else if (selectedSeats.length < 4) {
              selectedSeats.add(seat.index);
            } else {
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text('You can only select up to 4 seats')),
              );
            }
          });
        }
      },
      child: Container(
        margin: EdgeInsets.all(2),
        width: 30,
        height: 30,
        decoration: BoxDecoration(
          color: seat.status == 1
              ? getSeatColor(seat.type, isSelected)
              : Colors.grey.shade400,  // Unavailable seat
          borderRadius: BorderRadius.circular(4),
          border: Border.all(
            color: isSelected ? Colors.blue : Colors.grey.shade400,
            width: 1,
          ),
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.1),
              blurRadius: 2,
              offset: Offset(0, 1),
            ),
          ],
        ),
        child: Center(
          child: Text(
            seat.index,
            style: TextStyle(
              fontSize: 10,
              color: isSelected || seat.status != 1
                  ? Colors.white
                  : Colors.black87,
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
      ),
    );
  }

  Widget buildSeatLegend() {
    return Container(
      padding: EdgeInsets.symmetric(vertical: 8),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          _buildLegendItem('Business', 'business'),
          SizedBox(width: 16),
          _buildLegendItem('First Class', 'first'),
          SizedBox(width: 16),
          _buildLegendItem('Economy', 'economy'),
          SizedBox(width: 16),
          _buildLegendItem('Unavailable', 'unavailable'),
        ],
      ),
    );
  }

  Widget _buildLegendItem(String label, String type) {
    return Row(
      children: [
        Container(
          width: 20,
          height: 20,
          decoration: BoxDecoration(
            color: type == 'unavailable'
                ? Colors.grey.shade400
                : getSeatColor(type, false),
            borderRadius: BorderRadius.circular(4),
            border: Border.all(
              color: Colors.grey.shade400,
              width: 1,
            ),
          ),
        ),
        SizedBox(width: 4),
        Text(
          label,
          style: TextStyle(fontSize: 12),
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Select Seat'),
        elevation: 0,
      ),
      body: FutureBuilder<List<Seat>>(
        future: seatClassDB,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return Center(child: Text('No seats available'));
          }

          List<Seat> seats = snapshot.data!;

          return Column(
            children: [
              buildSeatLegend(),
              Expanded(
                child: SingleChildScrollView(
                  child: Column(
                    children: [
                      Container(
                        padding: EdgeInsets.symmetric(horizontal: 16),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Container(width: 50, child: Text('Window', style: TextStyle(fontSize: 12))),
                            Expanded(
                              child: Center(
                                child: Text('Aisle', style: TextStyle(fontSize: 12)),
                              ),
                            ),
                            Container(width: 50, child: Text('Window', style: TextStyle(fontSize: 12))),
                          ],
                        ),
                      ),
                      ...List.generate(
                        (seats.length / 6).ceil(),
                            (rowIndex) {
                          int startIndex = rowIndex * 6;
                          List<Seat> rowSeats = seats.skip(startIndex).take(6).toList();

                          return Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              ...rowSeats.take(3).map((seat) => buildSeat(seat)),
                              SizedBox(width: 20),
                              ...rowSeats.skip(3).map((seat) => buildSeat(seat)),
                            ],
                          );
                        },
                      ),
                    ],
                  ),
                ),
              ),
              Container(
                padding: EdgeInsets.all(16),
                decoration: BoxDecoration(
                  color: Colors.white,
                  boxShadow: [
                    BoxShadow(
                      color: Colors.black12,
                      blurRadius: 4,
                      offset: Offset(0, -2),
                    ),
                  ],
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    Text(
                      'Selected Seats: ${selectedSeats.join(", ")}',
                      style: TextStyle(fontSize: 14),
                    ),
                    SizedBox(height: 16),
                    ElevatedButton(
                      onPressed: selectedSeats.isNotEmpty
                          ? () {
                        if (widget.hotelBooking != null) {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => ListHotelPage(
                                hotelsearchDTO: widget.hotelBooking!,
                                paymentPage: widget.paymentPage,
                              ),
                            ),
                          );
                        } else {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => PaymentPage(
                                airlineName: widget.paymentPage.airlineName,
                                departureDate: widget.paymentPage.departureDate,
                                departureTime: widget.paymentPage.departureTime,
                                flightPrice: widget.paymentPage.flightPrice,
                                numberOfSeats: widget.paymentPage.numberOfSeats,
                              ),
                            ),
                          );
                        }
                      }
                          : null,
                      child: Text('Confirm (${selectedSeats.length}/4)'),
                    ),
                  ],
                ),
              ),
            ],
          );
        },
      ),
    );
  }
}