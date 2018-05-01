package core.comp3111;

import javafx.scene.layout.BorderPane;

public abstract class Chart{
    protected DataTable data;

    protected abstract boolean isLegal();

    public DataTable getData() { return data; }

    public void setData(DataTable input) { data = input; }
}