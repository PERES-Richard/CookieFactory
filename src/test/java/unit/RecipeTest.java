package unit;

import main.BDSingleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import products.Consomable;
import products.Product;
import products.consomables.Drink;
import products.consomables.ingredients.Dough;
import products.consomables.ingredients.Flavour;
import products.consomables.ingredients.Topping;
import products.recipes.CustomRecipe;
import products.recipes.DefaultRecipe;
import products.recipes.cookingTechnique.Cooking;
import products.recipes.cookingTechnique.Mix;
import store.Store;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeTest {

    private BDSingleton singleton = BDSingleton.getSINGLETON();

    @BeforeEach
    void setUp() {

    }

    @Test
    void test_margin_custom_recipe() {
        DefaultRecipe defaultRecipe;
        CustomRecipe customRecipe;

        // STORE
        Integer[] hours = {10, 19};
        Map<DayOfWeek, Integer[]> workingHours = new HashMap<>();
        workingHours.put(DayOfWeek.MONDAY, hours);
        workingHours.put(DayOfWeek.TUESDAY, hours);
        workingHours.put(DayOfWeek.WEDNESDAY, hours);
        workingHours.put(DayOfWeek.THURSDAY, hours);
        workingHours.put(DayOfWeek.FRIDAY, hours);
        workingHours.put(DayOfWeek.SATURDAY, hours);
        workingHours.put(DayOfWeek.SUNDAY, hours);
        Store store = new Store("Store Adress", workingHours, 0.2);

        // STOCK
        Drink drink = singleton.getDrinkByName("Coke");
        Flavour flavour = new Flavour("Flavour", 1);
        Dough dough = new Dough("Dough", 1);
        Topping topping = new Topping("Topping", 1);
        store.addStock(drink, 100);
        store.addStock(flavour, 100);
        store.addStock(dough, 100);
        store.addStock(topping, 100);

        // RECIPE
        Mix mix = new Mix("Mix", 1);
        Cooking cooking = new Cooking("Cooking", 1);
        Map<Product, Integer> composed = new HashMap<>();
        composed.put(mix, 1);
        composed.put(cooking, 1);
        Map<Consomable, Integer> consomables = new HashMap<>();
        consomables.put(flavour, 1);
        consomables.put(dough, 1);
        consomables.put(topping, 1);

        defaultRecipe = new DefaultRecipe(composed, consomables, "default-recipe-test");
        customRecipe = new CustomRecipe(composed, consomables);

        double default_price = defaultRecipe.getPriceHT();
        double custom_price = customRecipe.getPriceHT();
        assertEquals(5, default_price);
        assertEquals(default_price, custom_price - default_price * CustomRecipe.MARGIN);
    }
}