package exceptions;

import customer.CustomerType;

public class CannotAdhereException extends Exception {
    public CannotAdhereException(CustomerType customerType) {
        super("Cannot adhere because you are " + (customerType.equals(CustomerType.ANONYME) ? "not registered" : "already an adherent"));
    }
}
