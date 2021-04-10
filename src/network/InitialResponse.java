package network;

import main.Player;
import pages.PrepareBoard;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import static main.Config.*;
import static main.Globals.*;

public class InitialResponse implements Runnable{
    Socket client;

    public InitialResponse(Socket s) throws IOException, InterruptedException {
        client=s;
        InitServer.send(client,"hello!");

    }
    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            WHILE:
            while(true) {
                String data =in.readUTF();
                System.out.println(data);
                switch (data) {
                    case "name" -> InitServer.send(client, SERVER_NAME);
                    case "password" -> InitServer.send(client, SERVER_PASSWORD);
                    case "player" -> InitServer.send(client, String.valueOf(JOINED_PLAYERS));
                    case "size" -> InitServer.send(client, String.valueOf(SERVER_SIZE));
                    case "hello!" ->{
                        players.add((JOINED_PLAYERS),new Player(client));
                        players.get(JOINED_PLAYERS).id=JOINED_PLAYERS;
                        for(var t: PrepareBoard.board)
                            for(var tile :t)
                                if(tile.hasElement() && tile.getElement().id==JOINED_PLAYERS)
                                    players.get(JOINED_PLAYERS).setPiece(tile.getElement());

                        LOG.setText("Player "+ JOINED_PLAYERS +" joined to server");
                        Thread t =new Thread(new Response(players.get(JOINED_PLAYERS)));
                        t.start();
                        JOINED_PLAYERS++;
                        break WHILE;
                    }
                    case "close" ->{
                        client.close();
                        break WHILE;
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
