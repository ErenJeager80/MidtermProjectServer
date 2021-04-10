package main;

import elements.Log;

import java.util.ArrayList;
import java.util.List;

import static main.Config.SERVER_SIZE;

public class Globals {
    public static boolean IS_SERVER_STARTED=false;

    public static boolean IS_GAME_STARTED=false;
    public static boolean GAME_IS_ENDED =false;
    public static int TURN=0;
    public static String LAST_MOVE="";

    public static Log LOG=new Log();

    public static List<Player> players=new ArrayList<>();
    public static Player WINNER = null;
}
