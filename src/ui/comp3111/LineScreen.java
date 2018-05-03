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
    public static boolean linePie = false; // LINE true PIE false
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
	public static Pane pane() {
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
        addSample();
        pane = loadSample();
        
        return pane;
	}
	
	/**
	 * Generates random sample data. The random sample data is based on the number of 
	 * initialized numeric columns and text columns.
	 */
	static void addSample() {
		try {
	        Random rand = new Random();
	        char[] base = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
	
	        for (int i = 0; i < numCols; ++i) {
	            Number[] content = new Number[row];
	
	            for (int j = 0; j < row; ++j) {
	                int type = rand.nextInt(100) + 1;
	
	                if (type >= 67) content[j] = rand.nextDouble();
	                else if (type >= 33) content[j] = rand.nextDouble();
	                else content[j] = rand.nextDouble();
	            }
	
	            DataColumn column = new DataColumn(DataType.TYPE_NUMBER, content);
	            table.addCol("numCol" + i, column);
	        }
	
	        for (int i = 0; i < textCols; ++i) {
	            String[] content = new String[row];
	
	            for (int j = 0; j < row; ++j) {
	                int length = rand.nextInt(10) + 1;
	
	                String word = "";
	                for (int k = 0; k < length; ++k) {
	                    word += base[rand.nextInt(base.length)];
	                }
	
	                content[j] = word;
	            }
	
	            DataColumn column = new DataColumn(DataType.TYPE_STRING, content);
	            table.addCol("textCol" + i, column);
	        }
		} catch (Exception e) {
        	System.out.println(e);
        	e.printStackTrace();
		}
	}
	
	/**
	 * Load data from either sample data or dataset.
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
	
	/**
	 * Change the type of chart into line or pie, based on {@link LineScreen#linePie}
	 */
	public static void changeType() {
		try {
			if (linePie) {
				lineChart = new Line(table);
                lcd = new LineChartDisplay(lineChart);
			} else {
				pieChart = new Pie(table);
				pcd = new PieChartDisplay(pieChart);
			}
			chartNode = (linePie ? lcd.display() : pcd.display());
		} catch (ChartException ex) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Chart Display Error");
          alert.setHeaderText("There was an error in displaying chart!");
          alert.setContentText(ex.getMessage());
          alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
          alert.showAndWait();
      }
	}
}
