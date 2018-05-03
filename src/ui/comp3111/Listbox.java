package ui.comp3111;

import java.io.File;
import java.util.ArrayList;

import core.comp3111.*;
import core.comp3111.MyFileExtenstion.CorgiObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Listbox - A left-pane listview that displays all of the loaded datasets, either from .corgi 
 * extension or .csv extension. Once the user-selected file is loaded, Listbox will load all 
 * datasets contained in the file. It will also send all the dataset information for export/save
 * feature into .corgi files. Selecting any item of the list will display the table for filtering 
 * and chart displays.
 * 
 * @author kp-kepra
 * 
 */
public class Listbox extends Main {
	private static Pane pane;
	private static ObservableList<String> filenames = FXCollections.observableArrayList();
	private static ObservableList<String> corginames = FXCollections.observableArrayList();
	private static ArrayList<DataTable> tables = new ArrayList<DataTable>();
	private static ArrayList<CorgiObj> corgis = new ArrayList<CorgiObj>();
	private static ListView<String> list = new ListView<String>();
	
	/**
	 * Pane function. Generates and returns a list pane for multiple datasets and charts display.
	 * 
	 * @return Pane object that shows all datasets and charts hosted.
	 */
	public static Pane pane() {
		list.setItems(filenames);
		
		pane = new Pane();
		pane.getChildren().add(list);
		initHandlers();
		
		return pane;
	}
	
	/**
	 * Overloaded addDataset function. Accepts file input, loads all the DataTable items inside and add
	 * them into ArrayList containing DataTable items.
	 * 
	 * @param file
	 * 				- a java.io.File item.
	 */
	public static void addDataset(File file)throws DataTableException {
		filenames.add(file.getName());
		CSVReader csv = new CSVReader("resources/" + file.getName());
		System.out.println(file.getName());
		csv.readALL(0);
		csv.readField();
		DataTable table = DataTableTransformer.transform(csv);

		tables.add(table);
		
	}
	
	/**
	 * Overloaded addDataset function. Accepts DataTable as input and adds it into ArrayList containing
	 * DataTable items.
	 * 
	 * @param table
	 * 				- a DataTable item.
	 */
	public static void addDataset(DataTable table) {
		filenames.add(table.toString());
		tables.add(table);
	}
	
	/**
	 * Adds a Corgi obj (.corgi file) and loads all of the DataTable and Chart items. The items are then
	 * added into the ArrayLists containing DataTable and CorgiObj items respectively.
	 * 
	 * @param corgi
	 * 				- a CorgiObj item.
	 * 
	 */
	public static void addCorgi(CorgiObj corgi) {
		corginames.add(corgi.getName());
		corgis.add(corgi);
		ArrayList<DataTable> dts = corgi.getDts();
		dts.forEach(dt -> {
			filenames.add(corgi.getName());
			tables.add(dt);
		});
	}
	
	/**
	 * Returns all the tables loaded in the list.
	 * 
	 * @return An ArrayList containing DataTable items loaded in the list.
	 */
	public static ArrayList<DataTable> getTables() { return tables; }
	
	/**
	 * Initialize all of the EventHandler functions when user's actions are made in this class.
	 * 
	 */
	static void initHandlers() {
		list.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				DataTable table = tables.get(list.getSelectionModel().getSelectedIndex());
				DataTableDisplay.setTable(table);
				
				MainScreen.tfDisplay = new TransformDisplay(new Transform(DataTableDisplay.getDT()));
				MainScreen.rightc.getChildren().remove(1);
				MainScreen.filterPane = MainScreen.tfDisplay.splitFilter();
				MainScreen.rightc.getChildren().add(1, MainScreen.filterPane);
			}
			
		});
	}
}
