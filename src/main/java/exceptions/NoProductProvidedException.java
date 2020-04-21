package exceptions;

public class NoProductProvidedException extends Exception {

    public NoProductProvidedException() {
        super("No products provided");
    }
}
