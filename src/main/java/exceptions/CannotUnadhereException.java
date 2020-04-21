package exceptions;

import customer.CustomerType;

public class CannotUnadhereException extends Exception {
    public CannotUnadhereException(CustomerType customerType) {
        super("Cannot unadhere because you are not a Member (you are " + customerType.name() + ")");
    }
}
