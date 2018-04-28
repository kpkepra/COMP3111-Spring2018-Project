package core.comp3111;

public class ChartException extends Exception {

    public ChartException(String message) {
        super("ChartException: " + message);
    }
}
