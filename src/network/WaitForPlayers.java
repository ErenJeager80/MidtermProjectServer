package network;

import javafx.concurrent.Task;

import java.util.concurrent.TimeUnit;

import static main.Config.*;


public class WaitForPlayers extends Task<Long> {
    @Override
    protected Long call() throws Exception {
        while (JOINED_PLAYERS!=SERVER_SIZE){
            TimeUnit.MILLISECONDS.sleep(100);
        }
        return null;
    }
}
