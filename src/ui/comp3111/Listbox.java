package ui.comp3111;

import java.io.File;
import java.util.ArrayList;

import core.comp3111.MyFileExtenstion.CorgiObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class Listbox {
	private static Pane pane;
	private static ObservableList<String> filenames = FXCollections.observableArrayList();
	private static ObservableList<String> corginames = FXCollections.observableArrayList();
	private static ArrayList<File> files = new ArrayList<File>();
	private static ArrayList<CorgiObj> corgis = new ArrayList<CorgiObj>();

	
	public static Pane pane() {
		ListView<String> list = new ListView<String>();
		list.setItems(filenames);
		
		pane = new Pane();
		pane.getChildren().add(list);
		return pane;
	}
	
	/* addDataset
	 * Adds one dataset to the list
	 */
	public static void addDataset(File file) {
		filenames.add(file.getName());
		files.add(file);
		System.out.println(files.size());
	}
	
	/* addCorgi
	 * Adds one file extension to the list
	 */
	public static void addCorgi(CorgiObj corgi) {
		corginames.add(corgi.getName());
		corgis.add(corgi);
		System.out.println(corgis.size());
	}
}
