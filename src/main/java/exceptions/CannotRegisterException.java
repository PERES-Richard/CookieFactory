package exceptions;

import customer.CustomerType;

public class CannotRegisterException extends Exception {
    public CannotRegisterException(CustomerType customerType) {
        super("Cannot register because you are " + (customerType.equals(CustomerType.MEMBER) ? "already a Member" : "already a registered"));
    }
}
