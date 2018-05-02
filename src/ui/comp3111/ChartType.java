package ui.comp3111;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;

public class ChartType extends Main {
	private static Label lb_title;
	private static BorderPane pane;
	
	private static ToggleGroup tg;
	private static String[] radioText = {"Line", "Pie", "Animated Line"};
	private static RadioButton[] radios = new RadioButton[3];
	
	public static Pane pane() {
		lb_title = new Label("Chart Type");
		
		tg = new ToggleGroup();		
		for (int i = 0; i < 3; i++) {
			radios[i] = new RadioButton(radioText[i]);
			radios[i].setToggleGroup(tg);
		}
		
		radios[0].setSelected(true);
		
		pane = new BorderPane();
		pane.setTop(lb_title);
		
		VBox radioBox = new VBox(radios);
		pane.setCenter(radioBox);
		
		return pane;
	}
	
	static void initHandlers() {
		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle oldT, Toggle newT) {
				if (tg.getSelectedToggle() != null) {
					if (tg.getSelectedToggle().getUserData().toString() == "Line") {
						LineScreen.linePie = true;
					    LineScreen.changeType();
						
					} else {
						LineScreen.linePie = false;
						LineScreen.changeType();
					}
					MainScreen.pane.setCenter(MainScreen.chartc);
				}
			}
		});
	}
}
