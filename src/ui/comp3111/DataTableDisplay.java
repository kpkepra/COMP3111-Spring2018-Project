package ui.comp3111;
import java.util.Objects;

import core.comp3111.CSVReader;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataType;
import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class DataTableDisplay {
	  private static DataTable table = new DataTable();
	  private static CSVReader csv;
	  private static VBox pane;
	  private static TableView datasetTable;
	  private static TableColumn firstNameCol, lastNameCol, emailCol;
	  
	  public DataTableDisplay(DataTable dt) {
		  table = dt;
	  }
	  
	  public static DataTable getDT() {
		  return table;
	  }
	
	  public static VBox displayTable() {
		  pane = new VBox();
		  datasetTable = new TableView();
		  datasetTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

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
	  
	  public static void setTable(DataTable newTable) {
		  table = newTable;
		  MainScreen.centerc.getChildren().remove(MainScreen.tablec);
		  MainScreen.tablec = displayTable();
		  MainScreen.tablec.setMinWidth(500);
		  MainScreen.tablec.setMaxWidth(500);
		  MainScreen.tablec.setMinHeight(300);
		  MainScreen.tablec.setMaxHeight(300);
		  MainScreen.tablec.setStyle("-fx-background-color: orange");
		  MainScreen.centerc.getChildren().add(0, MainScreen.tablec);
	  }
}
