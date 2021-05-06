package pages;


import elements.Error;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;




import static main.Config.*;

public class InitServer extends GridPane {
    public InitServer() {
        setPadding(new Insets(10, 10, 10, 10));
        setVgap(5);
        setHgap(5);

        TextField serverName = new TextField(SERVER_NAME);
        serverName.setPromptText("Server name");
        setConstraints(serverName, 0, 0);
        getChildren().add(serverName);

        TextField serverPort = new TextField(String.valueOf(SERVER_PORT));
        serverPort.setPromptText("Server port");
        setConstraints(serverPort, 0, 1);
        getChildren().add(serverPort);

        PasswordField  serverPass = new PasswordField ();
        serverPass.setPromptText("Server Password");
        setConstraints(serverPass, 0, 2);
        getChildren().add(serverPass);

        TextField playerNum = new TextField(String.valueOf(SERVER_SIZE));
        playerNum.setPromptText("Player Number");
        setConstraints(playerNum, 0, 3);
        getChildren().add(playerNum);

        TextField width = new TextField(String.valueOf(WIDTH));
        width.setPromptText("Player Number");
        setConstraints(width, 0, 4);
        getChildren().add(width);

        TextField height = new TextField(String.valueOf(HEIGHT));
        height.setPromptText("Player Number");
        setConstraints(height, 0, 5);
        getChildren().add(height);

        Button submit = new Button("Submit");
        submit.setPrefSize(150,20);
        setConstraints(submit, 0, 6);
        getChildren().add(submit);
        playerNum.textProperty().addListener(e->{
                try {
                     Integer.parseInt(playerNum.getText());
                    submit.setDisable(false);
                    playerNum.setStyle("-fx-text-inner-color: black;");
                }catch(Exception ex){
                    playerNum.setStyle("-fx-text-inner-color: red;");
                    submit.setDisable(true);
                }
        });
        width.textProperty().addListener(e->{
            try {
                Integer.parseInt(width.getText());
                submit.setDisable(false);
                width.setStyle("-fx-text-inner-color: black;");
            }catch(Exception ex){
                width.setStyle("-fx-text-inner-color: red;");
                submit.setDisable(true);
            }
        });
        height.textProperty().addListener(e->{
            try {
                Integer.parseInt(height.getText());
                submit.setDisable(false);
                height.setStyle("-fx-text-inner-color: black;");
            }catch(Exception ex){
                height.setStyle("-fx-text-inner-color: red;");
                submit.setDisable(true);
            }
        });
        serverPort.textProperty().addListener(e->{
            try {
                Integer.parseInt(serverPort.getText());
                submit.setDisable(false);
                serverPort.setStyle("-fx-text-inner-color: black;");
            }catch(Exception ex){
                serverPort.setStyle("-fx-text-inner-color: red;");
                submit.setDisable(true);
            }
        });

        submit.setOnMouseClicked(e -> {
            SERVER_NAME=serverName.getText();
            SERVER_PASSWORD=serverPass.getText();
            SERVER_SIZE=Integer.parseInt(playerNum.getText());
            SERVER_PORT=Integer.parseInt(serverPort.getText());
            WIDTH=Integer.parseInt(width.getText());
            HEIGHT=Integer.parseInt(height.getText());

            PrepareBoard board = new PrepareBoard();
            Stage stage = new Stage();
            stage.setTitle("Game");
            stage.setScene(new Scene(board.build()));
            stage.show();
            ((Node) (e.getSource())).getScene().getWindow().hide();
        });
    }

}
