package ui.comp3111;

import core.comp3111.*;
import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Objects;

public class DataTableDisplay {
    DataTable table;

    public DataTableDisplay(DataTable dt) {
        table = dt;
    }

    public TableView displayTable() {
        TableView<Integer> datasetTable = new TableView();
        datasetTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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

        return datasetTable;
    }

    //TODO debug for the display
    public static void main(String args[]){
        CSVReader csv = new CSVReader("csvTest1.csv");
        csv.readALL(0);
        csv.readField();
        DataTable dt = DataTableTransformer.transform(csv);
        DataTableDisplay dt_display = new DataTableDisplay(dt);
        TableView tv = dt_display.displayTable();
    }
}
