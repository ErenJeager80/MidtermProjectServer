package main;

import pages.PrepareBoard;

import java.io.*;
import java.net.Socket;

import static main.Config.*;

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
                if(data.indexOf('-')!=-1)
                    LAST_MOVE=data;

                switch (data) {
                    case "elements" -> InitServer.sendObject(client, new PrepareBoard.SimpleBoard().elements);
                    case "colors" -> InitServer.sendObject(client, new PrepareBoard.SimpleBoard().pieceColor);
                    case "values"-> InitServer.sendObject(client, new PrepareBoard.SimpleBoard().values);
                    case "id"-> InitServer.sendObject(client, new PrepareBoard.SimpleBoard().id);
                    case "myId"-> InitServer.send(client, String.valueOf(id));
                    case "turn"-> InitServer.send(client, String.valueOf(TURN%SERVER_SIZE));
                    case "width"->  InitServer.send(client,String.valueOf( WIDTH));
                    case "lastMove"->  InitServer.send(client,LAST_MOVE);
                    case "height"-> InitServer.send(client,String.valueOf( HEIGHT));
                    case "moved"-> {
                        TURN++;
                        System.out.println(TURN);
                    }

                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
