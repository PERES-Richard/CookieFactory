package order.items;

import products.recipes.Recipe;

public abstract class CookieOrder extends ProductOrder {
    public CookieOrder(Recipe recipe, int nbCookies) {
        super(recipe, nbCookies);
    }
}