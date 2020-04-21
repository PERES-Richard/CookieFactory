package products.recipes;

import products.Consomable;
import products.Product;

import java.util.Map;

public class DefaultRecipe extends Recipe {

    public DefaultRecipe(Map<? extends Product, Integer> composedWith, Map<Consomable, Integer> consomableNeeded, String name) {
        super((Map<Product, Integer>) composedWith, consomableNeeded);
        this.name = name;
    }
}
