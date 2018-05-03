/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.comp3111;

import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Animated Chart - A pie chart that takes the data from current dataset and displays it. Hover animations are implemented in this
 * feature. 
 * 
 * @author Kevin Pratama
 */
public class AnimatedScreen extends Main {

	private static Pane pane;
    private static PieChart chart;
    private static boolean[] tg;
    private static ObservableList<PieChart.Data> pcData;
    private static ArrayList<String> nameID = new ArrayList<String>();

    /**
     * Initializes the animated chart.
     */
    public static void initialize() {
        // Add data to the observable list
    	chart = new PieChart();
        pcData = FXCollections.observableArrayList();
        pcData.add(new PieChart.Data("Nokia", 77.3));
        pcData.add(new PieChart.Data("RIM", 51.1));
        pcData.add(new PieChart.Data("Apple", 93.2));
        pcData.add(new PieChart.Data("HTC", 43.5));
        pcData.add(new PieChart.Data("Samsung", 94.0));
        pcData.add(new PieChart.Data("Others", 132.3));
        chart.setData(pcData);
        chart.setTitle("Smart Phone Sales 2011");
        setupAnimation();
    }
    
    /**
     * Runs the animation handler. Enables hovering animation when user's cursor moves to any of the pie slices.
     */
    private static void setupAnimation() {
    	tg = new boolean[pcData.size()];
    	for (int i = 0; i < pcData.size(); i++) {
    		tg[i] = false;
    	}
    	
        pcData.stream().forEach(pieData -> {
        	nameID.add(pieData.getName());
            System.out.println(pieData.getName() + ": " + pieData.getPieValue());
            pieData.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            	Bounds b1 = pieData.getNode().getBoundsInLocal();
                double newX = (b1.getWidth()) / 2 + b1.getMinX();
                double newY = (b1.getHeight()) / 2 + b1.getMinY();
                // Make sure pie wedge location is reset
                int idx = nameID.indexOf(pieData.getName());
                if (tg[idx] == false) {
                    pieData.getNode().setTranslateX(0);
                    pieData.getNode().setTranslateY(0);

                    // Create the animation
                    TranslateTransition tt = new TranslateTransition(
                            Duration.millis(150), pieData.getNode());
                    tt.setByX(newX/5);
                    tt.setByY(newY/5);
                    tt.play();
                    tg[idx] = true;
                }
            });
            
            pieData.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            	Bounds b1 = pieData.getNode().getBoundsInLocal();
                double newX = (b1.getWidth()) / 2 + b1.getMinX();
                double newY = (b1.getHeight()) / 2 + b1.getMinY();
                // Make sure pie wedge location is reset
                int idx = nameID.indexOf(pieData.getName());
                if (tg[idx] == true) {
                    pieData.getNode().setTranslateX(newX/5);
                    pieData.getNode().setTranslateY(newY/5);

                    // Create the animation
                    TranslateTransition tt = new TranslateTransition(
                            Duration.millis(150), pieData.getNode());
                    tt.setByX(-newX/5);
                    tt.setByY(-newY/5);
                    tt.play();
                	tg[idx] = false;
                }
            });
        });
    }
    
    /**
     * Pane function. Generates and returns a pane object that displays the animated chart.
     * 
     * @return Pane object containing the animated pie chart.
     */
    
    public static Pane pane() {
    	initialize();
    	System.out.println(chart.getData());
    	pane = new Pane();
    	pane.getChildren().add(chart);
    	return pane;
    }

}