package elements;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


import static main.Config.TILE_SIZE;

public class Slow extends Element {

    public void setValue(int value) {
        super.value = value;
    }

    public int getValue() {
        return value;
    }

    public Slow(int x, int y) {
        super(x, y, ElementType.SLOW);

        Rectangle slow = new Rectangle(0, 0, TILE_SIZE, TILE_SIZE);

        Image map = new Image("elements/assets/slow.png");
        ImagePattern pattern = new ImagePattern(map);
        slow.setFill(pattern);

        setVisible(false);

        getChildren().addAll(slow);
    }

}