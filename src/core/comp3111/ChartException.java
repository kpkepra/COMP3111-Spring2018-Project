package core.comp3111;

public class ChartException extends RuntimeException {

    public ChartException(String message) {
        super("ChartException: " + message);
    }
}
