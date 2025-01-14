import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:mobile/Model/AirPort.dart';
import 'package:mobile/Model/Flight.dart';
import 'package:mobile/Model/HotelBooking.dart';
import 'package:mobile/Views/Information.dart';
import 'package:mobile/Views/ListFlight.dart';
import 'package:mobile/APIs/AirPortAPI.dart';

class Home extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return HomePage();
  }
}

class HomePage extends State<Home> {
  var isChecked = false;
  final TextEditingController departureController = TextEditingController();
  final TextEditingController destinationController = TextEditingController();
  final TextEditingController departureDateController = TextEditingController();
  final TextEditingController returnDateController = TextEditingController();

  final TextEditingController locationController = TextEditingController();
  final TextEditingController checkInController = TextEditingController();
  final TextEditingController checkOutController = TextEditingController();
  final TextEditingController roomCountController = TextEditingController();
  final TextEditingController ticketCountController = TextEditingController();

  List<Airport> departureAirports = [];
  List<Airport> destinationAirports = [];

  void _fetchAirports(String query, bool isDeparture) async {
    if (query.isEmpty) {
      setState(() {
        if (isDeparture) {
          departureAirports = [];
        } else {
          destinationAirports = [];
        }
      });
      return;
    }

    try {
      List<Airport> airports = await AirPortAPI().searchAirPort(query);
      setState(() {
        if (isDeparture) {
          departureAirports = airports;
        } else {
          destinationAirports = airports;
        }
      });
    } catch (e) {
      print('Error fetching airports: $e');
    }
  }

  DateTime? selectedDepartureDate;
  DateTime? selectedReturnDate;
  DateTime? selectedCheckInDate;
  DateTime? selectedCheckOutDate;

  bool isRoundTrip = false;

  String selectedSeatClass = 'Economy'; // Default seat class
  final List<String> seatClasses = ['Economy', 'Business', 'First Class'];

  int _selectedIndex = 0;

  @override
  void initState() {
    super.initState();
  }

