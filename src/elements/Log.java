package elements;


import javafx.scene.control.TextArea;

public class Log extends TextArea {
    public Log(){
        setEditable(false);

    }
    public void add(String s){
        appendText(s+"\n");
    }
}
