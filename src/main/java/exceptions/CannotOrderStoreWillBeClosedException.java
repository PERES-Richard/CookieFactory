package exceptions;

public class CannotOrderStoreWillBeClosedException extends Exception {

    public CannotOrderStoreWillBeClosedException(int pickUp) {

        super("Cannot chose " + pickUp + " as pickup time, store wan't be opened");

    }
}
