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

public class DataTableDisplay {
	  private static File file;
	  private static DataTable table = new DataTable();
	  private static CSVReader csv;
	
	  public static TableView displayTable() {
		  TableView<Integer> datasetTable = new TableView();
		  datasetTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		  if (table.getNumRow() > 0 && table.getNumCol() > 0) {
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
			
			              return new ReadOnlyFloatWrapper(((Number)data[rowIndex]).floatValue());
			          });
			          column.prefWidthProperty().bind(datasetTable.widthProperty().divide(table.getNumCol()));
			          datasetTable.getColumns().add(column);
			      }
			  }
		  }
		
		  return datasetTable;
	  }
	  
	  public static void setTable(File newFile) {
		  file = newFile;
		  csv = new CSVReader(file.getName());
		  csv.readALL(0);
		  csv.readField();
		  table = DataTableTransformer.transform(csv);
		  System.out.println(table.getNumCol());
	  }
}
