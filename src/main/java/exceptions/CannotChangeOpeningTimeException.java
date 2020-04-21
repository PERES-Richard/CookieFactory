package exceptions;

public class CannotChangeOpeningTimeException extends Exception {

    public CannotChangeOpeningTimeException() {

        super("Cannot change to the new opening time because it's after the closing time");
    }
}
