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
    Player player;
    public Response(Player p) throws IOException {
        client=p.socket;
        id=p.id;
        player=p;
    }
    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            while(true) {
                String data =in.readUTF();

                if(data.indexOf('-')!=-1) {
                    LAST_MOVE = data;
                    Platform.runLater(() -> {
                        var x1 = Integer.parseInt(LAST_MOVE.substring(0, LAST_MOVE.indexOf("-")).substring(0, LAST_MOVE.indexOf("|")));
                        var y1 = Integer.parseInt(LAST_MOVE.substring(0, LAST_MOVE.indexOf("-")).substring(LAST_MOVE.indexOf("|") + 1));
                        var x2 = Integer.parseInt(LAST_MOVE.substring(LAST_MOVE.indexOf("-") + 1).substring(0, LAST_MOVE.indexOf("|")));
                        var y2 = Integer.parseInt(LAST_MOVE.substring(LAST_MOVE.indexOf("-") + 1).substring(LAST_MOVE.indexOf("|") + 1));
                        Move.set(PrepareBoard.board, x1, y1, x2, y2);
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
                    case "tileSize"->  InitServer.send(client,String.valueOf( TILE_SIZE));
                    case "lastMove"->  InitServer.send(client, LAST_MOVE);
                    case "fc"->  InitServer.send(client, FIRST_COLOR);
                    case "sc"->  InitServer.send(client, SECOND_COLOR);
                    case "height"-> InitServer.send(client,String.valueOf( HEIGHT));
                    case "limit"->{
                        if(player.limits.size()>0) {
                            InitServer.send(client, String.valueOf(player.limits.get(0)));
                            player.limits.remove(0);
                        }
                        else
                            InitServer.send(client,String.valueOf(-1));}
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
