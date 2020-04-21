package products.consomables.ingredients;

import products.consomables.Ingredients;

public class Dough extends Ingredients {

    public Dough(String name, double priceHT) {
        super(priceHT, name);
    }

    @Override
    public String getName() {
        return name;
    }
}