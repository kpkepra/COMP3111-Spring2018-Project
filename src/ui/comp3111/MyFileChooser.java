package ui.comp3111;
import java.io.File;
import java.io.IOException;

import core.comp3111.CSVReader;
import core.comp3111.MyFileExtenstion;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
* modified from java official example for file chooser
* https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
* */

public class MyFileChooser extends Application {
    private Button openButton;

    public Button getOpenButton() {
        return openButton;
    }
    @Override
    public void start(final Stage stage) {

        stage.setTitle("Choose a File");
        FileChooser fileChooser = new FileChooser();
        openButton = new Button("Open From File");
        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            openFile(file);
                            System.out.println(file.getName());
                        }
                    }
                });

        //UNNECESSARY LATER
        GridPane inputGridPane = new GridPane();

        GridPane.setConstraints(openButton, 0, 0);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(openButton);

        Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));

        stage.setScene(new Scene(rootGroup));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private void openFile(File file) {

            String extension;
            String fileName = file.getName();
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
                if (extension.equals("csv")) {
                    System.out.println("-----"+fileName);
                    CSVReader readerCSV = new CSVReader(fileName);
                    OpenCSV.openCSV(readerCSV);
                } else if (extension.equals("corgi")) {
                    MyFileExtenstion mf = new MyFileExtenstion();
                    try {
                        mf.loadCorgi(fileName);
                    }catch(IOException ioe){
                        System.out.println("IO Exception in FileExtension");
                    }
                    catch(ClassNotFoundException efe) {
                        System.out.println("Corgi, chart or dataTable class not found! ");
                    }
                } else {
                    System.out.println("unknown file type!!!!");
                }
            }
    }
}