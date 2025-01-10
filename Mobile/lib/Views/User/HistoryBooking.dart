import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class BookingHistoryPage extends StatefulWidget {
  const BookingHistoryPage({Key? key}) : super(key: key);

  @override
  State<BookingHistoryPage> createState() => _BookingHistoryPageState();
}

class _BookingHistoryPageState extends State<BookingHistoryPage> {
  final List<Map<String, dynamic>> _bookings = [
    {
      'id': '1',
      'bookingDate': DateTime.now().subtract(const Duration(days: 5)),
      'totalAmount': 1250.0,
      'status': 'Completed',
      'flight': {
        'from': 'Ho Chi Minh',
        'to': 'Ha Noi',
        'departureDate': '2024-01-15',
        'returnDate': '2024-01-20',
        'passengers': 2,
        'seatClass': 'Economy',
      },
      'hotel': {
        'name': 'Luxury Hotel', 
        'checkIn': '2024-01-15',
        'checkOut': '2024-01-20',
        'rooms': 1,
      },
    },
    // Add more sample bookings here
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Booking History'),
      ),
      body: _bookings.isEmpty
          ? const Center(
        child: Text('No booking history available'),
      )
          : ListView.builder(
        itemCount: _bookings.length,
        itemBuilder: (context, index) {
          final booking = _bookings[index];
          return _buildBookingCard(context, booking);
        },
      ),
    );
  }

  Widget _buildBookingCard(BuildContext context, Map<String, dynamic> booking) {
    final formatter = NumberFormat.currency(locale: 'vi_VN', symbol: 'đ');

    return Card(
      margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
      elevation: 3,
      child: InkWell(
        onTap: () {
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => BookingDetailPage(booking: booking),
            ),
          );
        },
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    'Booking #${booking['id']}',
                    style: const TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  Container(
                    padding: const EdgeInsets.symmetric(
                      horizontal: 8,
                      vertical: 4,
                    ),
                    decoration: BoxDecoration(
                      color: booking['status'] == 'Completed'
                          ? Colors.green[100]
                          : Colors.orange[100],
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: Text(
                      booking['status'],
                      style: TextStyle(
                        color: booking['status'] == 'Completed'
                            ? Colors.green[800]
                            : Colors.orange[800],
                      ),
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 12),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        'Booking Date',
                        style: TextStyle(color: Colors.grey),
                      ),
                      Text(
                        DateFormat('dd MMM yyyy').format(booking['bookingDate']),
                        style: const TextStyle(fontWeight: FontWeight.w500),
                      ),
                    ],
                  ),
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.end,
                    children: [
                      const Text(
                        'Total Amount',
                        style: TextStyle(color: Colors.grey),
                      ),
                      Text(
                        formatter.format(booking['totalAmount']),
                        style: const TextStyle(
                          fontWeight: FontWeight.bold,
                          color: Colors.blue,
                        ),
                      ),
                    ],
                  ),
                ],
              ),
              const SizedBox(height: 12),
              Row(
                children: [
                  const Icon(Icons.flight, color: Colors.blue, size: 20),
                  const SizedBox(width: 8),
                  Text(
                    '${booking['flight']['from']} → ${booking['flight']['to']}',
                    style: TextStyle(color: Colors.grey[600]),
                  ),
                ],
              ),
              if (booking['hotel'] != null) ...[
                const SizedBox(height: 4),
                Row(
                  children: [
                    const Icon(Icons.hotel, color: Colors.blue, size: 20),
                    const SizedBox(width: 8),
                    Text(
                      booking['hotel']['name'],
                      style: TextStyle(color: Colors.grey[600]),
                    ),
                  ],
                ),
              ],
            ],
          ),
        ),
      ),
    );
  }
}

// booking_detail_page.dart
class BookingDetailPage extends StatelessWidget {
  final Map<String, dynamic> booking;

  const BookingDetailPage({Key? key, required this.booking}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final formatter = NumberFormat.currency(locale: 'vi_VN', symbol: 'đ');

    return Scaffold(
      appBar: AppBar(
        title: Text('Booking #${booking['id']}'),
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // Booking Summary Card
              Card(
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        'Booking Summary',
                        style: TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(height: 16),
                      _buildInfoRow('Booking Date',
                          DateFormat('dd MMM yyyy').format(booking['bookingDate'])),
                      _buildInfoRow('Status', booking['status']),
                      _buildInfoRow(
                          'Total Amount', formatter.format(booking['totalAmount'])),
                    ],
                  ),
                ),
              ),
              const SizedBox(height: 16),

              // Flight Details Card
              Card(
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Row(
                        children: [
                          Icon(Icons.flight, color: Colors.blue),
                          SizedBox(width: 8),
                          Text(
                            'Flight Details',
                            style: TextStyle(
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ],
                      ),
                      const SizedBox(height: 16),
                      _buildInfoRow('From', booking['flight']['from']),
                      _buildInfoRow('To', booking['flight']['to']),
                      _buildInfoRow(
                          'Departure', booking['flight']['departureDate']),
                      _buildInfoRow('Return', booking['flight']['returnDate']),
                      _buildInfoRow('Passengers',
                          booking['flight']['passengers'].toString()),
                      _buildInfoRow('Class', booking['flight']['seatClass']),
                    ],
                  ),
                ),
              ),
              const SizedBox(height: 16),

              // Hotel Details Card (if exists)
              if (booking['hotel'] != null)
                Card(
                  child: Padding(
                    padding: const EdgeInsets.all(16),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Row(
                          children: [
                            Icon(Icons.hotel, color: Colors.blue),
                            SizedBox(width: 8),
                            Text(
                              'Hotel Details',
                              style: TextStyle(
                                fontSize: 18,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ],
                        ),
                        const SizedBox(height: 16),
                        _buildInfoRow('Hotel Name', booking['hotel']['name']),
                        _buildInfoRow('Check-in', booking['hotel']['checkIn']),
                        _buildInfoRow('Check-out', booking['hotel']['checkOut']),
                        _buildInfoRow(
                            'Rooms', booking['hotel']['rooms'].toString()),
                      ],
                    ),
                  ),
                ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildInfoRow(String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(
            label,
            style: const TextStyle(color: Colors.grey),
          ),
          Text(
            value,
            style: const TextStyle(fontWeight: FontWeight.w500),
          ),
        ],
      ),
    );
  }
}