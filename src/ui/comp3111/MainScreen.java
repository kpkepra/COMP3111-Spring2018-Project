package ui.comp3111;

import core.comp3111.*;
import javafx.geometry.Pos;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainScreen extends Main {
	private static DataTable sampleDataTable = null;
    private static Button btSampleLineChartData;
	private static Button btSampleLineChartDataV2;
	private static Button btSampleLineChart;
    private static Label lbSampleDataTable;
	private static Label lbMainScreenTitle;
	
	public MainScreen() {
		super();
	}
	
	public static Pane pane() {
		 lbMainScreenTitle = new Label("COMP3111 Chart");
	     btSampleLineChart = new Button("Sample Line Chart");
	     btSampleLineChartDataV2 = new Button("Sample 2");
	     btSampleLineChartData = new Button("Sample 1");
	     lbSampleDataTable = new Label("DataTable: empty");
	
	     // Layout the UI components
	     HBox hc = new HBox(20);
	     hc.setAlignment(Pos.CENTER);
	     hc.getChildren().addAll(btSampleLineChartData, btSampleLineChartDataV2);
	
	     VBox container = new VBox(20);
	     container.getChildren().addAll(lbMainScreenTitle, hc, lbSampleDataTable, new Separator(), btSampleLineChart);
	     container.setAlignment(Pos.CENTER);
	
	     BorderPane pane = new BorderPane();
	     pane.setCenter(container);
	
	     // Apply style to the GUI components
	     btSampleLineChart.getStyleClass().add("menu-button");
	     lbMainScreenTitle.getStyleClass().add("menu-title");
	     pane.getStyleClass().add("screen-background");
	     
	     initHandlers();
	     return pane;
	}
	
    static void initHandlers() {

        // click handler
        btSampleLineChartData.setOnAction(e -> {
        	lbSampleDataTable.setText("Clicked1");

            // In this example, we invoke SampleDataGenerator to generate sample data
//            sampleDataTable = SampleDataGenerator.generateSampleLineData();
//            lbSampleDataTable.setText(String.format("SampleDataTable: %d rows, %d columns", sampleDataTable.getNumRow(),
//                    sampleDataTable.getNumCol()));

//            populateSampleDataTableValuesToChart("Sample 1");

        });

        // click handler
        btSampleLineChartDataV2.setOnAction(e -> {
        	lbSampleDataTable.setText("Clicked2");

            // In this example, we invoke SampleDataGenerator to generate sample data
//            sampleDataTable = SampleDataGenerator.generateSampleLineDataV2();
//            lbSampleDataTable.setText(String.format("SampleDataTable: %d rows, %d columns", sampleDataTable.getNumRow(),
//                    sampleDataTable.getNumCol()));
//
//            populateSampleDataTableValuesToChart("Sample 2");

        });

//        // click handler
        btSampleLineChart.setOnAction(e -> {
            Main.putSceneOnStage(SCENE_LINE_CHART);
        });

    }
    
    private static void populateSampleDataTableValuesToChart(String seriesName) {

        // Get 2 columns
        DataColumn xCol = sampleDataTable.getCol("X");
        DataColumn yCol = sampleDataTable.getCol("Y");

        // Ensure both columns exist and the type is number
        if (xCol != null && yCol != null && xCol.getTypeName().equals(DataType.TYPE_NUMBER)
                && yCol.getTypeName().equals(DataType.TYPE_NUMBER)) {

//            lineChart.setTitle("Sample Line Chart");
//            xAxis.setLabel("X");
//            yAxis.setLabel("Y");

            // defining a series
            XYChart.Series series = new XYChart.Series();

            series.setName(seriesName);

            // populating the series with data
            // As we have checked the type, it is safe to downcast to Number[]
            Number[] xValues = (Number[]) xCol.getData();
            Number[] yValues = (Number[]) yCol.getData();

            // In DataTable structure, both length must be the same
            int len = xValues.length;

            for (int i = 0; i < len; i++) {
                series.getData().add(new XYChart.Data(xValues[i], yValues[i]));
            }

            // clear all previous series
//            lineChart.getData().clear();

            // add the new series as the only one series for this line chart
//            lineChart.getData().add(series);

        }

    }
}
