package ui.comp3111;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataType;
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

                    return new ReadOnlyFloatWrapper(((Number)(Float.valueOf((String)data[rowIndex]))).floatValue());
                });
                column.prefWidthProperty().bind(datasetTable.widthProperty().divide(table.getNumCol()));
                datasetTable.getColumns().add(column);
            }
        }

        return datasetTable;
    }
}
