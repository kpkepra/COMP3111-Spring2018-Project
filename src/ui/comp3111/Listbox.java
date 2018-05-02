package ui.comp3111;

import java.io.File;
import java.util.ArrayList;

import core.comp3111.CSVReader;
import core.comp3111.DataTable;
import core.comp3111.DataTableTransformer;
import core.comp3111.Transform;
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
	private static ArrayList<DataTable> tables = new ArrayList<DataTable>();
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
		CSVReader csv = new CSVReader(file.getName());
		csv.readALL(0);
		csv.readField();
		DataTable table = DataTableTransformer.transform(csv);
		tables.add(table);
		
	}
	
	public static void addDataset(DataTable table) {
		filenames.add(table.toString());
		tables.add(table);
	}
	
	/* addCorgi
	 * Adds one file extension to the list
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
	
	public static ArrayList<DataTable> getTables() {
		return tables;
	}
	
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
