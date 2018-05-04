package ui.comp3111;

import core.comp3111.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * MainScreen - Primary Pane class in the GUI Application. All panes and nodes are generated in the main Pane
 * and passed to the Main class to generate the JavaFX Scene. MainScreen class uses BorderPane object to put 
 * all the nodes in bordered layout (center, left, right, top).
 * 
 * @author kp-kepra
 *
 */
public class MainScreen {
	/**
	 * Primary view of the Application.
	 */
	public static BorderPane pane;
	protected static VBox leftc, centerc, rightc, tablec;
	protected static Pane listView, impexp, typePane, filterPane, chartc;
	private static HBox hc;
	protected static TransformDisplay tfDisplay;
	
	private Label lb_Title;
	/**
	 * Pane function. Gets all the required nodes and layout them in the main pane. The pane is then passed
	 * to the Main class for scene generation.
	 * Contains : Import/Export, Datasets-Charts lists, Table Display, Chart Display, Chart Type Selector, 
	 * Filter/Transformation.
	 * 
	 * @return Pane object containing the main view of the GUI layout.
	 */
	public Pane pane() {	     
	     // Layout the UI components
		 lb_Title = new Label("CORGI3111");
		 lb_Title.getStyleClass().add("menu-title");
	     hc = new HBox(20);
	     hc.setAlignment(Pos.CENTER_LEFT);
	     hc.getChildren().addAll(lb_Title);
	     
	     impexp = MyFileChooser.pane();
	     impexp.setMinWidth(200);
	     impexp.setMaxWidth(200);
	     
	     listView = Listbox.pane();
	     listView.setMinWidth(200);
	     listView.setMaxWidth(200);
	     
	     leftc = new VBox(20);
	     leftc.setAlignment(Pos.CENTER);
	     leftc.getChildren().addAll(impexp, listView);

	     typePane = ChartType.pane();
	     
	     tfDisplay = new TransformDisplay(new Transform(DataTableDisplay.getDT()));
	     filterPane = tfDisplay.splitFilter();
	     rightc = new VBox(20);
	     rightc.setAlignment(Pos.CENTER);
	     rightc.getChildren().addAll(typePane, filterPane);
	     rightc.setMinWidth(300);
	     rightc.setMaxWidth(300);
	     
	     tablec = DataTableDisplay.displayTable();
	     tablec.setMinWidth(200);
	     tablec.setMaxWidth(200);
	     tablec.setMinHeight(280);
	     tablec.setMaxHeight(280);

	     chartc = LineScreen.pane();
	     chartc.setMinWidth(200);
	     chartc.setMaxWidth(200);
	     chartc.setMinHeight(280);
	     chartc.setMaxHeight(280);
	     
	     centerc = new VBox(2);
	     centerc.setAlignment(Pos.CENTER);
	     centerc.getChildren().addAll(tablec, chartc);
	
	     pane = new BorderPane(centerc, hc, rightc, null, leftc);
	
	     // Apply style to the GUI components
	     lb_Title.getStyleClass().add("menu-title");
	     pane.getStyleClass().add("screen-background");
	     
	     return pane;
	}
}
