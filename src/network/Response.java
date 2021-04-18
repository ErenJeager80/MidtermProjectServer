package network;

import javafx.application.Platform;
import main.Move;
import main.Player;
import pages.PrepareBoard;

import java.io.*;

import static main.Config.*;
import static main.Globals.*;
import static network.Network.*;

public class Response implements Runnable{
    private final Player player;
    
    public Response(Player p) throws IOException {
        player=p;
    }
    @Override
    public void run() {
        try {
            while(!GAME_IS_ENDED) {
                String data = receive(player.getSocket());
                if(data.contains("l:")){
                    int playerData = Integer.parseInt(data.substring(data.indexOf(":")+1));
                    var x=String.valueOf(players.get(playerData).getPiece().getX());
                    var y=String.valueOf(players.get(playerData).getPiece().getY());
                    send(player.getSocket(),x+"|"+y);
                }
                if(data.indexOf('-')!=-1) {
                    LAST_MOVE = data;
                    Platform.runLater(() -> {
                        var x1 = Integer.parseInt(LAST_MOVE.substring(0, LAST_MOVE.indexOf("-")).substring(0, LAST_MOVE.indexOf("|")));
                        var y1 = Integer.parseInt(LAST_MOVE.substring(0, LAST_MOVE.indexOf("-")).substring(LAST_MOVE.indexOf("|") + 1));
                        var x2 = Integer.parseInt(LAST_MOVE.substring(LAST_MOVE.indexOf("-") + 1).substring(0, LAST_MOVE.indexOf("|")));
                        var y2 = Integer.parseInt(LAST_MOVE.substring(LAST_MOVE.indexOf("-") + 1).substring(LAST_MOVE.indexOf("|") + 1));
                        Move.set(PrepareBoard.getBoard(), x1, y1, x2, y2);
                    });

                }

                switch (data) {
                    case "elements" -> sendObject(player.getSocket(), new PrepareBoard.SimpleBoard().getElements());
                    case "colors" -> sendObject(player.getSocket(), new PrepareBoard.SimpleBoard().getPieceColor());
                    case "values"-> sendObject(player.getSocket(), new PrepareBoard.SimpleBoard().getValues());
                    case "id"-> sendObject(player.getSocket(), new PrepareBoard.SimpleBoard().getId());
                    case "myId"-> send(player.getSocket(), String.valueOf(player.getId()));
                    case "turn"-> send(player.getSocket(), String.valueOf(TURN%SERVER_SIZE));
                    case "width"->  send(player.getSocket(),String.valueOf( WIDTH));
                    case "height"-> send(player.getSocket(),String.valueOf( HEIGHT));
                    case "tileSize"->  send(player.getSocket(),String.valueOf( TILE_SIZE));
                    case "lastMove"->  send(player.getSocket(), LAST_MOVE);
                    case "fc"->  send(player.getSocket(), FIRST_COLOR);
                    case "sc"->  send(player.getSocket(), SECOND_COLOR);
                    case "end?"->  {
                        if(WINNER==null)
                        send(player.getSocket(), "-1");
                        else {
                            send(player.getSocket(), String.valueOf(WINNER.getId()));
                          //  GAME_IS_ENDED =true;
                        }
                    }
                    case "limit"->{
                        if(player.getLimits().size()>0) {
                            send(player.getSocket(), String.valueOf(player.getLimits().get(0)));
                            player.getLimits().remove(0);
                        }
                        else
                            send(player.getSocket(),String.valueOf(-1));}
                    case "moved"-> {
                        LOG.add("Player "+ player.getId() +" moved");
                        TURN++; }
                    case "start?"-> {
                        IS_GAME_STARTED=SERVER_SIZE==JOINED_PLAYERS;
                        send(player.getSocket(), IS_GAME_STARTED?"start":"wait");}

                }
            }
        } catch (IOException | InterruptedException e) {
            LOG.add("Player "+ player.getId() +":"+e.getMessage());
        }
        try {
            player.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
