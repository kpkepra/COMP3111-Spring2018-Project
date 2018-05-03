package ui.comp3111;

import core.comp3111.Pie;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * PieChartDisplay - A subclass of ChartDisplay. This class can be utilized to display a pie chart on a JavaFX
 * interface. It stores pie chart object which has the data contained in (pieChart).
 *
 * @author apsusanto
 *
 */
public class PieChartDisplay extends ChartDisplay{
    private Pie piechart;

    /**
     * Construct - Create a PieChartDisplay object by giving the Line object which will be displayed in the FXnode.
     *
     * @param ch
     *             - The Pie object which will be plotted.
     *
     */
    public PieChartDisplay(Pie ch) {
        piechart = ch;
    }

    /**
     * Displays the JavaFX node containing a chart inside. In addition, the node also includes an
     * option to select the desired categories and pie number.
     *
     * @return A BorderPane consisting of the chart and options.
     */
    public BorderPane display() {
        BorderPane root = new BorderPane();
        PieChart chart = getChart(piechart.getText(), piechart.getNum());
        root.setCenter(chart);
        root.setMargin(chart, new Insets(12,12,12,12));

        ComboBox textCombo = new ComboBox();
        for (String colName : piechart.getTextCols()) textCombo.getItems().add(colName);

        textCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                root.getChildren().remove(chart);
                piechart.setText(new_val);
                PieChart chart = getChart(piechart.getText(), piechart.getNum());
                root.setCenter(chart);
                root.setMargin(chart, new Insets(12,12,12,12));
            }
        });

        ComboBox numCombo = new ComboBox();
        for (String colName : piechart.getNumCols()) numCombo.getItems().add(colName);

        numCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                root.getChildren().remove(chart);
                piechart.setNum(new_val);
                PieChart chart = getChart(piechart.getText(), piechart.getNum());
                root.setCenter(chart);
                root.setMargin(chart, new Insets(12,12,12,12));
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

    /**
     * Displays the JavaFX node containing a chart inside. In addition, the node also includes an
     * option to select the desired categories and pie number.
     *
     * @return A BorderPane consisting of the chart and options.
     */
    private PieChart getChart(String text, String num) {
        PieChart temp = new PieChart();

        Object[] numData =  piechart.getData().getCol(piechart.getNum()).getData();
        Object[] textData =  piechart.getData().getCol(piechart.getText()).getData();

        for (int i = 0 ; i < piechart.getData().getNumRow(); ++i) {
            PieChart.Data slice = new PieChart.Data((String)textData[i], ((Number)numData[i]).floatValue());
            temp.getData().add(slice);
        }
        return temp;
    }

}
