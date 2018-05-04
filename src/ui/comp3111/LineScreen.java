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
	
	static void loadData(DataTable dt) {
		table = dt;
	}
	
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
	
	static void setChart(Chart ct, DataTable dt) {
		// Table
		table = dt;
		
		// Chart
		if (ct instanceof Pie) {
			pieChart = (Pie) ct;
			pcd = new PieChartDisplay(pieChart);
		} else {
			lineChart = (Line) ct;
			lcd = new LineChartDisplay(lineChart);
		}
		chartNode = (linePie == true ? lcd.display() : pcd.display());
		
		refresh();
		System.out.println("refresh");
	}
	
	static void refresh() {
		try {
			MainScreen.chartc = pane();
			if (MainScreen.centerc.getChildren().contains(MainScreen.chartc)) { 
				MainScreen.centerc.getChildren().remove(MainScreen.chartc);
			}
			MainScreen.chartc.setMinWidth(500);
			MainScreen.chartc.setMaxWidth(500);
			MainScreen.chartc.setMinHeight(400);
			MainScreen.chartc.setMaxHeight(400);
			MainScreen.centerc.getChildren().add(1, MainScreen.chartc);
		} catch (RuntimeException e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Chart Fail");
			alert.setHeaderText("Application fails to display the chart!");
			alert.setContentText("The DataTable does not fill the requirement!");

			alert.showAndWait();
		}
	}
	
	static Chart getChart() {
		return (linePie == true ? lineChart : pieChart);
	}
	
	/**
	 * Load data from either sample data.
	 * 
	 * @return Pane containing the chart Node.
	 */
	static BorderPane loadSample() {
        // Get 2 columns
        xAxis.setLabel("X");
        yAxis.setLabel("Y");
    		try {
    			if (linePie) {
    				lineChart = new Line(table);
    				lcd = new LineChartDisplay(lineChart);
    			} else {
    				pieChart = new Pie(table);
    				pcd = new PieChartDisplay(pieChart);
    			}
    			chartNode = (linePie == true ? lcd.display() : pcd.display());
    		} catch (ChartException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Chart Display Error");
                alert.setHeaderText("There was an error in displaying chart!");
                alert.setContentText(ex.getMessage());
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
      		}
		return chartNode;
	}
}
