package main;

import elements.Log;

import static main.Config.SERVER_SIZE;

public class Globals {
    public static boolean IS_SERVER_STARTED=false;

    public static boolean IS_GAME_STARTED=false;
    public static int TURN=0;
    public static String LAST_MOVE="";

    public static Log LOG=new Log();

    public static Player[] players=new Player[SERVER_SIZE];
}
