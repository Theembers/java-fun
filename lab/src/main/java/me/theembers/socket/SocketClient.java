package me.theembers.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) {
        try {

            Socket socket = new Socket("127.0.0.1", 8811);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream.writeUTF("客户端 发送测试！！");
            System.out.println(dataInputStream.readUTF());

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
