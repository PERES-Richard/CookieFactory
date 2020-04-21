package exceptions;

import order.OrderBuilder;

public class IncompleteOrderException extends Exception {
    public IncompleteOrderException(OrderBuilder orderBuilder) {
        super("Cannot build the order '" + orderBuilder + "' : Order instantiation incomplete");
    }
}
