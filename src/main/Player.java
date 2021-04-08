package main;

import java.net.Socket;

public class Player {
    public Socket socket;
    private String name;
    public int id;

    public boolean isConnected(){
        return socket.isConnected();
    }
    public Player(Socket socket){
        this.socket=socket;
    }
}
