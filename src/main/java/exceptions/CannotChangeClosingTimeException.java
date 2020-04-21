package exceptions;

public class CannotChangeClosingTimeException extends Exception {

    public CannotChangeClosingTimeException() {

        super("Cannot change to the new closing time because it's before the opening time");

    }
}
