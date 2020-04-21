package exceptions;

public class CannotPickUPOrderNotReadyException extends Exception {

    public CannotPickUPOrderNotReadyException() {
        super("Cannot validate order. Order not ready for pickUP");
    }

}
