package ui.comp3111;

import core.comp3111.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

/**
 * The Main class of this GUI application
 *
 * @author cspeter
 *
 */
public class Main extends Application {

    // Attribute: DataTable
    // In this sample application, a single data table is provided
    // You need to extend it to handle multiple data tables
    // Hint: Use java.util.List interface and its implementation classes (e.g.
    // java.util.ArrayList)
    private DataTable sampleDataTable = null;

    // Attributes: Scene and Stage
    private static final int SCENE_NUM = 2;
    private static final int SCENE_MAIN_SCREEN = 0;
    private static final int SCENE_LINE_CHART = 1;
    private static final String[] SCENE_TITLES = { "COMP3111 Chart - [Team Name]", "Sample Line Chart Screen" };
    private Stage stage = null;
    private Scene[] scenes = null;

    // To keep this application more structural,
    // The following UI components are used to keep references after invoking
    // createScene()

    // Screen 1: paneMainScreen
    private Button btSampleLineChartData, btSampleLineChartDataV2, btSampleLineChart;
    private Label lbSampleDataTable, lbMainScreenTitle;

    // Screen 2: paneSampleLineChartScreen
    private LineChart<Number, Number> lineChart = null;
    private NumberAxis xAxis = null;
    private NumberAxis yAxis = null;
    private Button btLineChartBackMain = null;

    /**
     * create all scenes in this application
     */
    private void initScenes() {
        scenes = new Scene[SCENE_NUM];
        scenes[SCENE_MAIN_SCREEN] = new Scene(paneMainScreen(), 400, 500);
        scenes[SCENE_LINE_CHART] = new Scene(paneLineChartScreen(), 800, 600);
        for (Scene s : scenes) {
            if (s != null)
                // Assumption: all scenes share the same stylesheet
                s.getStylesheets().add("Main.css");
        }
    }

    /**
     * This method will be invoked after createScenes(). In this stage, all UI
     * components will be created with a non-NULL references for the UI components
     * that requires interaction (e.g. button click, or others).
     */
    private void initEventHandlers() {
        initMainScreenHandlers();
        initLineChartScreenHandlers();
    }

    /**
     * Initialize event handlers of the line chart screen
     */
    private void initLineChartScreenHandlers() {

        // click handler
        btLineChartBackMain.setOnAction(e -> {
            putSceneOnStage(SCENE_MAIN_SCREEN);
        });
    }

    /**
     * Populate sample data table values to the chart view
     */
    private void populateSampleDataTableValuesToChart(String seriesName) {

        // Get 2 columns
        DataColumn xCol = sampleDataTable.getCol("X");
        DataColumn yCol = sampleDataTable.getCol("Y");

        // Ensure both columns exist and the type is number
        if (xCol != null && yCol != null && xCol.getTypeName().equals(DataType.TYPE_NUMBER)
                && yCol.getTypeName().equals(DataType.TYPE_NUMBER)) {

            lineChart.setTitle("Sample Line Chart");
            xAxis.setLabel("X");
            yAxis.setLabel("Y");

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
            lineChart.getData().clear();

            // add the new series as the only one series for this line chart
            lineChart.getData().add(series);
        }

    }

    /**
     * Initialize event handlers of the main screen
     */
    private void initMainScreenHandlers() {

        // click handler
        btSampleLineChartData.setOnAction(e -> {

            // In this example, we invoke SampleDataGenerator to generate sample data
            sampleDataTable = SampleDataGenerator.generateSampleLineData();
            lbSampleDataTable.setText(String.format("SampleDataTable: %d rows, %d columns", sampleDataTable.getNumRow(),
                    sampleDataTable.getNumCol()));

            populateSampleDataTableValuesToChart("Sample 1");

        });

        // click handler
        btSampleLineChartDataV2.setOnAction(e -> {

            // In this example, we invoke SampleDataGenerator to generate sample data
            sampleDataTable = SampleDataGenerator.generateSampleLineDataV2();
            lbSampleDataTable.setText(String.format("SampleDataTable: %d rows, %d columns", sampleDataTable.getNumRow(),
                    sampleDataTable.getNumCol()));

            populateSampleDataTableValuesToChart("Sample 2");

        });

        // click handler
        btSampleLineChart.setOnAction(e -> {
            putSceneOnStage(SCENE_LINE_CHART);
        });

    }

    /**
     * Create the line chart screen and layout its UI components
     *
     * @return a Pane component to be displayed on a scene
     */
    private Pane paneLineChartScreen() {

        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        btLineChartBackMain = new Button("Back");

        xAxis.setLabel("undefined");
        yAxis.setLabel("undefined");
        lineChart.setTitle("An empty line chart");

        // Layout the UI components
        VBox container = new VBox(20);
        container.getChildren().addAll(lineChart, btLineChartBackMain);
        container.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setCenter(container);

        // Apply CSS to style the GUI components
        pane.getStyleClass().add("screen-background");

        return pane;
    }

    /**
     * Creates the main screen and layout its UI components
     *
     * @return a Pane component to be displayed on a scene
     */
    private Pane paneMainScreen() {

        lbMainScreenTitle = new Label("COMP3111 Chart");
        btSampleLineChartData = new Button("Sample 1");
        btSampleLineChartDataV2 = new Button("Sample 2");
        btSampleLineChart = new Button("Sample Line Chart");
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

        return pane;
    }

