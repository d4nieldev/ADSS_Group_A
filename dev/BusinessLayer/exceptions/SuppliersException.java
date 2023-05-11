package BusinessLayer.exceptions;

public class SuppliersException extends RuntimeException {
    public SuppliersException(String message) {
        super("SUPPLIERS EXCEPTION: " + message);
    }
}
