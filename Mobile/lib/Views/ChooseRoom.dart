import 'package:flutter/material.dart';
import 'package:mobile/APIs/RoomAPI.dart';
import 'package:mobile/Model/RoomDetailHotel.dart';

import 'PaymentPage.dart';

class RoomSelectionPage extends StatefulWidget {
  final int hotelId;
  final PaymentPage paymentPage;


  const RoomSelectionPage({required this.hotelId, required this.paymentPage});

  @override
  State<RoomSelectionPage> createState() => _RoomSelectionPageState();
}

class _RoomSelectionPageState extends State<RoomSelectionPage> {
  late Future<List<RoomDetailHotel>> roomDetails;
  int quantityRoom=0;
  @override
  void initState() {
    super.initState();
    roomDetails = RoomApi().getRoomByHotelId(widget.hotelId);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Choose Room for Hotel: ${widget.paymentPage.hotelName}'),
      ),
      body: FutureBuilder<List<RoomDetailHotel>>(
        future: roomDetails,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return Center(child: Text('No rooms available'));
          } else {
            final rooms = snapshot.data!;
            return ListView.builder(
              padding: const EdgeInsets.all(16),
              itemCount: rooms.length,
              itemBuilder: (context, index) {
                final room = rooms[index];
                return Card(
                  margin: const EdgeInsets.only(bottom: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                  elevation: 4,
                  child: Padding(
                    padding: const EdgeInsets.all(12),
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        // Room Image
                        room.imageUrl.isNotEmpty
                            ? ClipRRect(
                          borderRadius: BorderRadius.circular(8),
                          child: Image.network(
                            "https://example.com/images/${room.imageUrl}", // Ensure this is a valid URL
                            height: 100,
                            width: 100,
                            fit: BoxFit.cover,
                            errorBuilder: (context, error, stackTrace) {
                              return Container(
                                height: 100,
                                width: 100,
                                color: Colors.grey[200],
                                child: const Icon(
                                  Icons.error,
                                  size: 40,
                                  color: Colors.red,
                                ),
                              );
                            },
                          ),
                        )
                            : Container(
                          height: 100,
                          width: 100,
                          decoration: BoxDecoration(
                            color: Colors.grey[200],
                            borderRadius: BorderRadius.circular(8),
                          ),
                          child: const Icon(
                            Icons.hotel,
                            size: 40,
                            color: Colors.grey,
                          ),
                        ),
                        const SizedBox(width: 12), // Space between image and text
                        // Room Information
                        Expanded(
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                            // Room Type
                            Text(
                            room.type,
                            style: const TextStyle(
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                          const SizedBox(height: 4),
                          // Room Price
                          Text(
                            'Price: \$${room.price.toStringAsFixed(2)}',
                            style: const TextStyle(
                              fontSize: 16,
                              color: Colors.green,
                            ),
                          ),
                          const SizedBox(height: 4),
                          // Room Occupancy
                          Text(
                            'Occupancy: ${room.occupancy}',
                            style: const TextStyle(
                              fontSize: 14,
                              color: Colors.black54,
                            ),
                          ),
                          const SizedBox(height: 12),
                          // Quantity Selector and Choose Button
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              // Quantity Selector
                              DropdownButton<int>(
                                value: 1, // Default quantity
                                items: List.generate(10, (index) => index + 1)
                                    .map((quantity) => DropdownMenuItem<int>(
                                  value: quantity,
                                  child: Text(quantity.toString()),
                                ))
                                    .toList(),
                                onChanged: (value) {
                                  quantityRoom=value!;
                                  print('Selected quantity: $value');
                                },
                                // Choose Button
                              ),
                              ElevatedButton(
                                onPressed: () {
                                  Navigator.push(context, MaterialPageRoute(builder: (context)=>PaymentPage(hotelName: widget.paymentPage.hotelName,airlineName: widget.paymentPage.airlineName,arrivalTime: widget.paymentPage.arrivalTime,checkInDate: widget.paymentPage.checkInDate, checkOutDate: widget.paymentPage.checkOutDate, departureDate: widget.paymentPage.departureDate, departureTime: widget.paymentPage.departureTime, flightPrice: widget.paymentPage.flightPrice,hotelPrice: room.price.toDouble()*quantityRoom, numberOfGuests: widget.paymentPage.numberOfGuests, returnDate: widget.paymentPage.returnDate, roomType: room.type,)));
                                },
                                child: const Text('Choose'),
                              ),
                                ],
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                  ),
                );
              },
            );
          }
        },
      ),
    );
  }
}