package elements;


import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class Log extends TextArea {
    public Log(){
        setEditable(false);

    }
    public void add(String s){
        Platform.runLater(()->appendText(s+"\n"));
    }
}
