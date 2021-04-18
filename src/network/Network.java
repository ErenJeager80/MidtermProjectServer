package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Network {

    public static void send(Socket s, String data) throws IOException, InterruptedException {
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        out.writeUTF(data);
        out.flush();
    }

    public static void sendObject(Socket s, Object data) throws IOException, InterruptedException {
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        out.writeObject(data);
        out.flush();
    }

    public static String receive(Socket s) throws IOException {
        DataInputStream in = new DataInputStream(s.getInputStream());
        return in.readUTF();
    }
}
