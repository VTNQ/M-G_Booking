import 'package:flutter/material.dart';

class ChangeInformation extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => ChangeInformationState();
}
class ChangeInformationState extends State<ChangeInformation>{
    final _formKey = GlobalKey<FormState>();

    final TextEditingController _emailController = TextEditingController();
    final TextEditingController _usernameController = TextEditingController();
    final TextEditingController _phoneController = TextEditingController();

    String? selectedCountry;
    String? selectedCity;
    // Sample country list - you can expand this or fetch from an API
    final List<Map<String, String>> countries = [
      {'id': '1', 'name': 'Vietnam'},
      {'id': '2', 'name': 'United States'},
      {'id': '3', 'name': 'Japan'},
      {'id': '4', 'name': 'South Korea'},
      {'id': '5', 'name': 'Singapore'},
    ];
    final List<Map<String, String>> cities = [
      {'id': '1', 'name': 'Vietnam'},
      {'id': '2', 'name': 'United States'},
      {'id': '3', 'name': 'Japan'},
      {'id': '4', 'name': 'South Korea'},
      {'id': '5', 'name': 'Singapore'},
    ];

    @override
    void initState() {
      super.initState();
      // Initialize with current user data
      _loadUserData();
    }

    void _loadUserData() {
      // TODO: Load actual user data here
      _emailController.text = 'example@email.com';
      _usernameController.text = 'username';
      _phoneController.text = '0123456789';
      selectedCountry = '1'; // Default to first country
      selectedCity = '1'; // Default to first city
    }

    Future<void> _handleSubmit() async {
      if (_formKey.currentState!.validate()) {
        try {
          // Show loading indicator
          showDialog(
            context: context,
            barrierDismissible: false,
            builder: (BuildContext context) {
              return const Center(
                child: CircularProgressIndicator(),
              );
            },
          );

          // TODO: Implement your API call here
          // Simulating API call with delay
          await Future.delayed(const Duration(seconds: 2));

          // Pop loading dialog
          Navigator.pop(context);

          // Show success message
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(
              content: Text('Information updated successfully'),
              backgroundColor: Colors.green,
            ),
          );
        } catch (e) {
          // Pop loading dialog
          Navigator.pop(context);

          // Show error message
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(
              content: Text('Error updating information: $e'),
              backgroundColor: Colors.red,
            ),
          );
        }
      }
    }

    @override
    Widget build(BuildContext context) {
      return Scaffold(
        appBar: AppBar(
          title: const Text('Change Information'),
          leading: IconButton(
            icon: const Icon(Icons.arrow_back),
            onPressed: () => Navigator.of(context).pop(),
          ),
        ),
        body: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Form(
              key: _formKey,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text(
                    'Personal Information',
                    style: TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 20),

                  // Email Field
                  TextFormField(
                    controller: _emailController,
                    keyboardType: TextInputType.emailAddress,
                    decoration: InputDecoration(
                      labelText: 'Email',
                      prefixIcon: const Icon(Icons.email),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                      filled: true,
                      fillColor: Colors.grey[100],
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter your email';
                      }
                      if (!RegExp(r'^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$').hasMatch(value)) {
                        return 'Please enter a valid email';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(height: 16),

                  // Username Field
                  TextFormField(
                    controller: _usernameController,
                    decoration: InputDecoration(
                      labelText: 'Username',
                      prefixIcon: const Icon(Icons.person),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                      filled: true,
                      fillColor: Colors.grey[100],
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter your username';
                      }
                      if (value.length < 3) {
                        return 'Username must be at least 3 characters';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(height: 16),

                  // Phone Field
                  TextFormField(
                    controller: _phoneController,
                    keyboardType: TextInputType.phone,
                    decoration: InputDecoration(
                      labelText: 'Phone Number',
                      prefixIcon: const Icon(Icons.phone),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                      filled: true,
                      fillColor: Colors.grey[100],
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter your phone number';
                      }
                      if (!RegExp(r'^\d{10}$').hasMatch(value)) {
                        return 'Please enter a valid 10-digit phone number';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(height: 16),

                  // Country Dropdown
                  DropdownButtonFormField<String>(
                    value: selectedCountry,
                    decoration: InputDecoration(
                      labelText: 'Country',
                      prefixIcon: const Icon(Icons.public),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                      filled: true,
                      fillColor: Colors.grey[100],
                    ),
                    items: countries.map((country) {
                      return DropdownMenuItem(
                        value: country['id'],
                        child: Text(country['name']!),
                      );
                    }).toList(),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please select a country';
                      }
                      return null;
                    },
                    onChanged: (String? newValue) {
                      setState(() {
                        selectedCountry = newValue;
                      });
                    },
                  ),
                  const SizedBox(height: 24),
                  DropdownButtonFormField<String>(
                    value: selectedCity,
                    decoration: InputDecoration(
                      labelText: 'City',
                      prefixIcon: const Icon(Icons.location_city),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                      filled: true,
                      fillColor: Colors.grey[100],
                    ),
                    items: countries.map((country) {
                      return DropdownMenuItem(
                        value: country['id'],
                        child: Text(country['name']!),
                      );
                    }).toList(),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please select a City';
                      }
                      return null;
                    },
                    onChanged: (String? newValue) {
                      setState(() {
                        selectedCountry = newValue;
                      });
                    },
                  ),
                  const SizedBox(height: 24),
                  TextFormField(
                    decoration: InputDecoration(
                      labelText: 'Address',
                      prefixIcon: const Icon(Icons.location_on),
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                      filled: true,
                      fillColor: Colors.grey[100],
                    ),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter your address';
                      }
                      return null;
                    },
                  ),
                  const SizedBox(height: 24),
                  // Submit Button
                  SizedBox(
                    width: double.infinity,
                    height: 50,
                    child: ElevatedButton(
                      onPressed: _handleSubmit,
                      style: ElevatedButton.styleFrom(
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(8),
                        ),
                      ),
                      child: const Text(
                        'Save Changes',
                        style: TextStyle(fontSize: 16),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      );
    }

    @override
    void dispose() {
      _emailController.dispose();
      _usernameController.dispose();
      _phoneController.dispose();
      super.dispose();
    }
}