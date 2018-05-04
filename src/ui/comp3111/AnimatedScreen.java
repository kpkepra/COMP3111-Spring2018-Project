/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.comp3111;

import java.util.ArrayList;

import core.comp3111.Chart;
import core.comp3111.ChartException;
import core.comp3111.DataTable;
import core.comp3111.Line;
import core.comp3111.Pie;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Animated Chart - A pie chart that takes the data from current dataset and displays it. Hover animations are implemented in this
 * feature. 
 * 
 * @author kp-kepra
 */
public class AnimatedScreen extends Main {

	private static BorderPane pane;
    private static PieChart chart = new PieChart();
    private static boolean[] tg;
    private static DataTable table = new DataTable();
    private static AnimatedPie pie;
    private static ArrayList<String> nameID = new ArrayList<String>();

    public static class AnimatedPie extends Pie {

		public AnimatedPie(DataTable dt) throws ChartException {
			super(dt);
		}
    }
    /**
     * Initializes the animated chart.
     */
    public static void initialize() {
    	try {
			pie = new AnimatedPie(table);
			chart = getChart(pie.getText(), pie.getNum());
		} catch (ChartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setupAnimation();
    }
    
    public static void loadData(DataTable dt) {
    	table = dt;
    }
    
    public static void refresh() {
		if (MainScreen.centerc.getChildren().contains(MainScreen.chartc)) { 
			MainScreen.centerc.getChildren().remove(MainScreen.chartc);
		}
		MainScreen.chartc = pane();
		MainScreen.chartc.setMinWidth(500);
		MainScreen.chartc.setMaxWidth(500);
		MainScreen.chartc.setMinHeight(400);
		MainScreen.chartc.setMaxHeight(400);
		MainScreen.centerc.getChildren().add(1, MainScreen.chartc);
    }
    
    private static PieChart getChart(String text, String num) {
    	PieChart temp = new PieChart();
    	temp.getStyleClass().add("black_font");
        Object[] numData =  pie.getData().getCol(pie.getNum()).getData();
        Object[] textData =  pie.getData().getCol(pie.getText()).getData();

        for (int i = 0 ; i < pie.getData().getNumRow(); ++i) {
            PieChart.Data slice = new PieChart.Data((String)textData[i], ((Number)numData[i]).doubleValue());
            temp.getData().add(slice);
        }
        temp.getData().forEach(d->
                d.getNode().getStyleClass().add("black_font"));
        return temp;
    }
    
    /**
     * Runs the animation handler. Enables hovering animation when user's cursor moves to any of the pie slices.
     * The animation uses a TranslateTransition object and offset based on each pie slice's orientation and position.
     */
    public static void setupAnimation() {
    	tg = new boolean[chart.getData().size()];
    	for (int i = 0; i < chart.getData().size(); i++) {
    		tg[i] = false;
    	}
    	
        chart.getData().stream().forEach(pieData -> {
        	nameID.add(pieData.getName());
            pieData.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            	Bounds b1 = pieData.getNode().getBoundsInLocal();
                double newX = (b1.getWidth()) / 2 + b1.getMinX();
                double newY = (b1.getHeight()) / 2 + b1.getMinY();
                // Make sure pie wedge location is reset
                int idx = nameID.indexOf(pieData.getName());
                if (tg[idx] == false) {
                    pieData.getNode().setTranslateX(0);
                    pieData.getNode().setTranslateY(0);

                    // Create the animation
                    TranslateTransition tt = new TranslateTransition(
                            Duration.millis(150), pieData.getNode());
                    tt.setByX(newX/5);
                    tt.setByY(newY/5);
                    tt.play();
                    tg[idx] = true;
                }
            });
            
            pieData.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            	Bounds b1 = pieData.getNode().getBoundsInLocal();
                double newX = (b1.getWidth()) / 2 + b1.getMinX();
                double newY = (b1.getHeight()) / 2 + b1.getMinY();
                // Make sure pie wedge location is reset
                int idx = nameID.indexOf(pieData.getName());
                if (tg[idx] == true) {
                    pieData.getNode().setTranslateX(newX/5);
                    pieData.getNode().setTranslateY(newY/5);

                    // Create the animation
                    TranslateTransition tt = new TranslateTransition(
                            Duration.millis(150), pieData.getNode());
                    tt.setByX(-newX/5);
                    tt.setByY(-newY/5);
                    tt.play();
                	tg[idx] = false;
                }
            });
        });
    }
    
    /**
     * Pane function. Generates and returns a pane object that displays the animated chart.
     * 
     * @return Pane object containing the animated pie chart.
     */
    public static BorderPane pane() {
    	initialize();
    	pane = new BorderPane();
    	pane.setCenter(chart);
    	pane.setMargin(chart, new Insets(12,12,12,12));

    	ComboBox textCombo = new ComboBox();
        for (String colName : pie.getTextCols()) textCombo.getItems().add(colName);
        textCombo.setValue(pie.getText());

        textCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                pane.getChildren().remove(chart);
                pie.setText(new_val);
                PieChart chart = getChart(pie.getText(), pie.getNum());
                pane.setCenter(chart);
                pane.setMargin(chart, new Insets(12,12,12,12));
            }
        });

        ComboBox numCombo = new ComboBox();
        for (String colName : pie.getNumCols()) numCombo.getItems().add(colName);
        numCombo.setValue(pie.getNum());

        numCombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String old_val, String new_val) {
                pane.getChildren().remove(chart);
                pie.setNum(new_val);
                PieChart chart = getChart(pie.getText(), pie.getNum());
                pane.setCenter(chart);
                pane.setMargin(chart, new Insets(12,12,12,12));
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
        pane.setBottom(selectAxis);
    	
    	return pane;
    }
    
    public static void setChart(DataTable dt) {
		table = dt;
		refresh();
    }
    
    public static Chart getChart() {
    	return pie;
    }

}