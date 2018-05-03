package ui.comp3111;

import javafx.scene.Node;

/**
 * ChartDisplay - A superclass to display charts. This class can be utilized with JavaFX to display a DataTable object
 * visually.
 *
 * @author apsusanto
 *
 */
public abstract class ChartDisplay {
    /**
     * Display the chart. JavaFX Main UI can directly call this function to and put it on the scene to view the chart.
     *
     * @return Node JavaFX node that has a chart inside
     */
    public abstract Node display();
}
