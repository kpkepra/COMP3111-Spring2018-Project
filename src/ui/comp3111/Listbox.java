package ui.comp3111;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
	 * Overloaded addDataset function. Accepts csv, loads all the DataTable items inside and add
	 * them into ArrayList containing DataTable items.
	 * 
	 * @param csv
	 * 				- a CSVReader object.
	 * 
	 * @param name
	 * 				- name of the dataset.
	 */
	public static void addDataset(CSVReader csv, String name) throws DataTableException {
		filenames.add(name);
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
		ArrayList<Chart> cts = corgi.getCharts();
		AtomicInteger i = new AtomicInteger(1);
		dts.forEach(dt -> {
			filenames.add(corgi.getName() + " - Dataset" + i.getAndIncrement());
			tables.add(dt);
		});
		
		//Charts
		if (cts.get(0) instanceof Pie || cts.get(0) instanceof Line) {
			LineScreen.setChart(cts.get(0), dts.get(corgi.getIndex()));
		} else {
			// Assign to animated chart
		}
		
		// Tables
		DataTableDisplay.setTable(corgi.getDts().get(corgi.getIndex()));
		
		MainScreen.tfDisplay = new TransformDisplay(new Transform(DataTableDisplay.getDT()));
		MainScreen.rightc.getChildren().remove(1);
		MainScreen.filterPane = MainScreen.tfDisplay.splitFilter();
		MainScreen.rightc.getChildren().add(1, MainScreen.filterPane);
	}
	
	/**
	 * Returns all the tables loaded in the list.
	 * 
	 * @return An ArrayList containing DataTable items loaded in the list.
	 */
	public static ArrayList<DataTable> getTables() { return tables; }
	
	/**
	 * Returns the index of current table
	 * 
	 * @return the index of current table
	 */
	public static int getIndex() { return list.getSelectionModel().getSelectedIndex(); }
	
	/**
	 * Initialize all of the EventHandler functions when user's actions are made in this class.
	 * 
	 */
	static void initHandlers() {
		list.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (list.getSelectionModel().getSelectedIndex() != -1) {
					DataTable table = tables.get(list.getSelectionModel().getSelectedIndex());
					DataTableDisplay.setTable(table);
					LineScreen.loadData(table);
					AnimatedScreen.loadData(table);
					if (ChartType.getType() == "Animated Pie") {
						AnimatedScreen.refresh();
					} else {
						LineScreen.refresh();
					}
					
					MainScreen.tfDisplay = new TransformDisplay(new Transform(DataTableDisplay.getDT()));
					MainScreen.rightc.getChildren().remove(1);
					MainScreen.filterPane = MainScreen.tfDisplay.splitFilter();
					MainScreen.rightc.getChildren().add(1, MainScreen.filterPane);
				}
			}
			
		});
	}
}
