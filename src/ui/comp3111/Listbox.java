package ui.comp3111;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class Listbox {
	private static Pane pane;
	
	public static Pane pane() {
		ListView<String> list = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList(
				"Dataset1", "Dataset2", "Dataset3", "Dataset4");
		list.setItems(items);
		
		pane = new Pane();
		pane.getChildren().add(list);
		return pane;
	}
}
