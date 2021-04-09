package main;

import javafx.concurrent.Task;

import java.util.concurrent.TimeUnit;

import static main.Config.*;


public class WaitForPlayers extends Task<Long> {
    @Override
    protected Long call() throws Exception {
        while (true){
            TimeUnit.MILLISECONDS.sleep(100);
            if(JOINED_PLAYERS==SERVER_SIZE)
                break;
        }
        return null;
    }
}
