package products;

public abstract class Consomable extends Product {

    public Consomable(String name, double price) {
        super(name, price);
    }

    public Consomable(double price, String name) {
        super(name, price);
    }

    @Override
    protected double calculatePrice() {
        return priceHT;
    }
}
