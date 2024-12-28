import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mobile/APIs/FlightAPI.dart';
import 'package:mobile/Model/Flight.dart';

class FlightPage extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    return ListFlight();
  }
}

class ListFlight extends State<FlightPage>{
  late Future<List<Flight>> flights;
  var flightAPI = FlightAPI();

  @override
  void initState() {
    super.initState();
    flights = flightAPI.getFlights();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("List Flight"),
        actions: [
          IconButton(onPressed: (){}, icon: const Icon(Icons.flight))
        ],
      ),
      body: SingleChildScrollView(
        child: Padding(
            padding: EdgeInsets.only(bottom: 16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
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
                child: FutureBuilder(future: flights, builder: (BuildContext buildContext, AsyncSnapshot<List<Flight>> snapshot){
                  if(snapshot.hasData){
                    return ListView.builder(
                      shrinkWrap: true,
                      itemCount: snapshot.data!.length,
                      itemBuilder: (BuildContext context, int index){
                        // return Row(
                        //   children: [
                        //     Text(snapshot.data![index].to!),
                        //     Text(snapshot.data![index].from!),
                        //     Text(snapshot.data![index].departureTime!),
                        //     Text(snapshot.data![index].arrivalTime!),
                        //     Text(snapshot.data![index].price.toString()),
                        //   ],
                        // );
                        return Card(
                          child: ListTile(
                            title: Text(snapshot.data![index].to!),
                            subtitle: Text(snapshot.data![index].from!),
                            trailing: Text(snapshot.data![index].price.toString()),
                          ),
                        )
                      },
                    );
                  }else if(snapshot.hasError){
                    return Text("${snapshot.error}");
                  }
                  return const CircularProgressIndicator();
                }
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}