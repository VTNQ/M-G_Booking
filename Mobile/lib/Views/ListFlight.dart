import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:mobile/APIs/FlightAPI.dart';
import 'package:mobile/Model/Flight.dart';
import 'package:mobile/Model/ResultFlightDTO.dart';

class FlightPage extends StatefulWidget {
  final Flight searchCriteria;
  final bool isReturn;

  const FlightPage({super.key, required this.searchCriteria, required this.isReturn});

  @override
  State<FlightPage> createState() => _FlightListPageState();
}

class _FlightListPageState extends State<FlightPage> {
  late List<ResultFlightDTO> flights=[];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    fetchFlight();
  }

  Future<void> fetchFlight() async {
    try {
      flights = await FlightAPI().getFlightDepartment(widget.searchCriteria);
      setState(() {});
    } catch (e) {
      print(e);
    } finally {
      setState(() {
        isLoading = false;
      });
    }
  }

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
              widget.searchCriteria.departureTime ?? '',
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
      body: isLoading
          ? Center(child: CircularProgressIndicator())
          : Column(
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

  Widget _buildFlightCard(ResultFlightDTO flight) {
    return Card(
      margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            Row(
              children: [
                Image.network(
                  flight.imageUrl,
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
                        flight.nameAirline,
                        style: const TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 16,
                        ),
                      ),
                      Text(
                        'Flight ${flight.idFlight}',
                        // Assuming idFlight is the flight number
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
                _buildTimeColumn(flight.timeDepart, flight.nameAirport),
                Expanded(
                  child: Column(
                    children: [
                      const Icon(Icons.arrow_forward, color: Colors.grey),
                      Text(
                        flight.durationString,
                        // Use the formatted duration string
                        style: TextStyle(
                          color: Colors.grey[600],
                          fontSize: 12,
                        ),
                      ),
                      if (flight.duration.inMinutes >
                          0) // Check for stops if applicable
                        Text(
                          '${flight.duration.inMinutes ~/ 60}h ${flight.duration
                              .inMinutes % 60}m',
                          style: TextStyle(
                            color: Colors.grey[600],
                            fontSize: 12,
                          ),
                        ),
                    ],
                  ),
                ),
                _buildTimeColumn(flight.timeArrival, flight.nameArrivalAirport),
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
                    padding: const EdgeInsets.symmetric(
                        horizontal: 24, vertical: 12),
                  ),
                  child: const Text(
                      'Select', style: TextStyle(color: Colors.blue)),
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
                    (index) =>
                    ListTile(
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