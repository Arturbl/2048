import 'dart:convert';
import 'dart:io';
import 'package:flutter/material.dart';
import 'package:remote/controller.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Demo',
        debugShowCheckedModeBanner: false,
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: const Home()
    );
  }
}

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {

  Future<void> sendMessage(String message) async {
    await Controller.connect().then((sock) {
      sock.add(utf8.encode(message));
      sock.close();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Container(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [

                TextButton(
                  onPressed: () {
                    sendMessage("up");
                  },
                  child: Container(
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(10),
                      color: Colors.green,
                      boxShadow: const [
                        BoxShadow(color: Colors.green, spreadRadius: 3),
                      ],
                    ),
                    height: 90,
                    width: 90,
                    child: const Icon(Icons.keyboard_arrow_up_outlined, size: 45, color: Colors.black,),
                  ),
                ),

                Padding(
                  padding: const EdgeInsets.only(top: 15, bottom: 15),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [

                      TextButton(
                        onPressed: () {
                          sendMessage("left");
                        },
                        child: Container(
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(10),
                            color: Colors.green,
                            boxShadow: const [
                              BoxShadow(color: Colors.green, spreadRadius: 3),
                            ],
                          ),
                          height: 90,
                          padding: const EdgeInsets.all(15),
                          width: 90,
                          child: const Icon(Icons.keyboard_arrow_left_outlined, size: 45, color: Colors.black),
                        ),
                      ),


                      TextButton(
                        onPressed: () {
                          sendMessage("right");
                        },
                        child: Container(
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(10),
                            color: Colors.green,
                            boxShadow: const [
                              BoxShadow(color: Colors.green, spreadRadius: 3),
                            ],
                          ),
                          height: 90,
                          padding: const EdgeInsets.all(15),
                          width: 90,
                          child: const Icon(Icons.keyboard_arrow_right_outlined, size: 45, color: Colors.black),
                        ),
                      )

                    ],
                  ),
                ),

                TextButton(
                  onPressed: (){
                    sendMessage("down");
                  },
                  child: Container(
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(10),
                      color: Colors.green,
                      boxShadow: const [
                        BoxShadow(color: Colors.green, spreadRadius: 3),
                      ],
                    ),
                    height: 90,
                    padding: const EdgeInsets.all(15),
                    width: 90,
                    child: const Icon(Icons.keyboard_arrow_down_outlined, size: 45, color: Colors.black),
                  ),
                )


              ],
            )
        )
    );
  }
}
