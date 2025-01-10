import 'package:flutter/material.dart';

class SeatSelectionPage extends StatefulWidget {
  @override
  State<SeatSelectionPage> createState() => SeatFlight();
}

class SeatFlight extends State<SeatSelectionPage> {
  List<String> selectedSeats = [];

  final Map<String, bool> occupiedSeats = {
    'A1': true, 'B3': true, 'C4': true, 'D2': true, 'E3': true, 'F2': true
  };

  final double seatSize = 40.0;
  final List<String> rows = ['A', 'B', 'C', 'D', 'E', 'F'];
  final int totalRows = 13;

  Widget buildSeatLegend() {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          _legendItem(Colors.grey[300]!, 'Ghế trống'),
          SizedBox(width: 16),
          _legendItem(Colors.red[100]!, 'Đã đặt'),
          SizedBox(width: 16),
          _legendItem(Colors.blue[400]!, 'Đang chọn'),
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

  Widget buildSeat(String seatNumber) {
    bool isOccupied = occupiedSeats[seatNumber] ?? false;
    bool isSelected = selectedSeats.contains(seatNumber);

    return GestureDetector(
      onTap: () {
        if (!isOccupied) {
          setState(() {
            if (isSelected) {
              selectedSeats.remove(seatNumber);
            } else if (selectedSeats.length < 4) {
              selectedSeats.add(seatNumber);
            } else {
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text('Bạn chỉ có thể chọn tối đa 4 ghế')),
              );
            }
          });
        }
      },
      child: Container(
        margin: EdgeInsets.all(2),
        width: seatSize,
        height: seatSize,
        decoration: BoxDecoration(
          color: isOccupied
              ? Colors.red[100]
              : isSelected
              ? Colors.blue[400]
              : Colors.grey[300],
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
        title: Text('Chọn ghế'),
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
                        Container(width: 50, child: Text('Cửa sổ', style: TextStyle(fontSize: 12))),
                        Expanded(
                          child: Center(
                            child: Text('Lối đi', style: TextStyle(fontSize: 12)),
                          ),
                        ),
                        Container(width: 50, child: Text('Cửa sổ', style: TextStyle(fontSize: 12))),
                      ],
                    ),
                  ),
                  for (int i = 1; i <= totalRows; i++)
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        // Left window seats
                        buildSeat('${rows[0]}$i'),
                        buildSeat('${rows[1]}$i'),
                        buildSeat('${rows[2]}$i'),
                        // Aisle
                        SizedBox(width: 20),
                        // Right window seats
                        buildSeat('${rows[3]}$i'),
                        buildSeat('${rows[4]}$i'),
                        buildSeat('${rows[5]}$i'),
                      ],
                    ),
                ],
              ),
            ),
          ),
          // Selected seats summary
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
                  'Ghế đã chọn: ${selectedSeats.join(", ")}',
                  style: TextStyle(fontSize: 14),
                ),
                SizedBox(height: 16),
                ElevatedButton(
                  onPressed: selectedSeats.isNotEmpty
                      ? () {
                    // Handle seat confirmation
                  }
                      : null,
                  child: Text('Xác nhận (${selectedSeats.length}/4)'),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}