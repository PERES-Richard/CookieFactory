package products.recipes;

import products.Consomable;
import products.Product;

import java.util.Map;

public abstract class Recipe extends Product {
    public Recipe(Map<Product, Integer> composedWith, Map<Consomable, Integer> consomableNeeded) {
        super(composedWith, consomableNeeded);
    }
}
