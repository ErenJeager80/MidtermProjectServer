package main;

import elements.Element;
import elements.Piece;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {
    public Socket socket;
    public int id;
    public Element piece;
    public int score;
    public List<Integer> limits=new ArrayList<Integer>();

    public void setPiece(Element piece) {
        this.piece = piece;
    }

    public Player(Socket socket){
        this.socket=socket;

    }

}
