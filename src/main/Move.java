package main;

import elements.ElementType;
import elements.Tile;
import pages.PrepareBoard;

import static main.Config.SERVER_SIZE;
import static main.Globals.*;

public class Move {
    public static void set(Tile[][] board, int x1, int y1, int x2, int y2) {
        if(board[x1][y1].hasElement() && board[x1][y1].getElement().getType()== ElementType.PIECE) {
            for (int i = x1, j = y1; (!(x2 > x1 || y2 > y1) || (i <= x2 && j <= y2)) && (!(x2 < x1 || y2 < y1) || i >= x2 && j >= y2); i += Integer.signum(x2 - x1), j += Integer.signum(y2 - y1)) {
                if (board[i][j].hasElement() && board[i][j].getElement().isVisible()) {
                    if (board[i][j].getElement().getType() == ElementType.STAR ) {
                        players.get(board[x1][y1].getElement().id).score++;
                        board[i][j].getElement().setVisible(false);
                    }
                    if (board[i][j].getElement().getType() == ElementType.SLOW) {
                        players.get((board[x1][y1].getElement().id+1)%SERVER_SIZE).limits.add(board[i][j].getElement().value);
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
        for(var t: PrepareBoard.board)
            for(var tile :t)
                if(tile.hasElement() && tile.getElement().getType() == ElementType.STAR &&tile.getElement().isVisible())
                    count++;
        if(count==0){
            int score=-1;
            for(var p : players)
                if(p.score>score){
                    score=p.score;
                    WINNER=p;
                }
            LOG.add("Player "+ (WINNER.id+1) +" won!!!"+" Score:"+WINNER.score);

        }

    }

}
