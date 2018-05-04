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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Objects;

/**
 * TransformDisplay - A class which can be utilized to display any form of Transformation. It stores the
 * Transform object (transform), a boolean to indicate whether users would like to save or replace (save), String to
 * indicate the name of file to be saved (fileName), and the general GridPane to display either of the transformation.
 *
 * @author apsusanto
 *
 */
public class TransformDisplay extends Main {
    private Transform transform;

    private int save;

    private String fileName;

    GridPane splitFilter;

    /**
     * Construct - Create a TransformDisplay object by giving the Transform object which will be displayed in the FX node.
     *
     * @param tf
     *             - The Transform object which will be displayed.
     *
     */
    public TransformDisplay(Transform tf) {
        transform = tf;
        save = 0;
    }

    /**
     * Display interface for split transformation. JavaFX Main UI can directly call this function to and
     * put it on the scene to split any datatable.
     *
     * @return GridPane JavaFX node with filter transformation inside.
     */
    private GridPane splitDisplay() {
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

        Pane datasetTable = new DataTableDisplay(transform.getDataTable()).displayTable();

        root.add(datasetTable, 0, 4, 2, 1);

        Button splitButton = new Button("Split");
        splitButton.getStyleClass().add("splitfilter_btn");
        splitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (numberField1.getText() == null || numberField2.getText() == null) return;
                try {
                    float[] newPercentSplit = new float[2];
                    newPercentSplit[0] = (Float.valueOf(numberField1.getText())).floatValue();
                    newPercentSplit[1] = (Float.valueOf(numberField2.getText())).floatValue();
                    transform.setPercentSplit(newPercentSplit);
                    DataTable[] newTables = transform.randomSplit();

                    stage = new Stage();
                    stage.setOnHiding(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            if (save == 1) {
                                stage = new Stage();
                                askFileName();

                                stage.setOnHiding(new EventHandler<WindowEvent>() {
                                    public void handle(WindowEvent we) {
                                        try {
                                            if (fileName != null) {
                                                CSVWriter csvWriter = new CSVWriter(fileName + ".csv");
                                                ArrayList<String> array = new DataTableTransformer().reverseTransform(newTables[0]);
                                                csvWriter.writeArray(array, newTables[0].getNumCol());
                                                csvWriter.close();
                                                stage = new Stage();
                                                askFileName();
                                                stage.setOnHiding(new EventHandler<WindowEvent>() {
                                                    public void handle(WindowEvent we) {
                                                        try {
                                                            if (fileName != null) {
                                                                CSVWriter csvWriter = new CSVWriter(fileName + ".csv");
                                                                ArrayList<String> array = new DataTableTransformer().reverseTransform(newTables[1]);
                                                                csvWriter.writeArray(array, newTables[1].getNumCol());
                                                                csvWriter.close();
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                            else if (save == 2) {
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
                                        // ( ͡° ͜ʖ ͡°)
//                                    	newTables[0]
                                        stage.hide();
                                    }
                                });

                                Button button2 = new Button("Dataset2");
                                button2.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent e) {
                                        // ( ͡° ͜ʖ ͡°)
//                                    	newTables[1]
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

                    VBox newPane = new VBox();

                    TableView<Integer> dataset1 = new TableView();
                    dataset1.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                    TableView<Integer> dataset2 = new TableView();
                    dataset2.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

                    for (int i = 0; i < newTables[0].getNumRow(); i++) {
                        dataset1.getItems().add(i);
                    }
                    for (int i = 0; i < newTables[1].getNumRow(); i++) {
                        dataset2.getItems().add(i);
                    }

                    newPane.getChildren().addAll(dataset1, dataset2);
                    root.add(newPane, 0, 4, 4, 1);


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

                                if (rowIndex < newTables[0].getNumRow()) return new ReadOnlyFloatWrapper((float)(double)(Double)data[rowIndex]);
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

                                if (rowIndex < newTables[1].getNumRow()) return new ReadOnlyFloatWrapper((float)(double)(Double)data[rowIndex]);
                                else return null;
                            });
                            column.prefWidthProperty().bind(dataset2.widthProperty().divide(newTables[1].getNumCol()));
                            dataset2.getColumns().add(column);
                        }
                    }
                    dataset1.setMaxHeight(100);
//                    dataset1.prefHeightProperty().bind(newPane.heightProperty().divide(2));
//                    dataset1.setStyle("-fx-border-width: 0px;");
//                    dataset2.prefHeightProperty().bind(newPane.heightProperty().divide(2));

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
     * Display interface for filter transformation. JavaFX Main UI can directly call this function to and
     * put it on the scene to filter any datatable.
     *
     * @return GridPane JavaFX node with filter transformation inside.
     */
    private GridPane filterDisplay() {
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

        Pane datasetTable = new DataTableDisplay(transform.getDataTable()).displayTable();
        selectFilter.add(datasetTable, 0, 4, 3, 1);

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

        selectFilter.add(new Label("Input filter parameters:"), 0, 0, 3, 1);

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
                if (!new_val.matches("[\\d,\\.]*")) {
                    numberField.setText(new_val.replaceAll("[^\\d, ^\\.]", ""));
                }
            }
        });

        selectFilter.add(new Label("Input number to check against: "), 2, 1);
        selectFilter.add(numberField, 2, 2);
//        selectFilter.getStyleClass().add("filter-gridpane");

        Button filterButton = new Button("Filter");
        filterButton.getStyleClass().add("splitfilter_btn");
        filterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    transform.setNumberFilter(numberField.getText());

                    selectFilter.getChildren().remove(datasetTable);

                    DataTable newTable = transform.filterData();

                    Pane datasetTable = new DataTableDisplay(newTable).displayTable();

                    selectFilter.add(datasetTable, 0, 4, 3, 1);

                    stage = new Stage();
                    stage.setOnHiding(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            if (save == 1) {
                                stage = new Stage();
                                askFileName();

                                stage.setOnHiding(new EventHandler<WindowEvent>() {
                                    public void handle(WindowEvent we) {
                                        try {
                                            CSVWriter csvWriter = new CSVWriter(fileName + ".csv");
                                            ArrayList<String> array = new DataTableTransformer().reverseTransform(newTable);
                                            csvWriter.writeArray(array, newTable.getNumCol());
                                            csvWriter.close();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                            else if (save == 2) {
                            	Listbox.replaceDataset(newTable);
                            	DataTableDisplay.setTable(newTable);
                            	DataTableDisplay.refresh();
                            	if (ChartType.getType() == "Animated Pie") {
                            		AnimatedScreen.setChart(newTable);
                            		AnimatedScreen.refresh();
                            	} else {
                            		LineScreen.setChart(null, newTable);
                            		LineScreen.refresh();
                            	}
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

        selectFilter.add(filterButton, 0, 3, 3, 1);

        return selectFilter;
    }

    /**
     * Display interface for choosing transformation method. JavaFX Main UI can directly call this function to and
     * put it on the scene to filter any datatable.
     *
     * @return GridPane JavaFX node with option to choose transformation method and the transform window itself.
     */
    public GridPane splitFilter() {
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

        splitFilter = splitDisplay();

        ToggleGroup group = new ToggleGroup();

        RadioButton splitButton = new RadioButton("Split");
        splitButton.setToggleGroup(group);
        splitButton.setSelected(true);
        splitButton.setUserData("split");

        RadioButton filterButton = new RadioButton("Filter");
        filterButton.setToggleGroup(group);
        filterButton.setUserData("filter");

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    root.getChildren().remove(splitFilter);
                    String choice = new_toggle.getUserData().toString();
                    if (Objects.equals("split", choice)) splitFilter = splitDisplay();
                    else if (Objects.equals("filter", choice)) splitFilter = filterDisplay();
                    else splitFilter = new GridPane();
                    root.add(splitFilter, 0, 2, 2, 1);
                }
            }
        });

        root.add(new Label("Choose transformation method:"), 0, 0, 2, 1);
        root.add(splitButton, 0, 1);
        root.add(filterButton, 1, 1);
        root.add(splitFilter, 0, 2, 2, 1);

        return root;
    }

    /**
     * A function which will display an interface to ask the user whether to save the new dataset
     * or replace old one instead.
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

        // Save Button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                save = 1;
                stage.hide();
            }
        });

        // Replace Button
        Button replaceButton = new Button("Replace");
        replaceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                save = 2;
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
     * A function which will display an interface to ask the filename to save the dataset to.
     */
    public void askFileName() {
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.CENTER);
        column1.setPercentWidth(100);
        root.getColumnConstraints().addAll(column1);
        root.add(new Label("What is the file name you would like to save as?"), 0, 0);

        TextField nameField = new TextField();
        root.add(nameField, 0, 1);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (nameField.getText() != null) {
                    fileName = nameField.getText();
                    stage.hide();
                }
            }
        });
        root.add(saveButton, 0, 2);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void refresh() {
    	
    }

}
