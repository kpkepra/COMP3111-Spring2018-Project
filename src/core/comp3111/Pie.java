package core.comp3111;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Objects;

public class Pie extends Chart {
    private DataTable data;
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
                Number[] colData = (Number[]) data.getCol(colName).getData();

                for (Number val : colData) {
                    if (val.floatValue() < 0.0) allPositive = false;
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

    public BorderPane display() {
        BorderPane root = new BorderPane();
        root.setCenter(getChart(text, num));

        ComboBox textCombo = new ComboBox();
        for (String colName : textCols) textCombo.getItems().add(colName);

        textCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                root.getChildren().remove(root.lookup(".chart"));
                text = new_val;
                root.setCenter(getChart(text, num));
            }
        });

        ComboBox numCombo = new ComboBox();
        for (String colName : numCols) numCombo.getItems().add(colName);

        numCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                root.getChildren().remove(root.lookup(".chart"));
                num = new_val;
                root.setCenter(getChart(text, num));
            }
        });

        GridPane selectAxis = new GridPane();
        Label textLabel = new Label("Select text column for categories: ");
        Label numLabel = new Label("Select numeric column for pie data: ");
        textLabel.setStyle("-fx-font: 16 arial;");
        numLabel.setStyle("-fx-font: 16 arial;");

        selectAxis.add(textLabel, 0, 0);
        selectAxis.add(textCombo, 0, 1);
        selectAxis.add(numLabel, 1, 0);
        selectAxis.add(numCombo, 1, 1);

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

    private PieChart getChart(String text, String num) {
        PieChart pieChart = new PieChart();
        
        Number[] numData = (Number[]) data.getCol(num).getData();
        String[] textData = (String[]) data.getCol(text).getData();

        for (int i = 0 ; i < data.getNumRow(); ++i) {
            PieChart.Data slice = new PieChart.Data(textData[i], numData[i].floatValue());
            pieChart.getData().add(slice);
        }

        return pieChart;
    }
}
