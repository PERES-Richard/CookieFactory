package products.recipes;

import products.Product;

public abstract class CookTechnique extends Product {

    public CookTechnique(double price, String name) {
        super(name, price);
    }

    @Override
    protected double calculatePrice() {
        return priceHT;
    }
}
