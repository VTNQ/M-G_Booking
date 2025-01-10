import 'package:flutter/material.dart';

class InformationUser extends StatefulWidget {
  const InformationUser({Key? key}) : super(key: key);

  @override
  State<InformationUser> createState() => _InformationState();
}

class _InformationState extends State<InformationUser> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Column(
          children: [
            Container(
              width: double.infinity,
              padding: const EdgeInsets.all(20),
              decoration: const BoxDecoration(
                color: Colors.deepPurple,
                borderRadius: BorderRadius.only(
                  bottomLeft: Radius.circular(30),
                  bottomRight: Radius.circular(30),
                ),
              ),
              child: Column(
                children: [
                  const CircleAvatar(
                    radius: 50,
                    backgroundColor: Colors.white,
                    child: Icon(Icons.account_circle, size: 80, color: Colors.deepPurple),
                  ),
                  const SizedBox(height: 15),
                  const Text(
                    "Votannamquoc",
                    style: TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                  const SizedBox(height: 5),
                  Text(
                    "Level: Bronze",
                    style: TextStyle(
                      color: Colors.white.withOpacity(0.8),
                    ),
                  ),
                ],
              ),
            ),
            const SizedBox(height: 20),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: Column(
                children: [
                  _buildInfoTile(
                    icon: Icons.list_alt,
                    title: "History Booking",
                    subtitle: "All your booking history",
                    onTap: () {
                      // Handle phone edit
                    },
                  ),
                  _buildInfoTile(
                    icon: Icons.info,
                    title: "Change Information",
                    subtitle: "Update your information",
                    onTap: () {
                      // Handle email edit
                    },
                  ),
                  _buildInfoTile(
                    icon: Icons.security,
                    title: "Change Password",
                    subtitle: "Update your password",
                    onTap: () {
                      // Handle password change
                    },
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildInfoTile({
    required IconData icon,
    required String title,
    required String subtitle,
    required VoidCallback onTap,
  }) {
    return Card(
      elevation: 2,
      margin: const EdgeInsets.only(bottom: 16),
      child: ListTile(
        leading: Container(
          padding: const EdgeInsets.all(8),
          decoration: BoxDecoration(
            color: Colors.deepPurple.withOpacity(0.1),
            borderRadius: BorderRadius.circular(8),
          ),
          child: Icon(icon, color: Colors.deepPurple),
        ),
        title: Text(
          title,
          style: const TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
        subtitle: Text(subtitle),
        trailing: const Icon(Icons.arrow_forward_ios, size: 16),
        onTap: onTap,
      ),
    );
  }
}