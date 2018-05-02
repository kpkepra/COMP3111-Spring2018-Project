package ui.comp3111;

import java.awt.TextArea;
import java.io.Console;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

import core.comp3111.*;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class LineScreen extends Main {
    private static NumberAxis xAxis = null;
    private static NumberAxis yAxis = null;
    private static Button btLineChartBackMain = null;
    
    static boolean linePie = true; // LINE true PIE false
    static int numCols = 20;
    static int textCols = 5;
    static int row = 5;

    static DataTable table = new DataTable();
    static Random rand = new Random();
    static char[] base = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
    
    static Line lineChart;
    static Pie pieChart;
    static LineChartDisplay lcd;
    static PieChartDisplay pcd;
    
    static BorderPane chartNode = null;
	
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

        pane = loadSample();
        initHandlers();
        
        return pane;
	}
	
	static BorderPane loadSample() {
        // Get 2 columns
        xAxis.setLabel("X");
        yAxis.setLabel("Y");
		
		try {
            // TEST PARAMETERS
            int numCols = 20;
            int textCols = 5;
            int row = 5;

            DataTable table = new DataTable();
            Random rand = new Random();
            char[] base = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};

            for (int i = 0; i < numCols; ++i) {
                Number[] content = new Number[row];

                for (int j = 0; j < row; ++j) {
                    int type = rand.nextInt(100) + 1;

                    if (type >= 67) content[j] = rand.nextInt(20);
                    else if (type >= 33) content[j] = rand.nextFloat();
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
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception Dialog");
            alert.setHeaderText("An exception occured during runtime");

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("The exception stacktrace was:");

//            TextArea textArea = new TextArea(exceptionText);
//            textArea.setEditable(false);
//            textArea.setWrapText(true);

//            textArea.setMaxWidth(Double.MAX_VALUE);
//            textArea.setMaxHeight(Double.MAX_VALUE);
//            GridPane.setVgrow(textArea, Priority.ALWAYS);
//            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
//            expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);
            alert.showAndWait();
        }
		return chartNode;
	}
	
    static void initHandlers() {
        // click handler
        btLineChartBackMain.setOnAction(e -> {
            Main.putSceneOnStage(0);
        });
    }
}
