package order;

import order.items.ProductOrder;
import order.items.SimpleCookieOrder;
import products.Consomable;
import products.ProductFactory;
import products.consomables.Drink;
import products.recipes.CookTechnique;
import products.recipes.CustomRecipe;
import products.recipes.DefaultRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProductOrderFactory {

    //    private int nbDrinks = 0; TODO with pack
    private ProductFactory productFactory;

    public ProductOrderFactory() {
        productFactory = new ProductFactory();
    }

    public List<ProductOrder> build(Map<String, Integer> drinks, Map<String, Integer> defaultRecipes, Map<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> customsCookies) {
        List<ProductOrder> orders = new ArrayList<>();

        if (drinks != null)
            orders.addAll(getDrinks(drinks));

        if (customsCookies != null)
            orders.addAll(getCustomsCookies(customsCookies));

        if (defaultRecipes != null)
            orders.addAll(getDefaultRecipes(defaultRecipes));


        return orders;
    }

    private List<ProductOrder> getCustomsCookies(Map<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> customsCookies) {
        List<ProductOrder> orders = new ArrayList<>();

        for (Map.Entry<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> entry : customsCookies.entrySet()) {
            CustomRecipe cookie = productFactory.build(entry.getKey());
            orders.add(new SimpleCookieOrder(cookie, entry.getValue()));
        }

        return orders;
    }

    private List<ProductOrder> getDefaultRecipes(Map<String, Integer> defaultRecipes) {
        List<ProductOrder> orders = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : defaultRecipes.entrySet()) {
            DefaultRecipe defaultRecipe = productFactory.buildDefaultRecipe(entry.getKey());
            orders.addAll(packOrNormalCookieOrder(defaultRecipe, entry.getValue()));
        }

        return orders;
    }

    private List<? extends ProductOrder> packOrNormalCookieOrder(DefaultRecipe defaultRecipe, int qte) {
        // TODO
        return Arrays.asList(new SimpleCookieOrder(defaultRecipe, qte));
    }

    private List<ProductOrder> getDrinks(Map<String, Integer> drinks) {
        List<ProductOrder> orders = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : drinks.entrySet()) {
            Drink drink = productFactory.buildDrink(entry.getKey());
            orders.add(new ProductOrder(drink, entry.getValue()));
        }

        return orders;
    }
}
