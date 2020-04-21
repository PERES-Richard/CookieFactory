package products.consomables.ingredients;

import products.consomables.Ingredients;

public class Flavour extends Ingredients {

    public Flavour(String name, double priceHT) {
        super(priceHT, name);
    }

    @Override
    public String getName() {
        return name;
    }
}