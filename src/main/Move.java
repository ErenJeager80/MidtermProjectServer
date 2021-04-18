package main;

import elements.*;
import pages.PrepareBoard;

import static main.Config.SERVER_SIZE;
import static main.Globals.*;

public class Move {
    public static void set(Tile[][] board, int x1, int y1, int x2, int y2) {
        if(board[x1][y1].hasElement() && board[x1][y1].getElement() instanceof Piece) {
            for (int i = x1, j = y1; (!(x2 > x1 || y2 > y1) || (i <= x2 && j <= y2)) && (!(x2 < x1 || y2 < y1) || i >= x2 && j >= y2); i += Integer.signum(x2 - x1), j += Integer.signum(y2 - y1)) {
                if (board[i][j].hasElement() && board[i][j].getElement().isVisible()) {
                    if (board[i][j].getElement() instanceof Star) {
                        players.get(((Piece)board[x1][y1].getElement()).getPieceId()).setScore(players.get(((Piece)board[x1][y1].getElement()).getPieceId()).getScore() + 1);
                        board[i][j].getElement().setVisible(false);
                    }
                    if (board[i][j].getElement() instanceof Slow) {
                        players.get((((Piece)board[x1][y1].getElement()).getPieceId() + 1) % SERVER_SIZE).getLimits().add(((Slow)board[i][j].getElement()).getValue());
                        board[i][j].getElement().setVisible(false);
                    }
                }
            }
            if(IS_GAME_STARTED)
                checkWin();

            board[x1][y1].getElement().move(x2, y2);
            board[x2][y2].setElement(board[x1][y1].getElement());
            board[x1][y1].setElement(null);
        }
    }

    private static void checkWin() {
        int count = 0;
        for(var t: PrepareBoard.getBoard())
            for(var tile :t)
                if(tile.hasElement() && tile.getElement() instanceof Star &&tile.getElement().isVisible())
                    count++;
        if(count==0){
            int score=-1;
            for(var p : players)
                if(p.getScore() >score){
                    score= p.getScore();
                    WINNER=p;
                }
            LOG.add("Player "+ (WINNER.getId() +1) +" won!!!"+" Score:"+ WINNER.getScore());

        }

    }

}
