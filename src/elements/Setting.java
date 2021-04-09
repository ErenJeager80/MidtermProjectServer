package elements;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.InitServer;
import main.WaitForPlayers;
import pages.PrepareBoard;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static main.Config.*;
import static main.Globals.*;

public class Setting extends GridPane {
    public Setting() {
        setPadding(new Insets(10, 10, 10, 10));
        setVgap(5);
        setHgap(5);


        ColorPicker fcp = new ColorPicker(Color.valueOf(FIRST_COLOR));
        setConstraints(fcp, 0, 0);
        getChildren().add(fcp);
//Defining the Last Name text field
        ColorPicker scp = new ColorPicker(Color.valueOf(SECOND_COLOR));
        setConstraints(scp, 0, 1);
        getChildren().add(scp);
//Defining the Submit button
        Button submit = new Button("Submit");
        setConstraints(submit, 1, 0);
        getChildren().add(submit);

        //Defining the Clear button
        Button clear = new Button("Reset");
        setConstraints(clear, 1, 1);
        getChildren().add(clear);

        LOG.setPrefSize(100,HEIGHT*TILE_SIZE-80);

        setConstraints(LOG, 0, 2);
        getChildren().add(LOG);


        submit.setOnMouseClicked(e -> {
            if(!IS_SERVER_STARTED) {
                IS_SERVER_STARTED=true;
                InitServer myServer = new InitServer();
                WaitForPlayers wfp = new WaitForPlayers();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
                ExecutorService executorService = Executors.newFixedThreadPool(2);
                executorService.execute(myServer);
                executorService.execute(wfp);
                Loading l = new Loading(LoadingType.LOAD, "Wait for players");
                wfp.setOnRunning(__ -> l.show());
                wfp.setOnSucceeded(__ -> l.close());
                submit.setDisable(true);
                clear.setDisable(true);
            }
        });

        clear.setOnMouseClicked(e -> {
            PrepareBoard pb = new PrepareBoard();
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(pb.build()));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node) (e.getSource())).getScene().getWindow().hide();
        });
        fcp.setOnAction(e -> {
            Color c = fcp.getValue();
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    if ((x + y) % 2 == 0)
                        PrepareBoard.board[x][y].setFill(c);
                }
            }
        });
        scp.setOnAction(e -> {
            Color c = scp.getValue();
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    if ((x + y) % 2 == 1)
                        PrepareBoard.board[x][y].setFill(c);
                }
            }
        });
    }
}
