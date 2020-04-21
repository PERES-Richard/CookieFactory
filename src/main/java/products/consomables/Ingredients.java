package products.consomables;

import products.Consomable;

public abstract class Ingredients extends Consomable {

    public Ingredients(double price, String name) {
        super(price, name);

    }

    @Override
    protected double calculatePrice() {
        return priceHT;
    }

}
