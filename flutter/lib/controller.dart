

// responsible for sending commands to the server
import 'dart:io';
import 'dart:typed_data';

class Controller {

  late Socket socket;

  Future<Socket> connect() async  {
    socket = await Socket.connect('192.168.1.64', 8000);
    socket.listen((Uint8List data) {
      final serverResponse = String.fromCharCodes(data);
        print("Server: " + serverResponse);
      },
      onError: (error) {
        print(error);
        socket.destroy();
      },
      onDone: () {
        print('Server left.');
        socket.destroy();
      },
    );
    return socket;
  }

  Future<void> sendMessage(String message) async {
    socket.write(message);
    await Future.delayed(Duration(seconds: 2));
  }

}