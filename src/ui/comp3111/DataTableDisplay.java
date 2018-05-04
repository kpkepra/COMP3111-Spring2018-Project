package ui.comp3111;
import java.util.Objects;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataType;
import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * DataTableDisplay - A class to display DataTable in a JavaFX UI. This class stores the table to be
 * displayed (table), a pane which contains the interface (pane), a JavaFX table which has the table
 * inside (datasetTable).
 *
 * @author apsusanto
 *
 */
public class DataTableDisplay {
	private static DataTable table = new DataTable();
	private static VBox pane;
	private static TableView datasetTable;

	/**
	 * Construct - Create a DataTableDisplay object by giving the DataTable object which will be displayed in the FXnode.
	 *
	 * @param dt
	 *             - The DataTable object which will be displayed.
	 *
	 */
	public DataTableDisplay(DataTable dt) {
	  table = dt;
	}

	/**
	 * Gets the DataTable that is stored within the class.
	 *
	 * @return DataTable The table object
	 */
	public static DataTable getDT() {
	  return table;
	}

	/**
	 * Returns the Vertical Box which contains the table inside.
	 *
	 * @return VBox The FX node which is ready to be displayed.
	 */
	public static VBox displayTable() {
		pane = new VBox();
		datasetTable = new TableView();

		for (int i = 0; i < table.getNumRow(); i++) {
		  datasetTable.getItems().add(i);
		}

		for (String colName : table.getColNames()) {
			DataColumn dataColumn = table.getCol(colName);
			Object[] data = dataColumn.getData();

			if (Objects.equals(dataColumn.getTypeName(), DataType.TYPE_STRING)) {
				TableColumn<Integer, String> column = new TableColumn<>(colName);

				column.setCellValueFactory(cellData -> {
				  Integer rowIndex = cellData.getValue();

				  return new ReadOnlyStringWrapper((String) data[rowIndex]);

				});
				column.prefWidthProperty().bind(datasetTable.widthProperty().divide(table.getNumCol()));
				datasetTable.getColumns().add(column);
			}
			if (Objects.equals(dataColumn.getTypeName(), DataType.TYPE_NUMBER)) {
				TableColumn<Integer, Number> column = new TableColumn<>(colName);

				column.setCellValueFactory(cellData -> {
				  Integer rowIndex = cellData.getValue();

				  return new ReadOnlyFloatWrapper(Float.valueOf(data[rowIndex].toString()));
				});
				column.prefWidthProperty().bind(datasetTable.widthProperty().divide(table.getNumCol()));
				datasetTable.getColumns().add(column);
			}
		}

	  pane.getChildren().addAll(datasetTable);
	  return pane;
	}
	
	/**
	 * Updates the GUI Window to load the DataTable display with its newest state.
	 */
	public static void refresh() {
		MainScreen.tablec = displayTable();
		MainScreen.tablec.setMinWidth(375);
		MainScreen.tablec.setMaxWidth(375);
		MainScreen.tablec.setMinHeight(200);
		MainScreen.tablec.setMaxHeight(200);
		if (MainScreen.centerc.getChildren().size() == 0) {
			MainScreen.centerc.getChildren().add(0, MainScreen.tablec);
		} else {
			MainScreen.centerc.getChildren().set(0, MainScreen.tablec);
		}
	}
	
	/**
	 * Change the displayed table.
	 *
	 * @param newTable
	 * 			   - the new DataTable to be displayed in the JavaFX UI.
	 */
	public static void setTable(DataTable newTable) {
		table = newTable;
	}
}
