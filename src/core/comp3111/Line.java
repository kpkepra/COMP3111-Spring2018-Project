package core.comp3111;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Line - A subclass of Chart, which is used to create a chart in the application. It stores the list of name of columns
 * that are numeric type (numCols). In addition, it also stores a string to indicate the
 * name of the columns which will be used as the x-axis (x),
 * and another string to indicate the name of the columns for the y-axis (y).
 *
 * @author apsusanto
 *
 */
public class Line extends Chart {
    private ArrayList<String> numCols;
    private String x;
    private String y;

    /**
     * Construct - Create a Line object by giving the DataTable which will be plotted
     *
     * @param dt
     *             - The DataTable object that will be plotted.
     *
     * @throws ChartException
     *              It throws ChartException if the table that is used to create the chart did not fulfill the requirement
     *              of the chart.
     */
    public Line(DataTable dt) throws ChartException {
        data = dt;
        numCols = new ArrayList<String>();

        if (!isLegal()) throw new ChartException("Data does not fill the requirement of line chart: containing at least two numeric columns");

        x = numCols.get(0);
        y = numCols.get(1);
    }

    /**
     * Check whether the DataTable fulfills the requirement to create a pie chart.
     * Requirements: At least two numeric columns
     */
    protected boolean isLegal() {
        int numCol = 0;

        for (String colName : data.getColNames()) {
            String colType = data.getCol(colName).getTypeName();
            if (Objects.equals(colType, DataType.TYPE_NUMBER)) {
                ++numCol;
                numCols.add(colName);
            }
        }

        if (numCol >= 2) return true;
        return false;
    }

    /**
     * Get the String object which contains the name of the column that will be used for the X axis of the chart.
     *
     * @return String The x-axis column name
     */
    public String getX() { return x; }

    /**
     * Get the String object which contains the name of the column that will be used for the Y axis of the chart.
     *
     * @return String The y-axis column name
     */
    public String getY() { return y; }

    /**
     * Get the names of the numeric columns in the table.
     *
     * @return ArrayList containing Strings of the column names.
     */
    public ArrayList<String> getNumCols() { return numCols; }

    /**
     * Set the name of the text column which will be used to plot the X axis in pie chart.
     *
     * @param input
     *             - The x-axis column name
     */
    public void setX(String input) {
        if (numCols.contains(input)) x = input;
    }

    /**
     * Set the name of the text column which will be used to plot the Y axis in pie chart.
     *
     * @param input
     *             - The y-axis column name
     */
    public void setY(String input) { if (numCols.contains(input)) y = input; }
}