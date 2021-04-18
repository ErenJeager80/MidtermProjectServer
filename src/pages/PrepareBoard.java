package pages;

import elements.*;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.Serializable;

import static main.Config.*;

public class PrepareBoard {

    private static Tile[][] board;
    private static Star[][] stars;
    private static Slow[][] slows;
    private static Wall[][] walls;
    private final Group tileGroup = new Group();
    private final Group elementGroup = new Group();


    public PrepareBoard() {
        board=new Tile[WIDTH][HEIGHT];
        stars=new Star[WIDTH][HEIGHT];
        slows=new Slow[WIDTH][HEIGHT];
        walls=new Wall[WIDTH][HEIGHT];
    }

    public static Tile[][] getBoard() {
        return board;
    }


    public static Star[][] getStars() {
        return stars;
    }


    public static Slow[][] getSlows() {
        return slows;
    }


    public static Wall[][] getWalls() {
        return walls;
    }


    public Pane build() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE + SETTING_WIDTH, HEIGHT * TILE_SIZE);

        Setting setting = new Setting();
        setting.setPrefWidth(SETTING_WIDTH);

        GridPane mainGrid = new GridPane();
        Pane boardPane = new Pane();


        boardPane.getChildren().addAll(tileGroup, elementGroup);

        GridPane.setConstraints(setting, 1, 0);
        GridPane.setConstraints(boardPane, 0, 0);
        mainGrid.getChildren().add(setting);
        mainGrid.getChildren().add(boardPane);

        root.getChildren().addAll(mainGrid);
        int pieceCount = 0;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                getBoard()[x][y] = tile;

                Wall wall = new Wall(x, y);
                Slow slow = new Slow(x, y);
                Star star = new Star(x, y);

                getStars()[x][y] = star;
                getSlows()[x][y] = slow;
                getWalls()[x][y] = wall;

                Piece piece = null;
                if (y == 0 && x < SERVER_SIZE)
                    piece = new Piece(x, y, pieceCount++);


                if (piece != null) {
                    tile.setElement(piece);
                    elementGroup.getChildren().add(piece);
                }
                elementGroup.getChildren().addAll(wall, star, slow);
                tileGroup.getChildren().add(tile);
            }
        }
        return root;
    }

    public static class SimpleBoard implements Serializable {
        private final ElementType[][] elements = new ElementType[WIDTH][HEIGHT];
        private final String[][] pieceColor = new String[WIDTH][HEIGHT];
        private final int[][] values = new int[WIDTH][HEIGHT];
        private final int[][] id = new int[WIDTH][HEIGHT];
        static ElementType getType(Element e){
            if(e instanceof Slow)
                return ElementType.SLOW;
            if(e instanceof Piece)
                return ElementType.PIECE;
            if(e instanceof Wall)
                return ElementType.WALL;
            if(e instanceof Star)
                return ElementType.STAR;
           return null;
        }
        public SimpleBoard() {

            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    if (board[x][y].hasElement()) {
                        elements[x][y] =getType(getBoard()[x][y].getElement());
                        if(board[x][y].getElement() instanceof Piece)
                        pieceColor[x][y] = ((Piece)getBoard()[x][y].getElement()).getColor();
                        if(board[x][y].getElement() instanceof Slow)
                        values[x][y] = ((Slow)getBoard()[x][y].getElement()).getValue();
                        if(board[x][y].getElement() instanceof Piece)
                        getId()[x][y] = ((Piece)getBoard()[x][y].getElement()).getPieceId();

                    } else
                        elements[x][y] = null;
                }
            }
        }

        public ElementType[][] getElements() {
            return elements;
        }

        public String[][] getPieceColor() {
            return pieceColor;
        }

        public int[][] getValues() {
            return values;
        }

        public int[][] getId() {
            return id;
        }
    }
}
