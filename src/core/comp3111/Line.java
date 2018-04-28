package core.comp3111;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Objects;

public class Line implements Chart {
    private DataTable data;
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

    private boolean isLegal() {
        int numCol = 0;

        for (String colName : data.getColNames()) {
            String colType = data.getCol(colName).getTypeName();
            if (Objects.equals(colType, DataType.TYPE_NUMBER)) {
                ++numCol;
                numCols.add(colName);
            }

            if (numCol >= 2) return true;
        }

        return false;
    }

    public BorderPane display() {
        BorderPane root = new BorderPane();
        root.setCenter(getChart(x, y));

        ComboBox xcombo = comboAxis();
        xcombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                root.getChildren().remove(root.lookup(".chart"));
                x = new_val;
                root.setCenter(getChart(x, y));
            }
        });

        ComboBox ycombo = comboAxis();
        ycombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                root.getChildren().remove(root.lookup(".chart"));
                y = new_val;
                root.setCenter(getChart(x, y));
            }
        });

        GridPane selectAxis = new GridPane();
        Label xlabel = new Label("Select column for x axis: ");
        Label ylabel = new Label("Select column for y axis: ");
        xlabel.setStyle("-fx-font: 16 arial;");
        ylabel.setStyle("-fx-font: 16 arial;");

        selectAxis.add(xlabel, 0, 0);
        selectAxis.add(xcombo, 0, 1);
        selectAxis.add(ylabel, 1, 0);
        selectAxis.add(ycombo, 1, 1);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        column1.setHalignment(HPos.CENTER);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.CENTER);
        column2.setPercentWidth(50);
        selectAxis.getColumnConstraints().addAll(column1, column2);
        root.setBottom(selectAxis);
        return root;
    }

    private LineChart getChart(String xname, String yname) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(xname);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yname);

        Number[] xData = (Number[]) data.getCol(xname).getData();
        Number[] yData = (Number[]) data.getCol(yname).getData();

        LineChart<Number, Number> lineChart = new LineChart<Number, Number> (xAxis, yAxis);

        XYChart.Series series = new XYChart.Series();

        for (int i = 0; i < data.getNumRow(); ++i) {
            series.getData().add(new XYChart.Data(xData[i], yData[i]));
        }

        lineChart.getData().add(series);

        return lineChart;
    }

    private ComboBox comboAxis() {
        ComboBox combo = new ComboBox();

        for (String colName : numCols) {
            combo.getItems().add(colName);
        }
        return combo;
    }
}