package ui.comp3111;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import core.comp3111.CSVReader;
import core.comp3111.Chart;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
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
    
    /**
     * Returns open button.
     * @return open button.
     */
    public Button getOpenButton() {
        return openButton;
    }
    
	/**
	 * Pane function. Generates and returns import and export buttons.
	 * 
	 * @return Pane object that shows the import and export buttons
	 */
    public static Pane pane() {
    	// FileChooser
    	fileChooser = new FileChooser();
    	openButton = new Button("Open File");
    	saveButton = new Button("Save File");
    	
    	initHandlers();
    	
    	// GridPane
    	inputGridPane = new GridPane();
    	GridPane.setConstraints(openButton, 0, 0);
    	inputGridPane.setHgap(6);
    	inputGridPane.setVgap(6);
    	inputGridPane.add(openButton, 0, 0);
    	inputGridPane.add(saveButton, 0, 1);
    	
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

    /**
     * Initialize all of the EventHandler functions when user's actions are made in this class.
     */
    static void initHandlers() {
        openButton.setOnAction(
        		new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            try {
								openFile(file);
							} catch (DataTableException e1) {
								e1.printStackTrace();
							}
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
    
    /**
     * Opens the file in either csv or corgi format.
     * 
     * @param file
     * 				- File object to be loaded into the application.
     * 
     * @throws DataTableException
     */
    static void openFile(File file) throws DataTableException {

            String extension;
            String filePath = file.getAbsolutePath();
            int i = filePath.lastIndexOf('.');
            if (i > 0) {
                extension = filePath.substring(i + 1);
                if (extension.equals("csv")) {
                    System.out.println("-----"+filePath);
            		CSVReader csv = new CSVReader(filePath);
            		OpenCSV.openCSV(csv);
                } else if (extension.equals("corgi")) {
                    MyFileExtenstion mf = new MyFileExtenstion();
                    try {
                        CorgiObj corgi = mf.loadCorgi(filePath);
                        Listbox.addCorgi(corgi);
                    }catch(IOException ioe){
                    	ioe.printStackTrace();
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
    
    /**
     * Saves the file in CorgiObj format (.corgi extension)
     * @param file
     * 				- File object to be saved into corgi file.
     */
    static void saveFile(File file) {
    	String fileName = file.getName();
    	ArrayList<DataTable> dt = Listbox.getTables();
    	int idx = Listbox.getIndex();
    	
    	ArrayList<Chart> ct = new ArrayList<Chart>();
    	if (ChartType.getType() == "Animated Pie") {
    		ct.add(AnimatedScreen.getChart());
    	} else {
    		ct.add(LineScreen.getChart());
    	}
    	
    	CorgiObj corgi = new CorgiObj(dt, ct, idx);
    	System.out.println(corgi.getIndex());
    	MyFileExtenstion mf = new MyFileExtenstion();
    	try {
			mf.saveCorgi("resources/" + fileName, corgi);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	/**
	 * Updates the GUI Window to load the import/export feature with its newest state.
	 */
    static void refresh() {
    	MainScreen.impexp = MyFileChooser.pane();
	    MainScreen.impexp.setMinWidth(200);
	    MainScreen.impexp.setMaxWidth(200);
    }
}