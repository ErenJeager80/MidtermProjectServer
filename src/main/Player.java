package main;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {
    public Socket socket;
    public int id;
    public int score;
    public List<Integer> limits=new ArrayList<Integer>();

    public Player(Socket socket){
        this.socket=socket;
    }
}
