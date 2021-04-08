package elements;

import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.StageStyle;
import javafx.util.Pair;


import static main.Config.*;


public class Piece extends Element {
    Ellipse ellipse;

    public Piece(int x, int y) {
        super(x, y, ElementType.PIECE);


        Ellipse bg = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE * 0.03);

        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2 + TILE_SIZE * 0.07);

        ellipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        color=Color.color(Math.random(), Math.random(), Math.random()).toString();
        ellipse.setFill(Color.valueOf(color));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE * 0.03);

        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse);

        setOnDragDetected(e -> {
            Dragboard db = startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();
            cb.putString(this.x + "|" + this.y);
            db.setContent(cb);
            e.consume();
        });
        ColorPicker cp = new ColorPicker(Color.valueOf(color));
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.initStyle(StageStyle.UNDECORATED);
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);
        dialog.getDialogPane().lookupButton(loginButtonType).setVisible(false);
        dialog.getDialogPane().setContent(cp);
        setOnContextMenuRequested(e->dialog.show());
        cp.setOnAction(__->{
            setColor(cp.getValue());
            dialog.hide();
        });
    }

    public void setColor(Color color) {
        this.color = color.toString();
        ellipse.setFill(color);
    }
}
