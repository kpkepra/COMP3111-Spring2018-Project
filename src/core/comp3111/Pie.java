package core.comp3111;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Pie extends Chart{
    private ArrayList<String> textCols;
    private ArrayList<String> numCols;
    private String num;
    private String text;

    public Pie(DataTable dt) throws ChartException{
        data = dt;
        textCols = new ArrayList<String>();
        numCols = new ArrayList<String>();

        if (!isLegal()) throw new ChartException("Data does not fill the requirement of pie chart: containing at least one numeric column, one text column, and there are no negative column present in any column.");

        text = textCols.get(0);
        num = numCols.get(0);
    }

    protected boolean isLegal() {
        for (String colName : data.getColNames()) {
            String colType = data.getCol(colName).getTypeName();

            if (Objects.equals(colType, DataType.TYPE_NUMBER)) {
                boolean allPositive = true;
                Object[] colData = data.getCol(colName).getData();

                for (Object val : colData) {
                    if (Double.valueOf((String)val) < 0.0) allPositive = false;
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

    public String getText() { return text; }

    public String getNum() { return num; }


    @Override
    public boolean equals(Object o){
        Pie pie = (Pie) o;
        if (!pie.data.equals(pie.data)) return false;
        return true;
    }

    public ArrayList<String> getTextCols() { return textCols; }

    public ArrayList<String> getNumCols() { return numCols; }

    public void setText(String input) { if (textCols.contains(input))text = input; }

    public void setNum(String input) { if (numCols.contains(input)) num = input; }

}
