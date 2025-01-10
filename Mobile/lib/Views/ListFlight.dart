import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:mobile/Model/Flight.dart';

class FlightPage extends StatefulWidget {
  final Flight searchCriteria;

  const FlightPage({Key? key, required this.searchCriteria}) : super(key: key);

  @override
  State<FlightPage> createState() => _FlightListPageState();
}

class _FlightListPageState extends State<FlightPage> {


  final List<FlightResult> flights = [
    FlightResult(
      airline: 'Vietnam Airlines',
      flightNumber: 'VN123',
      departureTime: '08:00',
      arrivalTime: '10:00',
      price: 1200000,
      duration: '2h',
      stops: 0,
    ),
    FlightResult(
      airline: 'Bamboo Airways',
      flightNumber: 'QH456',
      departureTime: '10:30',
      arrivalTime: '12:30',
      price: 990000,
      duration: '2h',
      stops: 0,
    ),
    // Add more dummy data as needed
  ];

  String _selectedFilter = 'Lowest Price';
  final List<String> _filterOptions = [
    'Lowest Price',
    'Earliest Departure',
    'Latest Departure',
    'Shortest Duration'
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              '${widget.searchCriteria.from} → ${widget.searchCriteria.to}',
              style: const TextStyle(fontSize: 16),
            ),
            Text(
              widget.searchCriteria.departureTime??'',
              style: const TextStyle(fontSize: 12),
            ),
          ],
        ),
        actions: [
          IconButton(
            icon: const Icon(Icons.filter_list),
            onPressed: _showFilterOptions,
          ),
        ],
      ),
      body: Column(
        children: [
          _buildFlightSummary(),
          Expanded(
            child: ListView.builder(
              itemCount: flights.length,
              itemBuilder: (context, index) {
                return _buildFlightCard(flights[index]);
              },
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildFlightSummary() {
    return Container(
      padding: const EdgeInsets.all(16),
      color: Colors.grey[100],
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text('${flights.length} flights found'),
          Text('Sorted by: $_selectedFilter'),
        ],
      ),
    );
  }

  Widget _buildFlightCard(FlightResult flight) {
    return Card(
      margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Row(
              children: [
                Image.asset(
                  'assets/airline_logos/${flight.airline.toLowerCase().replaceAll(' ', '_')}.png',
                  width: 40,
                  height: 40,
                  errorBuilder: (context, error, stackTrace) {
                    return Container(
                      width: 40,
                      height: 40,
                      color: Colors.grey[300],
                      child: const Icon(Icons.flight, color: Colors.grey),
                    );
                  },
                ),
                const SizedBox(width: 12),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        flight.airline,
                        style: const TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 16,
                        ),
                      ),
                      Text(
                        'Flight ${flight.flightNumber}',
                        style: TextStyle(
                          color: Colors.grey[600],
                          fontSize: 14,
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                _buildTimeColumn(flight.departureTime, widget.searchCriteria.from??''),
                Expanded(
                  child: Column(
                    children: [
                      const Icon(Icons.arrow_forward, color: Colors.grey),
                      Text(
                        flight.duration,
                        style: TextStyle(
                          color: Colors.grey[600],
                          fontSize: 12,
                        ),
                      ),
                      if (flight.stops > 0)
                        Text(
                          '${flight.stops} stop${flight.stops > 1 ? 's' : ''}',
                          style: TextStyle(
                            color: Colors.grey[600],
                            fontSize: 12,
                          ),
                        ),
                    ],
                  ),
                ),
                _buildTimeColumn(flight.arrivalTime, widget.searchCriteria.to??''),
              ],
            ),
            const SizedBox(height: 16),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  NumberFormat.currency(locale: 'vi_VN', symbol: 'đ')
                      .format(flight.price),
                  style: const TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.deepPurple,
                  ),
                ),
                ElevatedButton(
                  onPressed: () {
                    // Handle selection
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.deepPurple,
                    padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                  ),
                  child: const Text('Select',style: TextStyle(color: Colors.blue),),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildTimeColumn(String time, String location) {
    return Column(
      children: [
        Text(
          time,
          style: const TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: 16,
          ),
        ),
        Text(
          location,
          style: TextStyle(
            color: Colors.grey[600],
            fontSize: 14,
          ),
        ),
      ],
    );
  }

  void _showFilterOptions() {
    showModalBottomSheet(
      context: context,
      builder: (BuildContext context) {
        return Container(
          padding: const EdgeInsets.all(16),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              const Text(
                'Sort Flights',
                style: TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 16),
              ...List.generate(
                _filterOptions.length,
                    (index) => ListTile(
                  title: Text(_filterOptions[index]),
                  trailing: _selectedFilter == _filterOptions[index]
                      ? const Icon(Icons.check, color: Colors.deepPurple)
                      : null,
                  onTap: () {
                    setState(() {
                      _selectedFilter = _filterOptions[index];
                    });
                    Navigator.pop(context);
                  },
                ),
              ),
            ],
          ),
        );
      },
    );
  }
}

class FlightResult {
  final String airline;
  final String flightNumber;
  final String departureTime;
  final String arrivalTime;
  final double price;
  final String duration;
  final int stops;

  FlightResult({
    required this.airline,
    required this.flightNumber,
    required this.departureTime,
    required this.arrivalTime,
    required this.price,
    required this.duration,
    required this.stops,
  });
}