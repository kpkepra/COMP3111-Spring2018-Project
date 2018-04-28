package core.comp3111;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public abstract class Chart{
    public abstract BorderPane display();
    protected abstract boolean isLegal();
}