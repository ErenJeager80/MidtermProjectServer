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

        Button submit = new Button("Submit");
        setConstraints(submit, 1, 0);
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

            PrepareBoard board = new PrepareBoard();
            Stage stage = new Stage();
            stage.setTitle("Game");
            stage.setScene(new Scene(board.build()));
            stage.show();
            ((Node) (e.getSource())).getScene().getWindow().hide();
        });
    }

}
