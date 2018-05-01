package core.comp3111;

import java.util.*;

public class Transform {
    DataTable dataTable;

    // Filtering Variables
    boolean save;
    String columnFilter;
    String operatorFilter;
    String numberFilter;

    // Split Variable
    float[] percentSplit;

    public Transform(DataTable dt) {
        dataTable = dt;
    }

    public String getColumnFilter() { return columnFilter; }

    public String getOperatorFilter() { return operatorFilter; }

    public String getNumberFilter() { return numberFilter; }

    public float[] getPercentSplit() { return percentSplit; }

    public boolean getSave() { return save; }

    public void setColumnFilter(String input) { columnFilter = input; }

    public void setOperatorFilter(String input) { operatorFilter = input; }

    public void setNumberFilter(String input) { numberFilter = input; }

    public void setPercentSplit(float[] input) { percentSplit =Arrays.copyOf(input, input.length); }

    public void setSave(boolean input) { save = input; }

    /**
     * Split two num
     *
     * @return array containing two DataTable, each holding number of data according to the ratio.
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
     * Filter data according to input from user interface
     */
    public HashMap<String, DataColumn> filterData() throws TransformException, DataTableException {
        if (columnFilter == null) throw new TransformException("Column Filter parameters are not filled");
        if (numberFilter == null) throw new TransformException("Number Filter parameters are not filled");
        if (operatorFilter == null) throw new TransformException("Operator Filter parameters are not filled");


        HashMap<String, ArrayList<Object>> temp = new HashMap<String, ArrayList<Object>>();

        for (String colName : dataTable.getColNames()) {
            temp.put(colName, new ArrayList<Object>());
        }

        Number[] data;
        Number op_filter;

        try {
            data = (Number[]) dataTable.getCol(columnFilter).getData();
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

    public DataTable getDataTable() { return dataTable; }

    public void setDataTable(DataTable input) { dataTable = input; }

    public Map<String, DataColumn> getDc(){
        return dataTable.getDc();
    }

    public void setDc(Map<String, DataColumn> input) { dataTable.setDc(input); }
}
