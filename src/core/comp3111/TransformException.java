package core.comp3111;

@SuppressWarnings("serial")
public class TransformException extends Exception {

    public TransformException(String message) {
        super("TransformException: " + message);
    }

}
