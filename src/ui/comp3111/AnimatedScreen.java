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

    /**
     * AnimatedPie - Subclass of Pie function that has animation features.
     * 
     * @author kp-kepra
     *
     */
    public static class AnimatedPie extends Pie {

		public AnimatedPie(DataTable dt) throws ChartException { super(dt); }
    }
    /**
     * Initializes the animated pie chart and sets up the animation for the newly created pie chart.
     */
    public static void initialize() throws ChartException {
		pie = new AnimatedPie(table);
		chart = generateChart();
		setupAnimation();
    }
    
    /**
     * Generates PieChart object based on the data loaded in the Pie object.
     * @return PieChart object based on the data loaded in the Pie objec.t
     */
    private static PieChart generateChart() {
    	PieChart temp = new PieChart();
        Object[] numData =  pie.getData().getCol(pie.getNum()).getData();
        Object[] textData =  pie.getData().getCol(pie.getText()).getData();

        for (int i = 0 ; i < pie.getData().getNumRow(); ++i) {
            PieChart.Data slice = new PieChart.Data((String)textData[i], ((Number)numData[i]).doubleValue());
            temp.getData().add(slice);
        }
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
    	nameID = new ArrayList<String>();
    	
        chart.getData().stream().forEach(pieData -> {
        	nameID.add(pieData.getName());
        	// When user's cursor enters a pie slice.
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
            
            // When user's cursor leaves a pie slice.
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
    public static BorderPane pane() throws ChartException {
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
                chart = generateChart();
               	setupAnimation();
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
                chart = generateChart();
                setupAnimation();
                pane.setCenter(chart);
                pane.setMargin(chart, new Insets(12,12,12,12));
            }
        });

        GridPane selectAxis = new GridPane();
        Label textLabel = new Label("categories: ");
        Label numLabel = new Label("column of pie");
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
    
    /**
     * Loads external DataTable item to to the pie chart and updates the state of the animated chart.
     * 
     * @param dt
     * 			- DataTable to be loaded in the pie chart.
     */
    public static void setTable(DataTable dt) { table = dt; }
    
    /**
     * Loads external DataTable item to to the pie chart and updates the state of the animated chart.
     * 
     * @param dt
     * 			- DataTable to be loaded in the pie chart.
     */
    public static void setChart(DataTable dt) { table = dt; refresh(); }
    
    /**
     * Returns the Animated Pie Chart object.
     * @return the Animated Pie Chart object.
     */
    public static Chart getChart() { return pie; }
    
    /**
     * Updates the GUI Window to load the animated pie chart with its newest state.
     */
    public static void refresh() throws ChartException {
		MainScreen.chartc = pane();
		MainScreen.chartc.setMinWidth(400);
		MainScreen.chartc.setMaxWidth(400);
		MainScreen.chartc.setMinHeight(350);
		MainScreen.chartc.setMaxHeight(350);
		if (MainScreen.centerc.getChildren().size() < 2) {
			MainScreen.centerc.getChildren().add(1, MainScreen.chartc);
		} else {
			MainScreen.centerc.getChildren().set(1, MainScreen.chartc);
		}
    }
    
    /** 
	 * Empties the chart if requirement doesn't meet.
	 */
	public static void empty() {
        MainScreen.chartc = new Pane();
        MainScreen.chartc.setMinWidth(400);
        MainScreen.chartc.setMaxWidth(400);
        MainScreen.chartc.setMinHeight(350);
        MainScreen.chartc.setMaxHeight(350);
        if (MainScreen.centerc.getChildren().size() < 2) {
            MainScreen.centerc.getChildren().add(1, MainScreen.chartc);
        } else {
            MainScreen.centerc.getChildren().set(1, MainScreen.chartc);
        }
	}

}