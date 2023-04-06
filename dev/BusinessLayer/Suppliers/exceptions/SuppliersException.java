package BusinessLayer.Suppliers.exceptions;

public class SuppliersException extends Exception {
    public SuppliersException(String message) {
        super("SUPPLIERS EXCEPTION: " + message);
    }
}
