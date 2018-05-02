package ui.comp3111;

import core.comp3111.*;
import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.HashMap;
import java.util.Objects;

public class TransformDisplay extends Main {
    private Transform transform;

    private boolean save;

    public TransformDisplay(Transform tf) {
        transform = tf;
    }

    public GridPane splitDisplay() {
        transform.setPercentSplit(new float[2]);
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(4);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column1.setHalignment(HPos.CENTER);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.CENTER);
        column2.setPercentWidth(50);
        root.getColumnConstraints().addAll(column1, column2);

        root.add(new Label("Input percentage of split:"), 0, 0, 2, 1);
        root.add(new Label("Dataset 1"), 0, 1);
        root.add(new Label("Dataset 2"), 1, 1);

        TextField numberField1 = new TextField();
        numberField1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                if (!new_val.matches("[\\d,\\.]*")) {
                    numberField1.setText(new_val.replaceAll("[^\\d, ^\\.]", ""));
                }
            }
        });

        TextField numberField2 = new TextField();
        numberField2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                if (!new_val.matches("[\\d,\\.]*")) {
                    numberField2.setText(new_val.replaceAll("[^\\d, ^\\.]", ""));
                }
            }
        });

        root.add(numberField1, 0, 2);
        root.add(numberField2, 1, 2);

        TableView<Integer> datasetTable = new DataTableDisplay(transform.getDataTable()).displayTable();

        root.add(datasetTable, 0, 4, 2, 1);

        Button splitButton = new Button("Split");
        splitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    float[] newPercentSplit = new float[2];
                    newPercentSplit[0] = (Float.valueOf(numberField1.getText())).floatValue();
                    newPercentSplit[1] = (Float.valueOf(numberField2.getText())).floatValue();
                    transform.setPercentSplit(newPercentSplit);
                    DataTable[] newTables = transform.randomSplit();

                    stage = new Stage();
                    stage.setOnHiding(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            if (save) {
                                //TODO: Save Data Tables
                            }
                            else {
                                GridPane chooseReplace = new GridPane();
                                chooseReplace.setHgap(10);
                                chooseReplace.setVgap(10);
                                ColumnConstraints column1 = new ColumnConstraints();
                                column1.setHalignment(HPos.CENTER);
                                column1.setPercentWidth(50);
                                ColumnConstraints column2 = new ColumnConstraints();
                                column2.setHalignment(HPos.CENTER);
                                column2.setPercentWidth(50);
                                chooseReplace.getColumnConstraints().addAll(column1, column2);
                                chooseReplace.add(new Label("Which dataset to overwrite with?"), 0, 0, 2, 1);
                                chooseReplace.setStyle("-fx-font: 16 arial;");

                                Button button1 = new Button("Dataset 1");
                                button1.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent e) {
                                        //TODO: REPLACE WITH TABLE 1
                                        stage.hide();
                                    }
                                });

                                Button button2 = new Button("Dataset2");
                                button2.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent e) {
                                        //TODO: REPLACE WITH TABLE 2
                                        stage.hide();
                                    }
                                });

                                chooseReplace.add(button1, 0, 1);
                                chooseReplace.add(button2, 1, 1);
                                Scene scene = new Scene(chooseReplace);
                                stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                            }
                        }
                    });

                    root.getChildren().remove(datasetTable);

                    TableView<Integer> datasetTable = new TableView();
                    datasetTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                    root.add(datasetTable, 0, 4, 2, 1);

                    for (int i = 0; i < transform.getDataTable().getNumRow(); i++) {
                        datasetTable.getItems().add(i);
                    }

                    TableColumn dataset1 = new TableColumn("Dataset 1");
                    TableColumn dataset2 = new TableColumn("Dataset 2");

                    datasetTable.getColumns().addAll(dataset1, dataset2);

                    for (String colName : newTables[0].getColNames()) {
                        DataColumn dataColumn = newTables[0].getCol(colName);

                        if (Objects.equals(dataColumn.getTypeName(), DataType.TYPE_STRING)) {
                            TableColumn<Integer, String> column = new TableColumn<>(colName);
                            Object[] data = dataColumn.getData();

                            column.setCellValueFactory(cellData -> {
                                Integer rowIndex = cellData.getValue();

                                if (rowIndex < newTables[0].getNumRow()) return new ReadOnlyStringWrapper((String) data[rowIndex]);
                                else return null;
                            });
                            column.prefWidthProperty().bind(dataset1.widthProperty().divide(newTables[0].getNumCol()));
                            dataset1.getColumns().add(column);
                        }
                        if (Objects.equals(dataColumn.getTypeName(), DataType.TYPE_NUMBER)) {
                            TableColumn<Integer, Number> column = new TableColumn<>(colName);
                            Object[] data = dataColumn.getData();

                            column.setCellValueFactory(cellData -> {
                                Integer rowIndex = cellData.getValue();

                                if (rowIndex < newTables[0].getNumRow()) return new ReadOnlyFloatWrapper(((Number)data[rowIndex]).floatValue());
                                else return null;
                            });
                            column.prefWidthProperty().bind(dataset1.widthProperty().divide(newTables[0].getNumCol()));
                            dataset1.getColumns().add(column);
                        }
                    }

                    for (String colName : newTables[1].getColNames()) {
                        DataColumn dataColumn = newTables[1].getCol(colName);

                        if (Objects.equals(dataColumn.getTypeName(), DataType.TYPE_STRING)) {
                            TableColumn<Integer, String> column = new TableColumn<>(colName);
                            Object[] data = dataColumn.getData();

                            column.setCellValueFactory(cellData -> {
                                Integer rowIndex = cellData.getValue();

                                if (rowIndex < newTables[1].getNumRow()) return new ReadOnlyStringWrapper((String) data[rowIndex]);
                                else return null;
                            });
                            column.prefWidthProperty().bind(dataset2.widthProperty().divide(newTables[1].getNumCol()));
                            dataset2.getColumns().add(column);
                        }
                        if (Objects.equals(dataColumn.getTypeName(), DataType.TYPE_NUMBER)) {
                            TableColumn<Integer, Number> column = new TableColumn<>(colName);
                            Object[] data = dataColumn.getData();

                            column.setCellValueFactory(cellData -> {
                                Integer rowIndex = cellData.getValue();

                                if (rowIndex < newTables[1].getNumRow()) return new ReadOnlyFloatWrapper(((Number)data[rowIndex]).floatValue());
                                else return null;
                            });
                            column.prefWidthProperty().bind(dataset2.widthProperty().divide(newTables[1].getNumCol()));
                            dataset2.getColumns().add(column);
                        }
                    }

                    askSaveReplace();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        root.add(splitButton, 0, 3, 2, 1);

        return root;
    }

    /**
     * Ask the user whether to save the new dataset or replace old one instead
     */
    private void askSaveReplace() {
        GridPane saveReplace = new GridPane();
        saveReplace.setHgap(10);
        saveReplace.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.CENTER);
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.CENTER);
        column2.setPercentWidth(50);
        saveReplace.getColumnConstraints().addAll(column1, column2);
        saveReplace.add(new Label("What do you want to do with the filtered dataset?"), 0, 0, 2, 1);
        saveReplace.setStyle("-fx-font: 16 arial;");

        // Save Button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                save = true;
                stage.hide();
            }
        });

        // Replace Button
        Button replaceButton = new Button("Replace");
        replaceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                save = false;
                stage.hide();
            }
        });

        saveReplace.add(saveButton, 0, 1);
        saveReplace.add(replaceButton, 1, 1);
        Scene scene = new Scene(saveReplace);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Display UI for filtering data 
     */
    public GridPane filterDisplay() {
        GridPane selectFilter = new GridPane();
        selectFilter.setHgap(10);
        selectFilter.setVgap(4);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column1.setHalignment(HPos.CENTER);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.CENTER);
        column2.setPercentWidth(25);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHalignment(HPos.CENTER);
        column3.setPercentWidth(25);
        selectFilter.getColumnConstraints().addAll(column1, column2, column3);

        TableView datasetTable = new DataTableDisplay(transform.getDataTable()).displayTable();
        selectFilter.add(datasetTable, 0, 0, 3, 1);

        ComboBox columnCombo = new ComboBox();
        columnCombo = new ComboBox();
        for (String colName : transform.getDataTable().getColNames()) {
            String colType = transform.getDataTable().getCol(colName).getTypeName();
            if (Objects.equals(colType, DataType.TYPE_NUMBER)) columnCombo.getItems().add(colName);
        }

        columnCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                transform.setColumnFilter(new_val);
            }
        });

        selectFilter.add(new Label("Select column as filter base: "), 0, 1);
        selectFilter.add(columnCombo, 0, 2);

        String[] operators = {"<", "<=", ">", ">=", "==", "!="};

        ComboBox operatorCombo = new ComboBox();
        for (String operator : operators) {
            operatorCombo.getItems().add(operator);
        }

        operatorCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                transform.setOperatorFilter(new_val);
            }
        });

        selectFilter.add(new Label("Select operator to filter with: "), 1, 1);
        selectFilter.add(operatorCombo, 1, 2);

        TextField numberField = new TextField();

        numberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                if (!new_val.matches("\\d*")) {
                    numberField.setText(new_val.replaceAll("[^\\d]", ""));
                }
            }
        });

        selectFilter.add(new Label("Input number to check against: "), 1, 3);
        selectFilter.add(numberField, 1, 4);
        selectFilter.getStyleClass().add("filter-gridpane");
        
        Button filterButton = new Button("Filter");

        filterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    transform.setNumberFilter(numberField.getText());

                    selectFilter.getChildren().remove(datasetTable);

                    DataTable temp = transform.filterData();

                    TableView datasetTable = new DataTableDisplay(temp).displayTable();
                    selectFilter.add(datasetTable, 0, 0, 3, 1);

                    stage = new Stage();
                    stage.setOnHiding(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            if (save) {
                                //TODO: SAVE DATATABLE
                            }
                            else {
                                // TODO: REPLACE DATATABLE WITH NEW DATASET
                            }
                        }
                    });

                    askSaveReplace();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
       
        selectFilter.add(filterButton, 0, 5, 3, 1);

        return selectFilter;
    }
}
