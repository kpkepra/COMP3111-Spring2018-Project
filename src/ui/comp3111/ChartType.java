package ui.comp3111;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;

public class ChartType {
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
}
