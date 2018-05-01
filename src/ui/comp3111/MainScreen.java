package ui.comp3111;

import core.comp3111.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainScreen extends Main {
    private static Button bt_SampleLine;
	private static Button bt_SampleLine2;
	private static Button bt_SampleLineChart;
	
	private static Button 
		bt_Transform;
	
	
    private static Label lb_Left, lb_Right, lb_Chart, lb_Table;
	private static Label lbMainScreenTitle;
	
	public MainScreen() {
		super();
	}
	
	public static Pane pane() {
		 lbMainScreenTitle = new Label("CORGI3111");
	     bt_SampleLineChart = new Button("Sample Line Chart");
	     bt_SampleLine2 = new Button("Sample 2");
	     bt_SampleLine = new Button("Sample 1");
	     lb_Left = new Label("Dataset");
	     lb_Chart = new Label("Chart");
	     lb_Table = new Label("Table");
	     
	     bt_Transform = new Button("Transform");
	     
	     // Layout the UI components
	     HBox hc = new HBox(20);
	     hc.setAlignment(Pos.CENTER);
	     hc.getChildren().addAll(lbMainScreenTitle);
	     
	     Pane leftc = MyFileChooser.pane();
	     
	     Pane rightc = ChartType.pane();
	     
	     HBox tablec = new HBox(20);
	     tablec.setAlignment(Pos.CENTER);
	     tablec.getChildren().addAll(lb_Table);

	     Pane chartc = LineScreen.pane();
	     
	     HBox centerc = new HBox(2);
	     centerc.setAlignment(Pos.CENTER);
	     centerc.getChildren().addAll(tablec, chartc);
//	     hc.getChildren().addAll(bt_SampleLine, bt_SampleLine2);
	
//	     VBox container = new VBox(20);
//	     container.getChildren().addAll(lbMainScreenTitle, hc, lbSampleDataTable, new Separator(), bt_SampleLineChart);
//	     container.setAlignment(Pos.CENTER);
	
	     BorderPane pane = new BorderPane();
	     pane.setLeft(leftc);
	     pane.setRight(rightc);
	     pane.setCenter(centerc);
	     pane.setTop(hc);
	
	     // Apply style to the GUI components
	     bt_SampleLineChart.getStyleClass().add("menu-button");
	     lbMainScreenTitle.getStyleClass().add("menu-title");
	     pane.getStyleClass().add("screen-background");
	     
	     initHandlers();
	     return pane;
	}
	
    static void initHandlers() {

        // click handler
        bt_SampleLine.setOnAction(e -> {
        	lb_Left.setText("Clicked1");
        });

        // click handler
        bt_SampleLine2.setOnAction(e -> {
        	lb_Left.setText("Clicked2");
        });

        // click handler
        bt_SampleLineChart.setOnAction(e -> {
            Main.putSceneOnStage(1);
        });

    }
}
