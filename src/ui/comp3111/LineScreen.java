package ui.comp3111;

import java.util.Random;

import core.comp3111.*;
import javafx.geometry.Pos;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * LineScreen - A center pane that displays the static charts : line and pie chart. The pane
 * takes the value from table pane that is above it and translates the data into charts. Line 
 * chart takes any two numeric columns, while pie chart takes any one numeric column and text
 * column. Additional dropdown button is provided to change the desired columns to be displayed.
 * 
 * @author kp-kepra
 *
 */
public class LineScreen extends Main {
    private static NumberAxis xAxis = null;
    private static NumberAxis yAxis = null;
    private static Button btLineChartBackMain = null;
    
    /** 
     * Boolean field to determine the type of chart to display.
     * 
     * If true, show the line chart. Otherwise show the pie chart.
     */
    public static boolean linePie = true; // LINE true PIE false
    protected static int numCols = 20, textCols = 5, row = 5;
    
    protected static DataTable table = new DataTable();
    protected static Random rand = new Random();
    protected static char[] base = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
    
    protected static Line lineChart;
    protected static Pie pieChart;
    protected static LineChartDisplay lcd;
    protected static PieChartDisplay pcd;
    
    protected static BorderPane chartNode = null;
	
    /**
     * Pane function. Generates and returns a pane object that displays the chart.
     * 
     * @return Pane object containing the line or pie chart.
     */
	public static Pane pane() throws ChartException{
		xAxis = new NumberAxis();
        yAxis = new NumberAxis();

        btLineChartBackMain = new Button("Back");

        xAxis.setLabel("undefined");
        yAxis.setLabel("undefined");

        // Layout the UI components
        VBox container = new VBox(20);
        container.getChildren().addAll(btLineChartBackMain);
        container.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setCenter(container);

        // Apply CSS to style the GUI components
        pane.getStyleClass().add("screen-background");
        pane = generateChart();
        
        
        return pane;
	}
	
	/**
	 * Sets the DataTable table according to dt.
	 * @param dt
	 * 			- Desired DataTable object to be assigned.
	 */
	static void setTable(DataTable dt) { table = dt; }
	
	/**
	 * Generates chart based on the assigned DataTable table object.
	 * 
	 * @return static chart based on the assigned DataTable table object.
	 * @throws ChartException
	 */
	static BorderPane generateChart() throws ChartException {
		xAxis.setLabel("X");
		yAxis.setLabel("Y");
		
		if (table.getNumCol() > 0 && table.getNumRow() > 0) {
			if (linePie) {
				lineChart = new Line(table);
				lcd = new LineChartDisplay(lineChart);
			} else {
				pieChart = new Pie(table);
				pcd = new PieChartDisplay(pieChart);
			}
			chartNode = (linePie == true ? lcd.display() : pcd.display());
		} else {
			chartNode = new BorderPane();
		}

		return chartNode;
	}
	
	/**
	 * Sets the Chart and DataTable according to the desired objects.
	 * If Chart is null, then the displayed Chart will take data from dt.
	 * The function will then refreshes the state of the static chart display.
	 * @param ct
	 * 			- Desired Chart object to be displayed.
	 * 
	 * @param dt
	 * 			- Desired DataTable object to be displayed.
	 */
	static void setChart(Chart ct, DataTable dt) {
		// Table
		table = dt;
		
		// Chart
		if (ct instanceof Pie) {
			pieChart = (ct != null ? (Pie) ct : new Pie(table));
			pcd = new PieChartDisplay(pieChart);
		} else {
			lineChart = (ct != null ? (Line) ct : new Line(table));
			lcd = new LineChartDisplay(lineChart);
		}
		chartNode = (linePie == true ? lcd.display() : pcd.display());
		
		refresh();
	}
	
	/**
	 * Returns the pie or line chart depending on the displayed chart.
	 * @return the pie or line chart depending on the displayed chart.
	 */
	static Chart getChart() { return (linePie == true ? lineChart : pieChart); }
	
	/** 
	 * Updates the GUI Window to load the pie or line chart with its newest state.
	 */
	static void refresh() throws ChartException{
		MainScreen.chartc = pane();
		if (MainScreen.centerc.getChildren().contains(MainScreen.chartc)) {
			MainScreen.centerc.getChildren().remove(MainScreen.chartc);
		}
		MainScreen.chartc.setMinWidth(400);
		MainScreen.chartc.setMaxWidth(400);
		MainScreen.chartc.setMinHeight(350);
		MainScreen.chartc.setMaxHeight(350);
		MainScreen.centerc.getChildren().add(1, MainScreen.chartc);
	}
}