  _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  Future<void> _selectDepartureDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: selectedDepartureDate ?? DateTime.now(),
      firstDate: DateTime.now(),
      lastDate: DateTime.now().add(const Duration(days: 365)),
      builder: (context, child) {
        return Theme(
          data: Theme.of(context).copyWith(
            colorScheme: const ColorScheme.light(
              primary: Colors.deepPurple,
              onPrimary: Colors.white,
              onSurface: Colors.black,
            ),
          ),
          child: child!,
        );
      },
    );

    if (picked != null) {
      setState(() {
        selectedDepartureDate = picked;
        departureDateController.text = DateFormat('dd/MM/yyyy').format(picked);

        // Reset return date if it's before the new departure date
        if (selectedReturnDate != null &&
            selectedReturnDate!.isBefore(picked)) {
          selectedReturnDate = null;
          returnDateController.clear();
        }
      });
    }
  }

  Future<void> _selectReturnDate(BuildContext context) async {
    if (selectedDepartureDate == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Please select departure date first')),
      );
      return;
    }

    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: selectedReturnDate ??
          selectedDepartureDate!.add(const Duration(days: 1)),
      firstDate: selectedDepartureDate!,
      // This ensures return date is after departure date
      lastDate: DateTime.now().add(const Duration(days: 365)),
      builder: (context, child) {
        return Theme(
          data: Theme.of(context).copyWith(
            colorScheme: const ColorScheme.light(
              primary: Colors.deepPurple,
              onPrimary: Colors.white,
              onSurface: Colors.black,
            ),
          ),
          child: child!,
        );
      },
    );

    if (picked != null) {
      setState(() {
        selectedReturnDate = picked;
        returnDateController.text = DateFormat('dd/MM/yyyy').format(picked);
      });
    }
  }

  Future<void> _selectCheckInDate(BuildContext context) async {
    if (selectedReturnDate == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Please select flight dates first')),
      );
      return;
    }

    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: selectedCheckInDate ?? selectedDepartureDate!,
      firstDate: selectedDepartureDate!,
      // Start from departure date
      lastDate: selectedReturnDate!,
      // End at return date
      builder: (context, child) {
        return Theme(
          data: Theme.of(context).copyWith(
            colorScheme: const ColorScheme.light(
              primary: Colors.deepPurple,
              onPrimary: Colors.white,
              onSurface: Colors.black,
            ),
          ),
          child: child!,
        );
      },
    );

    if (picked != null) {
      setState(() {
        selectedCheckInDate = picked;
        checkInController.text = DateFormat('dd/MM/yyyy').format(picked);

        // Reset check-out date if it's before the new check-in date
        if (selectedCheckOutDate != null &&
            selectedCheckOutDate!.isBefore(picked)) {
          selectedCheckOutDate = null;
          checkOutController.clear();
        }
      });
    }
  }

  Future<void> _selectCheckOutDate(BuildContext context) async {
    if (selectedCheckInDate == null) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Please select check-in date first')),
      );
      return;
    }

    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: selectedCheckOutDate ??
          selectedCheckInDate!.add(const Duration(days: 1)),
      firstDate: selectedCheckInDate!,
      // Start from check-in date
      lastDate: selectedReturnDate!,
      // End at return flight date
      builder: (context, child) {
        return Theme(
          data: Theme.of(context).copyWith(
            colorScheme: const ColorScheme.light(
              primary: Colors.deepPurple,
              onPrimary: Colors.white,
              onSurface: Colors.black,
            ),
          ),
          child: child!,
        );
      },
    );

    if (picked != null) {
      setState(() {
        selectedCheckOutDate = picked;
        checkOutController.text = DateFormat('dd/MM/yyyy').format(picked);
      });
    }
  }

  Future<void> handleSearch() async {
    if (departureController.text.isEmpty ||
        destinationController.text.isEmpty ||
        departureDateController.text.isEmpty ||
        returnDateController.text.isEmpty ||
        ticketCountController.text.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Please fill in all fields')),
      );
      return;
    }
    try {
      HotelBooking? hotelBooking;
      if (isChecked) {
        if (roomCountController.text.isEmpty) {
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text('Please enter number of rooms')),
          );
          return;
        }
        hotelBooking = HotelBooking(
          location: locationController.text,
          checkInDate: checkInController.text,
          checkOutDate: checkOutController.text,
          roomsCount: int.parse(roomCountController.text),
        );
      }

      // Tạo booking request
      final request = Flight(
        from: departureController.text,
        to: destinationController.text,
        departureTime: departureDateController.text,
        arrivalTime: returnDateController.text,
        ticketCount: int.parse(ticketCountController.text),
        seatClass: selectedSeatClass,
      );

      // Gọi API
      // await flightAPI.bookFlight(request);
      // Xử lý sau khi gọi API thành công
      // Ví dụ: chuyển trang, hiển thị thông báo, etc.
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Search Success!')),
      );
      Navigator.push(
          context,
          MaterialPageRoute(
              builder: (context) => FlightPage(searchCriteria: request)));
    } catch (e) {
      // Xử lý lỗi
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('M&G Booking'),
        actions: [
          IconButton(
            icon: const Icon(Icons.notifications),
            onPressed: () {},
          ),
        ],
      ),
      body: IndexedStack(
        index: _selectedIndex,
        children: [
          _buildHomePage(),
          Information(),
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _selectedIndex,
        onTap: _onItemTapped,
        type: BottomNavigationBarType.fixed,
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Home',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.person),
            label: 'Account',
          ),
        ],
      ),
    );
  }

  Widget _buildHomePage() {
    return SingleChildScrollView(
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Search Flight Section
            Container(
              padding: const EdgeInsets.all(16),
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(12),
                boxShadow: [
                  BoxShadow(
                    color: Colors.grey.withOpacity(0.2),
                    spreadRadius: 2,
                    blurRadius: 5,
                    offset: const Offset(0, 3),
                  ),
                ],
              ),
              child: Column(
                children: [
                  Align(
                    alignment: Alignment.center,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        ElevatedButton(
                            onPressed: () {
                              setState(() {
                                isRoundTrip = false;
                                returnDateController.clear();
                                selectedReturnDate = null;
                              });
                            },
                            child: Text("One Way")),
                        SizedBox(
                          width: 16,
                        ),
                        ElevatedButton(
                            onPressed: () {
                              setState(() {
                                isRoundTrip = true;
                              });
                            },
                            child: Text("Round Trip")),
                      ],
                    ),
                  ),
                  TextField(
                    controller: departureController,
                    decoration: InputDecoration(
                      labelText: 'From',
                      prefixIcon: const Icon(Icons.flight_takeoff),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                    ),
                    onChanged: (value) {
                      _fetchAirports(value, true);
                    },
                  ),
                  if (departureAirports.isNotEmpty)
                    ListView.builder(
                      shrinkWrap: true,
                      physics: const NeverScrollableScrollPhysics(),
                      itemCount: departureAirports.length,
                      itemBuilder: (context, index) {
                        return ListTile(
                          title: Text(departureAirports[index].name!),
                          onTap: () {
                            setState(() {
                              departureController.text = departureAirports[index].name!;
                              departureAirports = []; // Clear suggestions
                            });
                          },
                        );
                      },
                    ),
                  const SizedBox(height: 16),
                  TextField(
                    controller: destinationController,
                    decoration: InputDecoration(
                      labelText: 'To',
                      prefixIcon: const Icon(Icons.flight_land),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                    ),
                    onChanged: (value) {
                      _fetchAirports(value, false);
                    },
                  ),
                  if (departureAirports.isNotEmpty)
                    ListView.builder(
                      shrinkWrap: true,
                      physics: const NeverScrollableScrollPhysics(),
                      itemCount: destinationAirports.length,
                      itemBuilder: (context, index) {
                        return ListTile(
                          title: Text(destinationAirports[index].name!),
                          onTap: () {
                            setState(() {
                              destinationController.text = destinationAirports[index].name!;
                              departureAirports = []; // Clear suggestions
                            });
                          },
                        );
                      },
                    ),
                  const SizedBox(height: 16),
                  Row(
                    children: [
                      Expanded(
                        child: TextField(
                          controller: departureDateController,
                          readOnly: true,
                          onTap: () => _selectDepartureDate(context),
                          decoration: InputDecoration(
                            labelText: 'Date to',
                            prefixIcon: const Icon(Icons.calendar_today),
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(8),
                            ),
                          ),
                        ),
                      ),
                      if (isRoundTrip) ...[
                        const SizedBox(width: 16),
                        Expanded(
                          child: TextField(
                            controller: returnDateController,
                            readOnly: true,
                            onTap: () => _selectReturnDate(context),
                            decoration: InputDecoration(
                              labelText: 'Date return',
                              prefixIcon: const Icon(Icons.calendar_today),
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(8),
                              ),
                            ),
                          ),
                        ),
                      ],
                    ],
                  ),
                  const SizedBox(height: 16),
                  Row(
                    children: [
                      Expanded(
                        child: TextField(
                          controller: ticketCountController,
                          keyboardType: TextInputType.number,
                          decoration: InputDecoration(
                            labelText: 'Number of Tickets',
                            prefixIcon: const Icon(Icons.airplane_ticket),
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(8),
                            ),
                          ),
                        ),
                      ),
                      const SizedBox(width: 16),
                      Expanded(
                        child: DropdownButtonFormField<String>(
                          value: selectedSeatClass,
                          decoration: InputDecoration(
                            labelText: 'Seat Class',
                            prefixIcon:
                                const Icon(Icons.airline_seat_recline_extra),
                            border: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(8),
                            ),
                          ),
                          items: seatClasses.map((String value) {
                            return DropdownMenuItem<String>(
                              value: value,
                              child: Text(value),
                            );
                          }).toList(),
                          onChanged: (String? newValue) {
                            setState(() {
                              selectedSeatClass = newValue!;
                            });
                          },
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 16),
                  Align(
                    alignment: Alignment.centerLeft,
                    child: Row(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        Checkbox(
                          value: isChecked,
                          onChanged: (bool? value) {
                            setState(() {
                              isChecked = value!;
                            });
                          },
                        ),
                        Text("Click here if you want to book more hotels"),
                      ],
                    ),
                  ),
                  // New hotel fields that appear when checkbox is checked
                  if (isChecked) ...[
                    const SizedBox(height: 16),
                    TextField(
                      controller: locationController,
                      decoration: InputDecoration(
                        labelText: 'Location',
                        prefixIcon: const Icon(Icons.location_on),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(8),
                        ),
                      ),
                    ),
                    const SizedBox(height: 16),
                    TextField(
                      controller: roomCountController,
                      keyboardType: TextInputType.number,
                      decoration: InputDecoration(
                        labelText: 'Number of Rooms',
                        prefixIcon: const Icon(Icons.hotel),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(8),
                        ),
                      ),
                    ),
                    const SizedBox(height: 16),
                    Row(
                      children: [
                        Expanded(
                          child: TextField(
                            controller: checkInController,
                            readOnly: true,
                            onTap: () => _selectCheckInDate(context),
                            decoration: InputDecoration(
                              labelText: 'Check-in Date',
                              prefixIcon: const Icon(Icons.hotel),
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(8),
                              ),
                            ),
                          ),
                        ),
                        const SizedBox(width: 16),
                        Expanded(
                          child: TextField(
                            controller: checkOutController,
                            readOnly: true,
                            onTap: () => _selectCheckOutDate(context),
                            decoration: InputDecoration(
                              labelText: 'Check-out Date',
                              prefixIcon: const Icon(Icons.hotel),
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(8),
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ],
                  const SizedBox(height: 16),
                  SizedBox(
                    width: double.infinity,
                    child: ElevatedButton(
                      onPressed: () {
                        handleSearch();
                      },
                      style: ElevatedButton.styleFrom(
                        padding: const EdgeInsets.symmetric(vertical: 16),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(8),
                        ),
                      ),
                      child: const Text(
                        'Search',
                        style: TextStyle(fontSize: 16),
                      ),
                    ),
                  ),
                ],
              ),
            ),

            const SizedBox(height: 24),

            // Popular Destinations
            const Text(
              'Popular Destinations',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 16),
            GridView.builder(
              shrinkWrap: true,
              physics: const NeverScrollableScrollPhysics(),
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 2,
                crossAxisSpacing: 16,
                mainAxisSpacing: 16,
                childAspectRatio: 1.5,
              ),
              itemCount: 4,
              itemBuilder: (context, index) {
                return Container(
                  decoration: BoxDecoration(
                    color: Colors.grey.withOpacity(0.1),
                    borderRadius: BorderRadius.circular(12),
                  ),
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
