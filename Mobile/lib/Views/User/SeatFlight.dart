import 'package:flutter/material.dart';
import 'package:mobile/Views/PaypalCheckout.dart';

// Enum để định nghĩa các loại ghế
enum SeatClass {
  business,
  firstClass,
  economy
}

class SeatSelectionPage extends StatefulWidget {
  @override
  State<SeatSelectionPage> createState() => SeatFlight();
}

class SeatFlight extends State<SeatSelectionPage> {
  List<String> selectedSeats = [];
  int SeatSelect = 0;

  final double seatSize = 40.0;
  final List<String> rows = ['A', 'B', 'C', 'D', 'E', 'F'];
  final int totalRows = 13;

  // Mock database của các ghế và loại của chúng
  // Trong thực tế, bạn sẽ lấy dữ liệu này từ API hoặc database thực
  final Map<String, SeatClass> seatClassDB = {
    // Business Class (2 hàng đầu)
    'A1': SeatClass.business, 'B1': SeatClass.business, 'C1': SeatClass.business,
    'D1': SeatClass.business, 'E1': SeatClass.business, 'F1': SeatClass.business,
    'A2': SeatClass.business, 'B2': SeatClass.business, 'C2': SeatClass.business,
    'D2': SeatClass.business, 'E2': SeatClass.business, 'F2': SeatClass.business,

    // First Class (3 hàng tiếp theo)
    'A3': SeatClass.firstClass, 'B3': SeatClass.firstClass, 'C3': SeatClass.firstClass,
    'D3': SeatClass.firstClass, 'E3': SeatClass.firstClass, 'F3': SeatClass.firstClass,
    'A4': SeatClass.firstClass, 'B4': SeatClass.firstClass, 'C4': SeatClass.firstClass,
    'D4': SeatClass.firstClass, 'E4': SeatClass.firstClass, 'F4': SeatClass.firstClass,
    'A5': SeatClass.firstClass, 'B5': SeatClass.firstClass, 'C5': SeatClass.firstClass,
    'D5': SeatClass.firstClass, 'E5': SeatClass.firstClass, 'F5': SeatClass.firstClass,
  };

  // Màu sắc cho từng loại ghế
  final Map<SeatClass, Color> classColors = {
    SeatClass.business: Colors.purple[100]!,
    SeatClass.firstClass: Colors.amber[100]!,
    SeatClass.economy: Colors.blue[100]!,
  };

  Widget buildSeatLegend() {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              _legendItem(classColors[SeatClass.business]!, 'Business Class'),
              SizedBox(width: 16),
              _legendItem(classColors[SeatClass.firstClass]!, 'First Class'),
            ],
          ),
          SizedBox(height: 8),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              _legendItem(classColors[SeatClass.economy]!, 'Economy Class'),
              SizedBox(width: 16),
              _legendItem(Colors.blue[400]!, 'Selected'),
              SizedBox(width: 16),
              _legendItem(Colors.red[400]!, 'Booked'),
            ],
          ),
        ],
      ),
    );
  }

  Widget _legendItem(Color color, String label) {
    return Row(
      children: [
        Container(
          width: 24,
          height: 24,
          decoration: BoxDecoration(
            color: color,
            borderRadius: BorderRadius.circular(4),
          ),
        ),
        SizedBox(width: 8),
        Text(label, style: TextStyle(fontSize: 12)),
      ],
    );
  }

  SeatClass getSeatClass(String seatNumber) {
    // Kiểm tra trong database, nếu không có thì mặc định là economy
    return seatClassDB[seatNumber] ?? SeatClass.economy;
  }

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
              SnackBar(content: Text('You can only select up to'+'seats')),
            );
          }
        });
      },
      child: Container(
        margin: EdgeInsets.all(2),
        width: seatSize,
        height: seatSize,
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
      body: Column(
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
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => CartPage()),
                    );
                  }
                      : null,
                  child: Text('Confirm (${selectedSeats.length}/4)'),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}