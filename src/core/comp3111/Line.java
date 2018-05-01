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

public class Line extends Chart {
    private ArrayList<String> numCols;
    private String x;
    private String y;

    public Line(DataTable dt) throws ChartException {
        data = dt;
        numCols = new ArrayList<String>();

        if (!isLegal()) throw new ChartException("Data does not fill the requirement of line chart: containing at least two numeric columns");

        x = numCols.get(0);
        y = numCols.get(1);
    }

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

    public String getX() { return x; }

    public String getY() { return y; }

    public ArrayList<String> getNumCols() { return numCols; }

    public void setX(String input) {
        if (numCols.contains(input)) x = input;
    }

    public void setY(String input) { if (numCols.contains(input)) y = input; }
}