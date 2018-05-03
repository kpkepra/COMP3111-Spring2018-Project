package ui.comp3111;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;

/**
 * ChartType - A class that displays a group of radio buttons to choose the chart type to display.
 * The feature provides three radio buttons : line, pie, and animated pie chart. When any button
 * is clicked, the displayed chart will change to that type.
 * 
 * @author kp-kepra
 *
 */
public class ChartType extends Main {
	private static Label lb_title;
	
	/**
	 * Pane object that contains the radio button group for selecting chart type.
	 */
	public static BorderPane pane;
	
	private static ToggleGroup tg;
	private static String[] radioText = {"Line", "Pie", "Animated Pie"};
	private static RadioButton[] radios = new RadioButton[3];
	
    /**
     * Pane function. Generates and returns a pane object that displays the chart type selectors.
     * 
     * @return Pane object containing radio button group to change chart type.
     */
	public static Pane pane() {
		lb_title = new Label("Chart Type");
		tg = new ToggleGroup();		
		for (int i = 0; i < 3; i++) {
			radios[i] = new RadioButton(radioText[i]);
			radios[i].setToggleGroup(tg);
			radios[i].setUserData(radioText[i]);
			radios[i].getStyleClass().add("typePane");
		}
		
		radios[0].setSelected(true);
		
		pane = new BorderPane();
		pane.setTop(lb_title);
		
		VBox radioBox = new VBox(radios);
		pane.setCenter(radioBox);
		
		initHandlers();
		return pane;
	}
	
	/**
	 * InitHandlers function. Initializes the radio button group handler to detect if there is user
	 * input on selecting the radio buttons.
	 */
	static void initHandlers() {
		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle oldT, Toggle newT) {
				if (tg.getSelectedToggle() != null) {
					if (tg.getSelectedToggle().getUserData().toString() == radioText[2]) {
						MainScreen.centerc.getChildren().remove(MainScreen.chartc);
						MainScreen.chartc = AnimatedScreen.pane();
						MainScreen.centerc.getChildren().add(1, MainScreen.chartc);
					} else {
						if (tg.getSelectedToggle().getUserData().toString() == radioText[0]) {
							LineScreen.linePie = true;
						} else {
							LineScreen.linePie = false;
						}
						MainScreen.centerc.getChildren().remove(MainScreen.chartc);
						MainScreen.chartc = LineScreen.pane();
						MainScreen.centerc.getChildren().add(1, MainScreen.chartc);
					}

				}
			}
		});
	}
}
