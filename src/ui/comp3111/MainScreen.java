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
	public static VBox leftc, centerc, rightc;
	public static HBox hc;
	public static Pane impexp, listView, typePane, filterPane, chartc, tablec;
	
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
	     rightc.setMinWidth(300);
	     rightc.setMaxWidth(300);
	     rightc.setStyle("-fx-background-color: blue");
	     
	     tablec = DataTableDisplay.displayTable();
	     tablec.setMinWidth(760);
	     tablec.setMaxWidth(760);
	     tablec.setMinHeight(300);
	     tablec.setMaxHeight(300);
	     tablec.setStyle("-fx-background-color: orange");

	     chartc = LineScreen.pane();
	     chartc.setStyle("-fx-background-color: red");
	     chartc.setMinWidth(760);
	     chartc.setMaxWidth(760);
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
