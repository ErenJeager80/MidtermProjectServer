package network;

import elements.ElementType;
import elements.Piece;
import main.Player;
import pages.PrepareBoard;

import java.io.IOException;
import java.net.Socket;

import static main.Config.*;
import static main.Globals.*;
import static network.Network.*;

public class InitialResponse implements Runnable{
    private final Socket client;

    public InitialResponse(Socket s) throws IOException, InterruptedException {
        client=s;
        send(client,"hello!");
    }
    @Override
    public void run() {
        try {
            WHILE:
            while(true) {
                String data =receive(client);
                System.out.println(data);
                switch (data) {
                    case "name" -> send(client, SERVER_NAME);
                    case "password" -> send(client, SERVER_PASSWORD);
                    case "player" -> send(client, String.valueOf(JOINED_PLAYERS));
                    case "size" -> send(client, String.valueOf(SERVER_SIZE));
                    case "hello!" ->{
                        players.add((JOINED_PLAYERS),new Player(client,JOINED_PLAYERS));
                        for(var t: PrepareBoard.getBoard())
                            for(var tile :t)
                                if(tile.getElement() instanceof Piece &&((Piece)tile.getElement()).getPieceId() ==JOINED_PLAYERS)
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
