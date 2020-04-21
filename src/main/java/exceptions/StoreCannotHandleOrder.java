package exceptions;

public class StoreCannotHandleOrder extends Exception {
    public StoreCannotHandleOrder(String store) {
        super("The store '" + store + "' cannot handle the order");
    }
}
