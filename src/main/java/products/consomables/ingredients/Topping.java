package products.consomables.ingredients;


import products.consomables.Ingredients;

public class Topping extends Ingredients {

    public Topping(String name, double priceHT) {
        super(priceHT, name);
    }

    @Override
    public String getName() {
        return name;
    }
}
