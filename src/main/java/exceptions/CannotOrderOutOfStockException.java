package exceptions;

import products.Consomable;

public class CannotOrderOutOfStockException extends Exception {
    public CannotOrderOutOfStockException(Consomable key, int i) {
        super("Cannot make this order because we are out of stock for those ingredients : needed " + i + " '" + key + "'");
    }

    public CannotOrderOutOfStockException() {
        super("Cannot make this order because we are out of stock for those ingredients");
    }
}