    /**
     * This method is used to pick anyone of the scene on the stage. It handles the
     * hide and show order. In this application, only one active scene should be
     * displayed on stage.
     *
     * @param sceneID
     *            - The sceneID defined above (see SCENE_XXX)
     */
    private void putSceneOnStage(int sceneID) {

        // ensure the sceneID is valid
        if (sceneID < 0 || sceneID >= SCENE_NUM)
            return;

        stage.hide();
        stage.setTitle(SCENE_TITLES[sceneID]);
        stage.setScene(scenes[sceneID]);
        stage.setResizable(true);
        stage.show();
    }

    /**
     * All JavaFx GUI application needs to override the start method You can treat
     * it as the main method (i.e. the entry point) of the GUI application
     */
    @Override
    public void start(Stage primaryStage) {
//        --------------------------------- ORIGINAL ----------------------------------
//        try {
//
//            stage = primaryStage; // keep a stage reference as an attribute
//            initScenes(); // initialize the scenes
//            initEventHandlers(); // link up the event handlers
//            putSceneOnStage(SCENE_MAIN_SCREEN); // show the main screen
//
//        } catch (Exception e) {
//
//            e.printStackTrace(); // exception handling: print the error message on the console
//        }
//        -----------------------------------------------------------------------------

//        -------------------------------- TEST CHARTS --------------------------------
//        try {
//            // TEST PARAMETERS
//            boolean linePie = false; // LINE true PIE false
//            int numCols = 20;
//            int textCols = 5;
//            int row = 5;
//
//            stage = primaryStage;
//            DataTable table = new DataTable();
//            Random rand = new Random();
//            char[] base = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
//
//            for (int i = 0; i < numCols; ++i) {
//                Number[] content = new Number[row];
//
//                for (int j = 0; j < row; ++j) {
//                    int type = rand.nextInt(100) + 1;
//
//                    if (type >= 67) content[j] = rand.nextInt(20);
//                    else if (type >= 33) content[j] = rand.nextFloat();
//                    else content[j] = rand.nextDouble();
//                }
//
//                DataColumn column = new DataColumn(DataType.TYPE_NUMBER, content);
//                table.addCol("numCol" + i, column);
//            }
//
//            for (int i = 0; i < textCols; ++i) {
//                String[] content = new String[row];
//
//                for (int j = 0; j < row; ++j) {
//                    int length = rand.nextInt(10) + 1;
//
//                    String word = "";
//                    for (int k = 0; k < length; ++k) {
//                        word += base[rand.nextInt(base.length)];
//                    }
//
//                    content[j] = word;
//                }
//
//                DataColumn column = new DataColumn(DataType.TYPE_STRING, content);
//                table.addCol("textCol" + i, column);
//            }
//
//            Scene scene;
//            BorderPane chartNode = null;
//            try {
//                Chart lineChart = linePie ? new Line(table) : new Pie(table);
//                chartNode = lineChart.display();
//            } catch (ChartException ex) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Chart Display Error");
//                alert.setHeaderText("There was an error in displaying chart!");
//                alert.setContentText(ex.getMessage());
//                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
//                alert.showAndWait();
//            }
//            scene  = new Scene(chartNode, 1280, 720);
//            stage.setScene(scene);
//            stage.setResizable(true);
//            stage.show();
//        } catch (Exception e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Exception Dialog");
//            alert.setHeaderText("An exception occured during runtime");
//
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//            String exceptionText = sw.toString();
//
//            Label label = new Label("The exception stacktrace was:");
//
//            TextArea textArea = new TextArea(exceptionText);
//            textArea.setEditable(false);
//            textArea.setWrapText(true);
//
//            textArea.setMaxWidth(Double.MAX_VALUE);
//            textArea.setMaxHeight(Double.MAX_VALUE);
//            GridPane.setVgrow(textArea, Priority.ALWAYS);
//            GridPane.setHgrow(textArea, Priority.ALWAYS);
//
//            GridPane expContent = new GridPane();
//            expContent.setMaxWidth(Double.MAX_VALUE);
//            expContent.add(label, 0, 0);
//            expContent.add(textArea, 0, 1);
//
//        // Set expandable Exception into the dialog pane.
//            alert.getDialogPane().setExpandableContent(expContent);
//            alert.showAndWait();
//        }
//        -----------------------------------------------------------------------------

//        ------------------------------- TEST TRANSFORM ------------------------------
        try {
            DataTable data = SampleDataGenerator.generateSampleLineData();
            Transform transform = new Transform(data);
            TransformDisplay transformDisplay = new TransformDisplay(transform);

            stage = primaryStage;

            GridPane node = transformDisplay.filterDisplay();
            Scene scene  = new Scene(node, 1280, 720);

            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                        public void handle(WindowEvent we) {
                                            System.out.println("AFTER FILTER");
                                            transform.getDataTable().printTable();
                                        }
                                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
//        -----------------------------------------------------------------------------
    }

    /**
     * main method - only use if running via command line
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
//        testSplit();
//        testFilter();
    }
}