package ui.comp3111;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import core.comp3111.CSVReader;
import core.comp3111.Chart;
import core.comp3111.DataTable;
import core.comp3111.MyFileExtenstion;
import core.comp3111.MyFileExtenstion.CorgiObj;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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

public class MyFileChooser extends Main {
    private static Button openButton, saveButton;
    
    private static FileChooser fileChooser;
    private static Pane pane;
    private static GridPane inputGridPane;
    

    public Button getOpenButton() {
        return openButton;
    }
    
    public static Pane pane() {
    	// FileChooser
    	fileChooser = new FileChooser();
    	openButton = new Button("Open From File");
    	saveButton = new Button("Save to File");
    	
    	initHandlers();
    	
    	// GridPane
    	inputGridPane = new GridPane();
    	GridPane.setConstraints(openButton, 0, 0);
    	inputGridPane.setHgap(6);
    	inputGridPane.setVgap(6);
    	inputGridPane.getChildren().addAll(openButton, saveButton);
    	
    	// Pane
    	pane = new VBox(12);
    	pane.getChildren().addAll(inputGridPane);
    	pane.setPadding(new Insets(12, 12, 12, 12));
    	return pane;
    }
    @Override
    public void start(final Stage stage) {

        stage.setTitle("Choose a File");
        fileChooser = new FileChooser();
        
        openButton = new Button("Open From File");

    }

    static void initHandlers() {
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
        
        saveButton.setOnAction(
        		new EventHandler<ActionEvent>() {
        			@Override
        			public void handle(final ActionEvent e) {
        				File file = fileChooser.showSaveDialog(stage);
        				if (file != null) {
        					saveFile(file);
        				}
        			}
        		});
    }
    
    static void openFile(File file) {

            String extension;
            String fileName = file.getName();
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
                if (extension.equals("csv")) {
                    System.out.println("-----"+fileName);
                    CSVReader readerCSV = new CSVReader(fileName);
                    OpenCSV.openCSV(readerCSV);
                    Listbox.addDataset(file);
                } else if (extension.equals("corgi")) {
                    MyFileExtenstion mf = new MyFileExtenstion();
                    try {
                        CorgiObj corgi = mf.loadCorgi(fileName);
                        Listbox.addCorgi(corgi);
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
    
    static void saveFile(File file) {
    	String fileName = file.getName();
    	ArrayList<DataTable> dt = Listbox.getTables();
    	
    	ArrayList<Chart> ct = new ArrayList<Chart>();
    	CorgiObj corgi = new CorgiObj(dt, ct);
    	MyFileExtenstion mf = new MyFileExtenstion();
    	try {
			mf.saveCorgi(fileName, corgi);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}