package main;

import pages.PrepareBoard;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import static main.Config.*;

public class InitialResponse implements Runnable{
    public static Player[] players=new Player[SERVER_SIZE];
    Socket client;

    public InitialResponse(Socket s) throws IOException, InterruptedException {
        client=s;
        InitServer.send(client,"hello!");
    }
    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
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
                        players[JOINED_PLAYERS] = new Player(client);
                        Thread t =new Thread(new Response(players[JOINED_PLAYERS]));
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
