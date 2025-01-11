import 'package:flutter/material.dart';
import 'package:mobile/APIs/HotelAPI.dart';
import 'package:mobile/Model/Hotel.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class ListHotelPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => ListHotel();
}

class ListHotel extends State<ListHotelPage> {
  List<Hotel> hotels = [];
  TextEditingController searchController = TextEditingController();
  var hotelAPI = HotelAPI();

  @override
  void initState() {
    super.initState();
    hotelAPI.getHotelByCountryId();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          "Hotels",
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
        actions: [
          IconButton(
            onPressed: () {},
            icon: const Icon(Icons.filter_list),
          ),
          IconButton(
            onPressed: () {},
            icon: const Icon(Icons.search),
          ),
        ],
      ),
      body: Column(
        children: [
          // Search TextField
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: TextField(
              controller: searchController,
              decoration: InputDecoration(
                hintText: 'Search hotels...',
                prefixIcon: const Icon(Icons.search),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(12),
                ),
                filled: true,
                fillColor: Colors.grey[100],
              ),
              onChanged: (value) {
                // Implement search functionality
                setState(() {});
              },
            ),
          ),
          // Hotel List
          Expanded(
            child: ListView.builder(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              itemCount: hotels.length,
              itemBuilder: (context, index) {
                final hotel = hotels[index];
                return Card(
                  margin: const EdgeInsets.only(bottom: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                  elevation: 4,
                  child: IntrinsicHeight(
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.stretch,
                      children: [
                        // Hotel Image (Left side)
                        ClipRRect(
                          borderRadius: const BorderRadius.horizontal(
                            left: Radius.circular(12),
                          ),
                          child: Image.asset(
                            'assets/images/hotel.jpg',
                            width: 120,
                            height: double.infinity,
                            fit: BoxFit.cover,
                            errorBuilder: (context, error, stackTrace) {
                              return Container(
                                width: 120,
                                color: Colors.grey[200],
                                child: const Icon(
                                  Icons.hotel,
                                  size: 40,
                                  color: Colors.grey,
                                ),
                              );
                            },
                          ),
                        ),
                        // Hotel Information (Right side)
                        Expanded(
                          child: Padding(
                            padding: const EdgeInsets.all(12),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                // Hotel Name
                                Text(
                                  hotel.name ?? 'Unknown Hotel',
                                  style: const TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                  maxLines: 2,
                                  overflow: TextOverflow.ellipsis,
                                ),
                                const SizedBox(height: 8),
                                // Location
                                Row(
                                  children: [
                                    const Icon(
                                      Icons.location_on,
                                      size: 14,
                                      color: Colors.grey,
                                    ),
                                    const SizedBox(width: 4),
                                    Expanded(
                                      child: Text(
                                        hotel.address ??
                                            'Address not available',
                                        style: const TextStyle(
                                          color: Colors.grey,
                                          fontSize: 12,
                                        ),
                                        maxLines: 1,
                                        overflow: TextOverflow.ellipsis,
                                      ),
                                    ),
                                  ],
                                ),
                                const SizedBox(height: 8),
                                // Description
                                Text(
                                  hotel.description ??
                                      'No description available',
                                  style: const TextStyle(
                                    fontSize: 14,
                                    color: Colors.grey,
                                  ),
                                  maxLines: 2,
                                  overflow: TextOverflow.ellipsis,
                                ),
                                const SizedBox(height: 12),
                                // Book Now Button
                                SizedBox(
                                  width: double.infinity,
                                  child: ElevatedButton(
                                    onPressed: () {
                                      // Handle booking
                                      print(
                                          'Booking hotel with ID: ${hotel.id}');
                                    },
                                    style: ElevatedButton.styleFrom(
                                      padding: const EdgeInsets.symmetric(
                                          vertical: 8),
                                      shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(8),
                                      ),
                                    ),
                                    child: const Text(
                                      'Book Now',
                                      style: TextStyle(fontSize: 14),
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }

  @override
  void dispose() {
    searchController.dispose();
    super.dispose();
  }
}