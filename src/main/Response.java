package main;

import javafx.application.Platform;
import pages.PrepareBoard;

import java.io.*;
import java.net.Socket;

import static main.Config.*;
import static main.Globals.*;

public class Response implements Runnable{
    Socket client;
    int id;
    public Response(Player p) throws IOException {
        client=p.socket;
        id=p.id;
    }
    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            while(true) {
                String data =in.readUTF();
               // System.out.println(data);
                if(data.indexOf('-')!=-1) {
                    LAST_MOVE = data;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            var x1 = Integer.parseInt(LAST_MOVE.substring(0, LAST_MOVE.indexOf("-")).substring(0, LAST_MOVE.indexOf("|")));
                            var y1 = Integer.parseInt(LAST_MOVE.substring(0, LAST_MOVE.indexOf("-")).substring(LAST_MOVE.indexOf("|") + 1));
                            var x2 = Integer.parseInt(LAST_MOVE.substring(LAST_MOVE.indexOf("-") + 1).substring(0, LAST_MOVE.indexOf("|")));
                            var y2 = Integer.parseInt(LAST_MOVE.substring(LAST_MOVE.indexOf("-") + 1).substring(LAST_MOVE.indexOf("|") + 1));

                            Move.set(PrepareBoard.board, x1, y1, x2, y2);
                        }
                    });

                }

                switch (data) {
                    case "elements" -> InitServer.sendObject(client, new PrepareBoard.SimpleBoard().elements);
                    case "colors" -> InitServer.sendObject(client, new PrepareBoard.SimpleBoard().pieceColor);
                    case "values"-> InitServer.sendObject(client, new PrepareBoard.SimpleBoard().values);
                    case "id"-> InitServer.sendObject(client, new PrepareBoard.SimpleBoard().id);
                    case "myId"-> InitServer.send(client, String.valueOf(id));
                    case "turn"-> InitServer.send(client, String.valueOf(TURN%SERVER_SIZE));
                    case "width"->  InitServer.send(client,String.valueOf( WIDTH));
                    case "lastMove"->  InitServer.send(client, LAST_MOVE);
                    case "height"-> InitServer.send(client,String.valueOf( HEIGHT));
                    case "moved"-> { 
                        LOG.add("Player "+id+" moved");
                        TURN++; }
                    case "start?"-> {
                        IS_GAME_STARTED=SERVER_SIZE==JOINED_PLAYERS;
                        InitServer.send(client, IS_GAME_STARTED?"start":"wait");}

                }
            }
        } catch (IOException | InterruptedException e) {
            LOG.add("Player "+ id+":"+e.getMessage());
        }

    }

}
