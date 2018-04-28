package core.comp3111;

import java.util.HashMap;
import java.util.Map;

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
public class DataTable {

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
     * Filter the data table according to the input given from UI.
     *
     * @param op
     * 			- the filter operation that will be used to filter the data table. Example: "< 5".
     */
    public void filterData(String op) throws DataTableException {
        String[] operation = op.split("\\s+");

        Number op_val;

        if (operation.length != 2) throw new DataTableException("Operation does not have two words");

        if (!Objects.equals(operation[0], "<") &&
                !Objects.equals(operation[0], "<=") &&
                !Objects.equals(operation[0], ">=") &&
                !Objects.equals(operation[0], ">") &&
                !Objects.equals(operation[0], "==") &&
                !Objects.equals(operation[0], "!=")
                )
            throw new DataTableException("Comparison operator is invalid!");

        try {
            op_val = NumberFormat.getInstance().parse(operation[1]);
        }
        catch(NumberFormatException | ParseException e) {
            throw new DataTableException("Failure in parsing number. Please try again!");
        }

        Map<String, DataColumn> temp = new HashMap<String, DataColumn>();

        for (String colName : dc.keySet()) {
            DataColumn column = dc.get(colName);

            if (Objects.equals(column.getTypeName(), DataType.TYPE_NUMBER)) {
                Number[] values = (Number[]) column.getData();
                ArrayList<Number> filtered_values = new ArrayList<Number>();

                for (Number val : values) {
                    if (filter(operation[0], op_val, val)) filtered_values.add(val);
                }

                column.set(DataType.TYPE_NUMBER, filtered_values.toArray());
            }

            temp.put(colName, column);
        }

        dc = temp;
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
     */
    private boolean filter(String operator, Number op_val, Number number) {
        switch (operator) {
            case "<": return number.floatValue() < op_val.floatValue();
            break;
            case "<=": return number.floatValue() <= op_val.floatValue();
            break;
            case ">": return number.floatValue() > op_val.floatValue();
            break;
            case ">=": return number.floatValue() >= op_val.floatValue();
            break;
            case "==": return number.floatValue() == op_val.floatValue();
            break;
            case "!=": return number.floatValue() != op_val.floatValue();
            break;
        }
    }

    // attribute: A java.util.Map interface
    // KeyType: String
    // ValueType: DataColumn
    private Map<String, DataColumn> dc;

}