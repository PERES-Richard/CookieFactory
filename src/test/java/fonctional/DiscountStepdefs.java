package fonctional;

import customer.Customer;
import discount.Discount;
import io.cucumber.java8.En;
import main.BDSingleton;
import order.Order;
import products.recipes.DefaultRecipe;
import store.Store;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountStepdefs implements En {

    BDSingleton singleton = BDSingleton.getSINGLETON();
    private Store store;
    private Customer customer;
    private Order order;
    private DefaultRecipe defaultRecipe;

    public DiscountStepdefs() {

        Given("^a store opens at \"([^\"]*)\" and closes at \"([^\"]*)\" every days, has a tax of \"([^\"]*)\"% and is located on \"([^\"]*)\"$", (String openTime, String closeTime, String taxValue, String address) -> {
            this.store = singleton.getStoreByAddress(address);
        });
        And("^the store on \"([^\"]*)\" sell one recipe of cookie$", (String storeAddress) -> {
            defaultRecipe = singleton.getDefaultRecipeByName("Touchoko");
            store.addStock(singleton.getIngredientsByName("Milk chocolate"), 1000);
            store.addStock(singleton.getIngredientsByName("Cinnamon"), 1000);
            store.addStock(singleton.getIngredientsByName("Chocolate"), 1000);
        });
        And("^a user named \"([^\"]*)\" who use \"([^\"]*)\" as email address$", (String customerName, String customerMail) -> {
            this.customer = new Customer(customerName, customerMail);
        });
        Given("^\"([^\"]*)\" is an adherent of the Loyalty Program$", (String customerName) -> {
            customer.register("password");
            customer.adhere();
        });
        And("^\"([^\"]*)\" have already ordered \"([^\"]*)\" cookies before$", (String customerName, Integer cookieNumber) -> {
            customer.setCounterOrder(cookieNumber);
        });
        Then("^\"([^\"]*)\" have a reduction about \"([^\"]*)\"% on his order$", (String arg0, Double discountValue) -> {
            Order order = customer.getOrders().get(0);
            double discounted = order.getPriceTTC() - order.getPriceTTC() * (discountValue / 100);
            assertEquals(discounted, order.getPriceDiscounted(), 0.1);
        });
        When("^\"([^\"]*)\" make a new order$", (String customerName) -> {
            Map<String, Integer> products = new HashMap<>();
            products.put(defaultRecipe.getName(), 1);
            customer.makeOrder(null, products, null, null, store.getAdress());
        });
        When("^\"([^\"]*)\" make a new order of \"([^\"]*)\" cookies and give the code \"([^\"]*)\"$", (String customerName, String cookieNumber, String code) -> {
            Map<String, Integer> products = new HashMap<>();
            products.put(defaultRecipe.getName(), Integer.parseInt(cookieNumber));
            customer.makeOrder(null, products, null, Collections.singletonList(code), store.getAdress());
        });
        When("^\"([^\"]*)\" make a new order and give the code \"([^\"]*)\"$", (String customerName, String code) -> {
            Discount discount = BDSingleton.getSINGLETON().getDiscountByCode(code);
            System.out.println("Discount value : " + discount.getValue());
            Map<String, Integer> products = new HashMap<>();
            products.put(defaultRecipe.getName(), 5);
            customer.makeOrder(null, products, null, Collections.singletonList(code), store.getAdress());
        });
        Then("^\"([^\"]*)\" have no reduction on his order$", (String custmerName) -> {
            Order order = customer.getOrders().get(0);
            assertEquals(order.getPriceTTC(), order.getPriceDiscounted(), 0.1);
        });
    }

}
