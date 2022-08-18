

// responsible for sending commands to the server
import 'dart:io';
import 'dart:typed_data';

class Controller {

  static  Future<Socket> connect() async  {
    Socket socket = await Socket.connect('192.168.1.64', 8000);
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

}