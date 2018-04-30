package core.comp3111;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.Serializable;
import java.util.*;

/**
 * 2D array of data values with the following requirements: (1) There are 0 to
 * many columns (2) The number of row for each column is the same (3) 2 columns
 * may have different type (e.g. String and Number). (4) A column can be
 * uniquely identified by its column name (5) add/remove a column is supported
 * (6) Suitable exception handling is implemented
 *
 * @author cspeter
 *
 */
public class DataTable implements Serializable {

    /**
     * Construct - Create an empty DataTable
     */
    public DataTable() {

        // In this application, we use HashMap data structure defined in
        // java.util.HashMap
        dc = new HashMap<String, DataColumn>();
    }

    /**
     * Add a data column to the table.
     *
     * @param colName
     *            - name of the column. It should be a unique identifier
     * @param newCol
     *            - the data column
     * @throws DataTableException
     *             - It throws DataTableException if a column is already exist, or
     *             the row size does not match.
     */
    public void addCol(String colName, DataColumn newCol) throws DataTableException {
        if (containsColumn(colName)) {
            throw new DataTableException("addCol: The column already exists");
        }

        int curNumCol = getNumCol();
        if (curNumCol == 0) {
            dc.put(colName, newCol); // add the column
            return; // exit the method
        }

        // If there is more than one column,
        // we need to ensure that all columns having the same size

        int curNumRow = getNumRow();
        if (newCol.getSize() != curNumRow) {
            throw new DataTableException(String.format(
                    "addCol: The row size does not match: newCol(%d) and curNumRow(%d)", newCol.getSize(), curNumRow));
        }

        dc.put(colName, newCol); // add the mapping
    }

    /**
     * Remove a column from the data table
     *
     * @param colName
     *            - The column name. It should be a unique identifier
     * @throws DataTableException.
     *             It throws DataTableException if the column does not exist
     */
    public void removeCol(String colName) throws DataTableException {
        if (containsColumn(colName)) {
            dc.remove(colName);
            return;
        }
        throw new DataTableException("removeCol: The column does not exist");
    }

    /**
     * Get the DataColumn object based on the give colName. Return null if the
     * column does not exist
     *
     * @param colName
     *            The column name
     * @return DataColumn reference or null
     */
    public DataColumn getCol(String colName) {
        if (containsColumn(colName)) {
            return dc.get(colName);
        }
        return null;
    }

    /**
     * Check whether the column exists by the given column name
     *
     * @param colName
     * @return true if the column exists, false otherwise
     */
    public boolean containsColumn(String colName) {
        return dc.containsKey(colName);
    }

    /**
     * Return the number of column in the data table
     *
     * @return the number of column in the data table
     */
    public int getNumCol() {
        return dc.size();
    }

    /**
     * Return the number of row of the data table. This data structure ensures that
     * all columns having the same number of row
     *
     * @return the number of row of the data table
     */
    public int getNumRow() {
        if (dc.size() <= 0)
            return dc.size();

        // Pick the first entry and get its size
        // assumption: For DataTable, all columns should have the same size
        Map.Entry<String, DataColumn> entry = dc.entrySet().iterator().next();
        return dc.get(entry.getKey()).getSize();
    }

    /**
     * Return the column names of the data table
     *
     * @return the column names of the data table
     */
    public String[] getColNames() {
        return dc.keySet().toArray(new String[0]);
    }

