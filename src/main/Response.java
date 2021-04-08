package main;

import elements.ElementType;
import pages.PrepareBoard;

import java.io.*;
import java.net.Socket;

import static main.Config.*;

public class Response implements Runnable{
    Socket client;
    public Response(Player p) throws IOException {
        client=p.socket;
    }
    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
            while(true) {
                String data =in.readUTF();
                switch (data) {
                    case "elements" -> InitServer.sendObject(client, new PrepareBoard.SimpleBoard().elemets);
                    case "colors" -> InitServer.sendObject(client, new PrepareBoard.SimpleBoard().pieceColor);
                    case "values"-> InitServer.sendObject(client, new PrepareBoard.SimpleBoard().values);
                    case "width"->  InitServer.send(client,String.valueOf( WIDTH));
                    case "height"-> InitServer.send(client,String.valueOf( HEIGHT));
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}
