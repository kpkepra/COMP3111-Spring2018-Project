package ui.comp3111;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OpenCSV {
	public void openCSV(CSVReader readerCSV){
        Stage stage = new Stage();
        Button btFillWithZero = new Button("Fill Empty Data With 0");
        btFillWithZero.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        readALL(0);
                        System.out.println("fill with 0");
                        stage.close();
                        System.out.println(data);
                    }
                });
        Button btFillWithMean =  new Button("Fill Empty Data With mean");
        btFillWithMean.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        readALL(1);
                        System.out.println("fill with mean");
                        stage.close();
                        System.out.println(data);
                    }
                });
        Button btFillWithMedian =  new Button("Fill Empty Data With median");
        btFillWithMedian.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        readALL(2);
                        System.out.println("fill with median");
                        stage.close();
                        System.out.println(data);
                    }
                });

        /*open a new window with 3 buttons to choose how to fill in missing data*/
        stage.setTitle("Choose to Fill in Missing Data");

        GridPane inputGridPane = new GridPane();

        GridPane.setConstraints(btFillWithZero, 0, 0);
        GridPane.setConstraints(btFillWithMean, 1, 0);
        GridPane.setConstraints(btFillWithMedian, 2, 0);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(btFillWithZero,btFillWithMean,btFillWithMedian);

        Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));

        stage.setScene(new Scene(rootGroup));
        stage.show();
    }
}
