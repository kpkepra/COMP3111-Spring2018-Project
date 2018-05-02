package core.comp3111;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Pie - A subclass of Chart, which is used to create a chart in the application. It stores the list of name of columns
 * that are of type text (textCols) and columns which are numeric and all-positive (numCols).
 * In addition, it also stores a string to indicate the name of the categories column (text),
 * and another string to indicate the name of the pie ratio column (num).
 *
 * @author apsusanto
 *
 */
public class Pie extends Chart implements Serializable{
    private ArrayList<String> textCols;
    private ArrayList<String> numCols;
    private String num;
    private String text;

    /**
     * Construct - Create a Pie object by giving the DataTable which will be plotted
     *
     * @param dt
     *             - The DataTable object that will be plotted.
     *
     * @throws ChartException
     *              It throws ChartException if the table that is used to create the chart did not fulfill the requirement
     *              of the chart.
     */
    public Pie(DataTable dt) throws ChartException{
        data = dt;
        textCols = new ArrayList<String>();
        numCols = new ArrayList<String>();

        if (!isLegal()) throw new ChartException("Data does not fill the requirement of pie chart: containing at least one numeric column, one text column, and there are no negative column present in any column.");

        text = textCols.get(0);
        num = numCols.get(0);
    }

    /**
     * Check whether the DataTable fulfills the requirement to create a pie chart.
     * Requirements: At least one numeric column with no negative values and at least one text column
     */
    protected boolean isLegal() {
        for (String colName : data.getColNames()) {
            String colType = data.getCol(colName).getTypeName();

            if (Objects.equals(colType, DataType.TYPE_NUMBER)) {
                boolean allPositive = true;
                Object[] colData = data.getCol(colName).getData();

                for (Object val : colData) {
                    if (((Number) val).floatValue() < 0.0) allPositive = false;
                }
                if (allPositive) numCols.add(colName);
            }
            else if (Objects.equals(colType, DataType.TYPE_STRING)) {
                textCols.add(colName);
            }
        }

        if (numCols.size() < 1 || textCols.size() < 1) {
            return false;
        }

        return true;
    }

    /**
     * Get the String object which contains the name of the column that will be used as the categories of the pie chart.
     *
     * @return String The categories column name
     */
    public String getText() { return text; }

    /**
     * Get the String object which contains the name of the column that will be used as the the pie chart number.
     *
     * @return String The number column name
     */
    public String getNum() { return num; }

    /**
     * Get the names of the text column in the table.
     *
     * @return ArrayList containing Strings of the column names.
     */
    public ArrayList<String> getTextCols() { return textCols; }

    /**
     * Get the names of the non-negative numeric columns in the table.
     *
     * @return ArrayList containing Strings of the column names.
     */
    public ArrayList<String> getNumCols() { return numCols; }

    /**
     * Set the name of the text column which will be used as the categories in pie chart.
     *
     * @param input
     *             - The text column name
     */
    public void setText(String input) { if (textCols.contains(input))text = input; }

    /**
     * Set the name of the numeric column which will be used as the ratio in pie chart.
     *
     * @param input
     *             - The numeric column name
     */
    public void setNum(String input) { if (numCols.contains(input)) num = input; }

    /**
     * Check the equality of two Pie charts.
     *
     * @param o
     *      The Pie to be checked against
     *
     * @returns True if the Pie contents are the same
     */
    @Override
    public boolean equals(Object o){
        Pie pie = (Pie) o;
        if (!pie.data.equals(pie.data)) return false;
        return true;
    }

}
