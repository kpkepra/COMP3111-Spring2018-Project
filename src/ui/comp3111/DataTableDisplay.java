package ui.comp3111;

import java.io.File;
import java.util.Objects;

import core.comp3111.CSVReader;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableTransformer;
import core.comp3111.DataType;
import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class DataTableDisplay {
	  private static File file;
	  private static DataTable table = new DataTable();
	  private static CSVReader csv;
	  private static Pane pane;
	  private static TableView datasetTable;
	  private static TableColumn firstNameCol, lastNameCol, emailCol;
	
	  public static Pane displayTable() {
		  pane = new Pane();
		  datasetTable = new TableView()<<<<<<< dev-kevin;
		  datasetTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		  table.printTable();
		  
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
	  
	  public static void setTable(File newFile) {
		  file = newFile;
		  csv = new CSVReader(file.getName());
		  csv.readALL(0);
		  csv.readField();
		  table = DataTableTransformer.transform(csv);
		  MainScreen.centerc.getChildren().remove(MainScreen.tablec);
		  MainScreen.tablec = displayTable();
		  MainScreen.tablec.setMinWidth(800);
		  MainScreen.tablec.setMaxWidth(800);
		  MainScreen.tablec.setMinHeight(300);
		  MainScreen.tablec.setMaxHeight(300);
		  MainScreen.tablec.setStyle("-fx-background-color: orange");
		  MainScreen.centerc.getChildren().add(0, MainScreen.tablec);
	  }
}