    /**
     * Display UI for filtering data
     */
    public GridPane filterDisplay() throws DataTableException {
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

        ComboBox columnCombo = new ComboBox();
        for (String colName : getColNames()) {
            String colType = getCol(colName).getTypeName();
            if (Objects.equals(colType, DataType.TYPE_NUMBER)) columnCombo.getItems().add(colName);
        }

        columnCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                columnFilter = new_val;
            }
        });

        selectFilter.add(new Label("Select column as filter base: "), 0, 0);
        selectFilter.add(columnCombo, 0, 1);

        String[] operators = {"<", "<=", ">", ">=", "==", "!="};
        ComboBox operatorCombo = new ComboBox();
        for (String operator : operators) {
            operatorCombo.getItems().add(operator);
        }

        operatorCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                operatorFilter = new_val;
            }
        });

        selectFilter.add(new Label("Select operator to filter with: "), 1, 0);
        selectFilter.add(operatorCombo, 1, 1);

        TextField numberField = new TextField();
        numberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                if (!new_val.matches("\\d*")) {
                    numberField.setText(new_val.replaceAll("[^\\d]", ""));
                }
                if (new_val != null) numberFilter = new_val;
            }
        });

        selectFilter.add(new Label("Input number to check against: "), 2, 0);
        selectFilter.add(numberField, 2, 1);
        selectFilter.setStyle("-fx-font: 16 arial;");

        Button filterButton = new Button("Filter");
        filterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    HashMap<String, DataColumn> tempTable = filterData();

                    stage = new Stage();
                    stage.setOnHiding(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            if (save) {
                                dc = tempTable;
                            }
                            else {
                                DataTable newTable = new DataTable();
                                newTable.dc = tempTable;
                                // TODO: SAVE DATATABLE TO NEW DATASET
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

        selectFilter.add(filterButton, 0, 2, 3, 1);

        return selectFilter;
    }

    /**
     * Filter data according to input from user interface
     */
    private HashMap<String, DataColumn> filterData() throws DataTableException {
        if (operatorFilter == null || columnFilter == null || numberFilter == null) throw new DataTableException("Filter parameters are not filled");

        HashMap<String, ArrayList<Object>> temp = new HashMap<String, ArrayList<Object>>();

        for (String colName : getColNames()) {
            temp.put(colName, new ArrayList<Object>());
        }

        Number[] data = (Number[]) getCol(columnFilter).getData();
        Number op_filter = (Float.valueOf(numberFilter)).floatValue();

        for (int i = 0; i < getNumRow(); ++i) {
            if (filter(operatorFilter, op_filter, data[i])) {
                for (String colName : getColNames()) {
                    Object filteredCell = getCol(colName).getData()[i];
                    temp.get(colName).add(filteredCell);
                }
            }
        }

        HashMap<String, DataColumn> tempMap = new HashMap<String, DataColumn>();
        for (String colName : getColNames()) {
            String type = getCol(colName).getTypeName();
            DataColumn filteredCol = new DataColumn(type, temp.get(colName).toArray());
            tempMap.put(colName, filteredCol);
        }

        return tempMap;
    }

    /**
     * Check if the number pass the filter
     *
     * @param operator
     * 			- the string containing filter operator that will be used to filter the data table. Example: "<"
     * @param op_val
     * 			- the number that will be used for filter
     * @param number
     * 			- the number that will be checked against
     *
     * @return whether the data pass the filter
     */
    private boolean filter(String operator, Number op_val, Number number) {
        boolean ret_val = false;
        switch (operator) {
            case "<":
                ret_val = number.floatValue() < op_val.floatValue();
                break;
            case "<=":
                ret_val = number.floatValue() <= op_val.floatValue();
                break;
            case ">":
                ret_val = number.floatValue() > op_val.floatValue();
                break;
            case ">=":
                ret_val = number.floatValue() >= op_val.floatValue();
                break;
            case "==":
                ret_val = number.floatValue() == op_val.floatValue();
                break;
            case "!=":
                ret_val = number.floatValue() != op_val.floatValue();
                break;
        }

        return ret_val;
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


        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                save = true;
                stage.hide();
            }
        });

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

    public GridPane splitDisplay() {
        percentSplit = new float[2];
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
                if (!new_val.matches("\\d*")) {
                    numberField1.setText(new_val.replaceAll("[^\\d]", ""));
                }
                if (new_val != null) percentSplit[0] = (Float.valueOf(new_val)).floatValue();
            }
        });

        TextField numberField2 = new TextField();
        numberField2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                if (!new_val.matches("\\d*")) {
                    numberField2.setText(new_val.replaceAll("[^\\d]", ""));
                }
                if (new_val != null) percentSplit[1] = (Float.valueOf(new_val)).floatValue();
            }
        });

        root.add(numberField1, 0, 2);
        root.add(numberField2, 1, 2);

        Button splitButton = new Button("Split");
        splitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    DataTable[] newTables = randomSplit();

                    newTables[0].printTable();
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAA");
                    newTables[1].printTable();
                    //TODO: Save Data Table
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
     * Split two num
     *
     * @return array containing two DataTable, each holding number of data according to the ratio.
     */
    private DataTable[] randomSplit() throws DataTableException{
        if (percentSplit.length != 2) throw new DataTableException("Percent array length is not 2!");
        else if ((percentSplit[0] + percentSplit[1]) != 100.0) throw new DataTableException("Total number of percentage is not equal to 100!");

        Random rand = new Random();

        HashMap<String, ArrayList<Object>> table0 = new HashMap<String, ArrayList<Object>>();
        HashMap<String, ArrayList<Object>> table1 = new HashMap<String, ArrayList<Object>>();

        for (String colName : getColNames()) {
            table0.put(colName, new ArrayList<Object>());
            table1.put(colName, new ArrayList<Object>());
        }

        for (int i = 0; i < getNumRow(); ++i) {
            int tableSelect = rand.nextInt(100) + 1;

            for (String colName : getColNames()) {
                Object data = getCol(colName).getData()[i];

                if (tableSelect < percentSplit[0]) table0.get(colName).add(data);
                else table1.get(colName).add(data);
            }
        }

        DataTable[] newTables = { new DataTable(), new DataTable() };

        for (String colName : getColNames()) {
            String type = getCol(colName).getTypeName();
            Object[] colData0 = table0.get(colName).toArray();
            Object[] colData1 = table1.get(colName).toArray();

            DataColumn column0 = new DataColumn(type, colData0);
            DataColumn column1 = new DataColumn(type, colData1);
            newTables[0].addCol(colName, column0);
            newTables[1].addCol(colName, column1);
        }

        return newTables;
    }

    /**
     * Debugging method - prints DataTable
     */
    public void printTable() {
        for (String colName : getColNames()) {
            System.out.println("Column " + colName);
            DataColumn column  = getCol(colName);
            for (int i = 0; i < getNumRow(); ++i) System.out.println(column.getData()[i]);
        }
    }

    /**
     * Get the DataColumn map
     *
     * @returns Map<String, DataColumn> of the DataTable
     */
    public Map<String, DataColumn> getDc(){
        return dc;
    }

    // attribute: A java.util.Map interface
    // KeyType: String
    // ValueType: DataColumn
    private Map<String, DataColumn> dc;

    // Filtering Variables
    boolean save;
    Stage stage;
    String columnFilter;
    String operatorFilter;
    String numberFilter;

    // Split Variable
    float[] percentSplit;
}