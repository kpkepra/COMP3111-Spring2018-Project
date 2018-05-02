package ui.comp3111;

import core.comp3111.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainScreen extends Pane {
	public static BorderPane pane;
	public static VBox leftc, centerc, rightc, tablec;
	public static HBox hc;
	public static Pane impexp, listView, typePane, filterPane, chartc;
	
	public static TransformDisplay tfDisplay;
	
	private Label lb_Title;
	
	public MainScreen() {
		super();
		lb_Title = new Label("CORGI3111");
		
	}	
	
	public Pane pane() {	     
	     // Layout the UI components
	     hc = new HBox(20);
	     hc.setAlignment(Pos.CENTER);
	     hc.getChildren().addAll(lb_Title);
	     
	     impexp = MyFileChooser.pane();
	     impexp.setMinWidth(240);
	     impexp.setMaxWidth(240);
	     
	     listView = Listbox.pane();
	     listView.setMinWidth(240);
	     listView.setMaxWidth(240);
	     
	     leftc = new VBox(20);
	     leftc.setAlignment(Pos.CENTER);
	     leftc.getChildren().addAll(impexp, listView);
	     leftc.setStyle("-fx-background-color: blue");
	     
	     typePane = ChartType.pane();
	     
	     tfDisplay = new TransformDisplay(new Transform(DataTableDisplay.getDT()));
	     filterPane = tfDisplay.splitFilter();
	     rightc = new VBox(20);
	     rightc.setAlignment(Pos.CENTER);
	     rightc.getChildren().addAll(typePane, filterPane);
	     rightc.setMinWidth(400);
	     rightc.setMaxWidth(400);
	     rightc.setStyle("-fx-background-color: blue");
	     //760
	     tablec = DataTableDisplay.displayTable();
	     tablec.setMinWidth(500);
	     tablec.setMaxWidth(500);
	     tablec.setMinHeight(300);
	     tablec.setMaxHeight(300);
	     tablec.setStyle("-fx-background-color: orange");

	     chartc = LineScreen.pane();
	     chartc.setStyle("-fx-background-color: red");
	     chartc.setMinWidth(500);
	     chartc.setMaxWidth(500);
	     chartc.setMinHeight(400);
	     chartc.setMaxHeight(400);
	     
	     centerc = new VBox(2);
	     centerc.setAlignment(Pos.CENTER);
	     centerc.getChildren().addAll(tablec, chartc);
	
	     pane = new BorderPane(centerc, hc, rightc, null, leftc);
	
	     // Apply style to the GUI components
	     lb_Title.getStyleClass().add("menu-title");
	     pane.getStyleClass().add("screen-background");
	     
	     initHandlers();
	     return pane;
	}
	
    static void initHandlers() {
    	
    }
}
