package main;

import elements.Piece;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {
    public Socket socket;
    public int id;
    Piece piece;
    public int score;
    public List<Integer> limits=new ArrayList<Integer>();

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Player(Socket socket){
        this.socket=socket;

    }
}
