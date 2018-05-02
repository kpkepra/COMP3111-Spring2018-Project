package core.comp3111;

import java.util.*;

/**
 * Transform - A class containing methods to transform a DataTable. It
 * stores the tables which will be transformed (dataTable) and the transform parameters.
 * Filtering parameter includes a string which is the base column to check filter against (columnFilter),
 * another string for the filter operator (operatorFilter), and a string which will be
 * formatted to a Number functioning as a value to check filter against (numberFilter).
 * Split parameter consists of only an array which will be used as the ratio to split
 * the table into.
 *
 * @author apsusanto
 *
 */
public class Transform {
    DataTable dataTable;

    // Filtering Parameters
    String columnFilter;
    String operatorFilter;
    String numberFilter;

    // Split Parameters
    float[] percentSplit;

    /**
     * Construct - Create a Transform object by giving the DataTable to transform
     *
     * @param dt
     *             - The DataTable object that will be transformed.
     */
    public Transform(DataTable dt) {
        dataTable = dt;
    }

    /**
     * Get the String object that will be used as the base column to filter the data with.
     *
     * @return String The column name
     */
    public String getColumnFilter() { return columnFilter; }

    /**
     * Get the String object that will be used as the operator to filter the data with, for example, "<" or ">=".
     *
     * @return String The operator filter
     */
    public String getOperatorFilter() { return operatorFilter; }

    /**
     * Get the String object which can be formatted into a Number, for instance, "5.5" or "-3". This will function as a value to check filtration against.
     *
     * @return String The number filter
     */
    public String getNumberFilter() { return numberFilter; }

    /**
     * Get the float array which will be used as the ratio of resultant DataTables when splitting, for instance, {99.1, 0.9}.
     *
     * @return float[] The array containing percentages of split.
     */
    public float[] getPercentSplit() { return percentSplit; }

    /**
     * Get the DataTable object of the Transform.
     *
     * @return DataTable the table that will be transformed.
     */
    public DataTable getDataTable() { return dataTable; }

    /**
     * Set the base column to filter the data with.
     *
     * @param input
     *          - The column name.
     */
    public void setColumnFilter(String input) { columnFilter = input; }

    /**
     * Set the operator to filter the data with.
     *
     * @param input
     *          - The operator, e.g. "<" or ">=".
     */
    public void setOperatorFilter(String input) { operatorFilter = input; }

    /**
     * Set the value to check the filtration against.
     *
     * @param input
     *          - The string which can be formatted into a Number, e.g. "5" or "-3.3".
     */
    public void setNumberFilter(String input) { numberFilter = input; }

    /**
     * Set the percentage of split.
     *
     * @param input
     *          - Array of float with the ratio of split in each elements.
     */
    public void setPercentSplit(float[] input) { percentSplit =Arrays.copyOf(input, input.length); }

    /**
     * Set the table that will be transformed.
     *
     * @param input
     *          - DataTable object which will be transformed.
     */
    public void setDataTable(DataTable input) { dataTable = input; }

    /**
     * Split the dataTable of the class into two instances of DataTables according to the parameter percentSplit.
     *
     * @throws TransformException
     *             - It throws TransformException if the percentSplit does not have two elements, or the sum of elements of percentSplit is not equal to 100.
     * @throws DataTableException
     *             - It throws DataTableException if the method fails to add one of the columns into the new DataTables.
     *
     * @return DataTable[] The two resultant DataTables
     */
    public DataTable[] randomSplit() throws TransformException, DataTableException {
        if (percentSplit.length != 2) throw new TransformException("Percent array length is not 2!");
        else if ((percentSplit[0] + percentSplit[1]) != 100.0) throw new TransformException("Total number of percentage is not equal to 100!");

        Random rand = new Random();

        HashMap<String, ArrayList<Object>> table0 = new HashMap<String, ArrayList<Object>>();
        HashMap<String, ArrayList<Object>> table1 = new HashMap<String, ArrayList<Object>>();

        for (String colName : dataTable.getColNames()) {
            table0.put(colName, new ArrayList<Object>());
            table1.put(colName, new ArrayList<Object>());
        }

        for (int i = 0; i < dataTable.getNumRow(); ++i) {
            int tableSelect = rand.nextInt(100) + 1;

            for (String colName : dataTable.getColNames()) {
                Object data = dataTable.getCol(colName).getData()[i];

                if (tableSelect < percentSplit[0]) table0.get(colName).add(data);
                else table1.get(colName).add(data);
            }
        }

        DataTable[] newTables = { new DataTable(), new DataTable() };

        for (String colName : dataTable.getColNames()) {
            String type = dataTable.getCol(colName).getTypeName();
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
     * Filter the DataTable according to the parameters columnFilter, numberFilter, and operatorFilter.
     *
     * @throws TransformException
     *             - It throws TransformException if one of the filtering parameters is null, or it fails to format the data inside the column into a Number array,
     *             or it fails to format the numberFilter into float
     *
     * @return DataTable The new DataTable containing elements that passed the filter
     */
    public DataTable filterData() throws TransformException {
        if (columnFilter == null) throw new TransformException("Column Filter parameters are not filled");
        if (numberFilter == null) throw new TransformException("Number Filter parameters are not filled");
        if (operatorFilter == null) throw new TransformException("Operator Filter parameters are not filled");


        HashMap<String, ArrayList<Object>> temp = new HashMap<String, ArrayList<Object>>();

        for (String colName : dataTable.getColNames()) {
            temp.put(colName, new ArrayList<Object>());
        }

        Number[] data = new Number[dataTable.getNumRow()];
        Number op_filter;

        try {
            for (int i = 0; i < dataTable.getNumRow(); ++i) {
                data[i] = (Number) dataTable.getCol(columnFilter).getData()[i];
            }
        } catch (Exception e) {
            throw new TransformException("Failed to format column data");
        }
        try {
            op_filter = (Float.valueOf(numberFilter)).floatValue();
        } catch (Exception e) {
            throw new TransformException("Failed to format number");
        }

        for (int i = 0; i < dataTable.getNumRow(); ++i) {
            if (filter(operatorFilter, op_filter, data[i])) {
                for (String colName : dataTable.getColNames()) {
                    Object filteredCell = dataTable.getCol(colName).getData()[i];
                    temp.get(colName).add(filteredCell);
                }
            }
        }

        HashMap<String, DataColumn> tempMap = new HashMap<String, DataColumn>();
        for (String colName : dataTable.getColNames()) {
            String type = dataTable.getCol(colName).getTypeName();
            DataColumn filteredCol = new DataColumn(type, temp.get(colName).toArray());
            tempMap.put(colName, filteredCol);
        }

        DataTable tempTable = new DataTable();
        tempTable.setDc(tempMap);

        return tempTable;
    }

    /**
     * Check whether a number pass a filter
     *
     * @param operator
     * 			   - the string containing filter operator that will be used to filter the data table. Example: "<"
     * @param op_val
     * 			   - the number that will be used for filter
     * @param number
     * 		       - the number that will be checked against
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
}
