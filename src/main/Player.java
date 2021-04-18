package main;

import elements.Element;
import elements.Piece;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final Socket socket;
    private final int id;
    private Element piece;
    private int score;
    private List<Integer> limits=new ArrayList<>();

    public void setPiece(Element piece) {
        this.piece = piece;
    }

    public Player(Socket socket,int id){
        this.socket=socket;
        this.id=id;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getId() {
        return id;
    }

    public Element getPiece() {
        return piece;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Integer> getLimits() {
        return limits;
    }

    public void setLimits(List<Integer> limits) {
        this.limits = limits;
    }
}
