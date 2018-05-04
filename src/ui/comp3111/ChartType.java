package ui.comp3111;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Objects;

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

	private static boolean change = false;
	
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
						try {
							AnimatedScreen.refresh();
						} catch (RuntimeException e) {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Chart Fail");
							alert.setHeaderText("Application fails to display the chart!");
							alert.setContentText("The DataTable does not fill the requirement!");

							alert.showAndWait();
						}
					} else {
						if (!change) {
							if (Objects.equals(tg.getSelectedToggle().getUserData().toString(), radioText[0])) {
								LineScreen.linePie = true;
							} else {
								LineScreen.linePie = false;
							}
						}
						try {
							LineScreen.refresh();
						} catch (RuntimeException e) {
							LineScreen.empty();
							if (!change) {
//								if (Objects.equals(tg.getSelectedToggle().getUserData().toString(), radioText[0])) {
								if (LineScreen.linePie) {
									LineScreen.linePie = false;
									change = true;
									radios[1].setSelected(true);
								}
								else {
									LineScreen.linePie = true;
									change = true;
									radios[0].setSelected(true);
								}
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Chart Fail");
								alert.setHeaderText("Application fails to display the chart!");
								alert.setContentText("The DataTable does not fill the requirement!");

								alert.showAndWait();
								change = false;
							}
						}
					}

				}
			}
		});
	}
	
	/**
	 * Returns the selected chart type in the radio button group.
	 * @return the selected chart type in the radio button group.
	 */
	static String getType() {
		return tg.getSelectedToggle().getUserData().toString();
	}
	
	/**
	 * Sets the selected chart type in the radio button group.
	 * @param i
	 * 			- the desired chart type in the radio button group.
	 */
	static void setType(int i) {
		radios[i].setSelected(true);
	}
	
	/** 
	 * Updates the GUI Window to load the Chart Type pane with its newest state.
	 */
	public static void refresh() {
		MainScreen.typePane = pane();
	}
}
