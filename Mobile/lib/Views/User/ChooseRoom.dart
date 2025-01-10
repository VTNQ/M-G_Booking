import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class RoomSelectionPage extends StatefulWidget {
  @override
  State<RoomSelectionPage> createState() => Chooseroom();
}

class Chooseroom extends State<RoomSelectionPage> {
  int? selectedRoomId;

  final List<Map<String, dynamic>> rooms = [
    {
      'id': 1,
      'type': 'Phòng Deluxe',
      'price': 2500000,
      'capacity': 2,
      'size': '30m²',
      'amenities': ['WiFi', 'Bữa sáng', 'Minibar', 'TV'],
      'image': 'assets/room1.jpg',
    },
    {
      'id': 2,
      'type': 'Phòng Superior',
      'price': 1800000,
      'capacity': 2,
      'size': '25m²',
      'amenities': ['WiFi', 'Bữa sáng', 'TV'],
      'image': 'assets/room2.jpg',
    },
    {
      'id': 3,
      'type': 'Phòng Suite',
      'price': 3500000,
      'capacity': 3,
      'size': '45m²',
      'amenities': ['WiFi', 'Bữa sáng', 'Minibar', 'TV', 'Bồn tắm'],
      'image': 'assets/room3.jpg',
    },
  ];

  String formatPrice(int price) {
    final formatter = NumberFormat.currency(locale: 'vi_VN', symbol: 'đ');
    return formatter.format(price);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Chọn Phòng'),
        elevation: 0,
      ),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Text(
              'Vui lòng chọn loại phòng phù hợp với nhu cầu của bạn',
              style: TextStyle(
                color: Colors.grey[600],
                fontSize: 12,
              ),
            ),
          ),
          Expanded(
            child: ListView.builder(
              padding: EdgeInsets.all(8),
              itemCount: rooms.length,
              itemBuilder: (context, index) {
                final room = rooms[index];
                final isSelected = selectedRoomId == room['id'];

                return Card(
                  margin: EdgeInsets.only(bottom: 8),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                    side: BorderSide(
                      color: isSelected ? Colors.blue : Colors.transparent,
                      width: 1,
                    ),
                  ),
                  child: InkWell(
                    onTap: () {
                      setState(() {
                        selectedRoomId = room['id'];
                      });
                    },
                    child: Padding(
                      padding: EdgeInsets.all(8),
                      child: Row(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          // Room Image
                          ClipRRect(
                            borderRadius: BorderRadius.circular(4),
                            child: Container(
                              width: 120,
                              height: 80,
                              color: Colors.grey[300],
                              child: Center(
                                child: Text('Hình phòng'),
                              ),
                            ),
                          ),
                          SizedBox(width: 12),
                          // Room Details
                          Expanded(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  room['type'],
                                  style: TextStyle(
                                    fontWeight: FontWeight.bold,
                                    fontSize: 14,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Row(
                                  children: [
                                    Icon(Icons.person_outline, size: 14),
                                    SizedBox(width: 4),
                                    Text(
                                      '${room['capacity']} người',
                                      style: TextStyle(fontSize: 12),
                                    ),
                                    SizedBox(width: 8),
                                    Icon(Icons.bedroom_parent_outlined, size: 14),
                                    SizedBox(width: 4),
                                    Text(
                                      room['size'],
                                      style: TextStyle(fontSize: 12),
                                    ),
                                  ],
                                ),
                                SizedBox(height: 4),
                                Wrap(
                                  spacing: 4,
                                  runSpacing: 4,
                                  children: (room['amenities'] as List<String>)
                                      .map((amenity) => Container(
                                    padding: EdgeInsets.symmetric(
                                      horizontal: 6,
                                      vertical: 2,
                                    ),
                                    decoration: BoxDecoration(
                                      color: Colors.grey[200],
                                      borderRadius:
                                      BorderRadius.circular(4),
                                    ),
                                    child: Text(
                                      amenity,
                                      style: TextStyle(fontSize: 10),
                                    ),
                                  ))
                                      .toList(),
                                ),
                              ],
                            ),
                          ),
                          // Price and Select Button
                          Column(
                            crossAxisAlignment: CrossAxisAlignment.end,
                            children: [
                              Text(
                                formatPrice(room['price']),
                                style: TextStyle(
                                  fontWeight: FontWeight.bold,
                                  color: Colors.blue[700],
                                  fontSize: 14,
                                ),
                              ),
                              Text(
                                'mỗi đêm',
                                style: TextStyle(
                                  color: Colors.grey[600],
                                  fontSize: 10,
                                ),
                              ),
                              Padding(
                                padding: EdgeInsets.only(top: 8),
                                child: ElevatedButton(
                                  onPressed: () {
                                    setState(() {
                                      selectedRoomId = room['id'];
                                    });
                                  },
                                  style: ElevatedButton.styleFrom(
                                    padding: EdgeInsets.symmetric(
                                      horizontal: 12,
                                      vertical: 4,
                                    ),
                                    minimumSize: Size(0, 24),
                                    textStyle: TextStyle(fontSize: 12),
                                  ),
                                  child: Text(
                                    isSelected ? 'Đã chọn' : 'Chọn phòng',
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ),
                );
              },
            ),
          ),
          // Bottom Button
          Container(
            padding: EdgeInsets.all(16),
            child: ElevatedButton(
              onPressed: selectedRoomId != null
                  ? () {
                // Handle continue booking
              }
                  : null,
              style: ElevatedButton.styleFrom(
                minimumSize: Size(double.infinity, 40),
              ),
              child: Text('Tiếp tục đặt phòng'),
            ),
          ),
        ],
      ),
    );
  }
}