import 'package:flutter/material.dart';
import 'package:mobile/APIs/SeatAPI.dart';
import 'package:mobile/Model/HotelBooking.dart';
import 'package:mobile/Model/Seat.dart';
import 'package:mobile/Views/ListHotel.dart';
import 'package:mobile/Views/PaymentPage.dart';

// Enum to define seat classes
enum SeatClass {
  business,
  firstClass,
  economy
}

class SeatSelectionPage extends StatefulWidget {
  final int idFlight;
  final PaymentPage paymentPage;
  final HotelBooking hotelBooking;

  const SeatSelectionPage({required this.idFlight,required this.paymentPage,required this.hotelBooking});

  @override
  State<SeatSelectionPage> createState() => SeatFlight();
}

class SeatFlight extends State<SeatSelectionPage> {
  List<String> selectedSeats = [];
  int seatSize = 0;
  final List<String> rows = ['A', 'B', 'C', 'D', 'E', 'F'];
  int totalRows = 0;

  late SeatAPI seatAPI;
  Future<List<Seat>> seatClassDB=Future.value([]);

  @override
  void initState() {
    super.initState();
    seatAPI = SeatAPI();
    fetchSeat();
  }
  Future<void> fetchSeat() async {
    try {
      seatClassDB =seatAPI.detailSeat(widget.idFlight);
      totalRows = (seatSize/6) as int;
      setState(() {});
    } catch (e) {
      print(e);
    }
  }

  // Get seat class based on seat number
  SeatClass getSeatClass(String seatNumber) {
    // Assuming seatClassDB is a List<Seat> and you have a way to map seatNumber to Seat
    // You may need to adjust this logic based on your Seat model
    return SeatClass.economy; // Default to economy for now
  }

  // Build the seat widget
  Widget buildSeat(String seatNumber) {
    bool isSelected = selectedSeats.contains(seatNumber);
    SeatClass seatClass = getSeatClass(seatNumber);
    Color baseColor = classColors[seatClass]!;

    return GestureDetector(
      onTap: () {
        setState(() {
          if (isSelected) {
            selectedSeats.remove(seatNumber);
          } else if (selectedSeats.length < 4) {
            selectedSeats.add(seatNumber);
          } else {
            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(content: Text('You can only select up to 4 seats')),
            );
          }
        });
      },
      child: Container(
        margin: EdgeInsets.all(2),
        width: 30,
        height: 30,
        decoration: BoxDecoration(
          color: isSelected ? Colors.blue[400] : baseColor,
          borderRadius: BorderRadius.circular(4),
          border: Border.all(
            color: isSelected ? Colors.blue : Colors.grey[400]!,
            width: 1,
          ),
        ),
        child: Center(
          child: Text(
            seatNumber,
            style: TextStyle(
              fontSize: 10,
              color: isSelected ? Colors.white : Colors.black87,
            ),
          ),
        ),
      ),
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

          // Assuming the API returns a list of Seat objects
          List<Seat> seats = snapshot.data!;
          seatSize = seats.length;

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
                      for (int i = 1; i <= totalRows; i++)
                        Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            buildSeat('${rows[0]}$i'),
                            buildSeat('${rows[1]}$i'),
                            buildSeat('${rows[2]}$i'),
                            SizedBox(width: 20),
                            buildSeat('${rows[3]}$i'),
                            buildSeat('${rows[4]}$i'),
                            buildSeat('${rows[5]}$i'),
                          ],
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
                        // Navigate to PaymentPage with selected seats
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => ListHotelPage(hotelsearchDTO: widget.hotelBooking, paymentPage: widget.paymentPage),
                          ),
                        );
                      }
                          : null,
                      child: Text('Confirm (${selectedSeats.length}/)'+widget.hotelBooking.roomsCount.toString()),
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

  // Example seat class colors
  final Map<SeatClass, Color> classColors = {
    SeatClass.business: Colors.green,
    SeatClass.firstClass: Colors.red,
    SeatClass.economy: Colors.grey,
  };

  Widget buildSeatLegend() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Container(width: 20, height: 20, color: classColors[SeatClass.business], margin: EdgeInsets.all(4)),
        Text('Business'),
        SizedBox(width: 10),
        Container(width: 20, height: 20, color: classColors[SeatClass.firstClass], margin: EdgeInsets.all(4)),
        Text('First Class'),
        SizedBox(width: 10),
        Container(width: 20, height: 20, color: classColors[SeatClass.economy], margin: EdgeInsets.all(4)),
        Text('Economy'),
      ],
    );
  }
}