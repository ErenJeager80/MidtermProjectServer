package elements;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class Loading extends Alert {
    public Loading(LoadingType type, String header) {
        super(AlertType.WARNING);
        Rectangle r = new Rectangle();
        Image map = new Image(type==LoadingType.SEARCH?"elements/assets/search.gif":"elements/assets/load.gif");
        r.setWidth(300);
        r.setHeight(300/map.getWidth()*map.getHeight());
        ImagePattern pattern = new ImagePattern(map);
        r.setFill(pattern);
        initStyle(StageStyle.UNDECORATED);
        setHeaderText(header);
        initModality(Modality.NONE);
        getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
        getDialogPane().setContent(r);
    }
}
