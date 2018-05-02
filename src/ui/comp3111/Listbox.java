package ui.comp3111;

import java.io.File;
import java.util.ArrayList;

import core.comp3111.MyFileExtenstion.CorgiObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Listbox {
	private static Pane pane;
	
	// Datasets and Corgis to internal memory
	private static ObservableList<String> filenames = FXCollections.observableArrayList();
	private static ObservableList<String> corginames = FXCollections.observableArrayList();
	private static ArrayList<File> files = new ArrayList<File>();
	private static ArrayList<CorgiObj> corgis = new ArrayList<CorgiObj>();
	
	// List for Display
	private static ListView<String> list = new ListView<String>();

	
	public static Pane pane() {
		list.setItems(filenames);
		
		pane = new Pane();
		pane.getChildren().add(list);
		initHandlers();
		
		return pane;
	}
	
	/* addDataset
	 * Adds one dataset to the list
	 */
	public static void addDataset(File file) {
		filenames.add(file.getName());
		files.add(file);
	}
	
	/* addCorgi
	 * Adds one file extension to the list
	 */
	public static void addCorgi(CorgiObj corgi) {
		corginames.add(corgi.getName());
		corgis.add(corgi);
	}
	
	static void initHandlers() {
		list.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				int idx = list.getSelectionModel().getSelectedIndex();
				DataTableDisplay.setTable(files.get(idx));
			}
			
		});
	}
}
