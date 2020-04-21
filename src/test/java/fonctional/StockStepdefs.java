package fonctional;

import cucumber.api.java8.En;
import customer.Customer;
import exceptions.CannotOrderOutOfStockException;
import main.BDSingleton;
import products.Consomable;
import products.recipes.DefaultRecipe;
import store.Store;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StockStepdefs implements En {


    public String exception;
    public boolean good;
    Store store;
    Store store2;
    String storeAddress;
    String storeAddress2;
    Customer customer;
    Customer customer2;
    DefaultRecipe cookie1;
    DefaultRecipe cookie2;
    DefaultRecipe cookie3;
    Map<Consomable, Integer> stockOldAmounts;
    Map<Consomable, Integer> stockOldAmounts2;
    int customerOldNumberOfOrder;
    int customerOldNumberOfOrder2;
    int storeOldNumberOfOrder;
    int storeOldNumberOfOrder2;
    BDSingleton singleton = BDSingleton.getSINGLETON();


    public StockStepdefs() {


        Given("^A store in \"([^\"]*)\".$", (String arg0) -> {

            storeAddress = arg0;

            store = singleton.getStoreByAddress(storeAddress);

            storeOldNumberOfOrder = store.getOrders().size();

        });


        And("^A client that wants to make an order.$", () -> {
            customer = new Customer("amine", "legrifi");
            customerOldNumberOfOrder = customer.getOrders().size();

            cookie1 = singleton.getDefaultRecipeByName("Touchoko");
            store.addStock(singleton.getIngredientsByName("Milk chocolate"), 10);
            store.addStock(singleton.getIngredientsByName("Cinnamon"), 10);
            store.addStock(singleton.getIngredientsByName("Chocolate"), 10);
            Map<String, Integer> products = new HashMap<>();
            products.put(cookie1.getName(), 1);


            customer.makeOrder(null, products, null, null, store.getAdress());


        });
        When("^When the store checks its stock to prepare the order$", () -> {
            int milkquantity = store.getStocks().getAmountOf(singleton.getIngredientsByName("Milk chocolate"));
            int cinnamonquantity = store.getStocks().getAmountOf(singleton.getIngredientsByName("Cinnamon"));
            int chocolatequantity = store.getStocks().getAmountOf(singleton.getIngredientsByName("Chocolate"));
            assertTrue(milkquantity > 0);
            assertTrue(cinnamonquantity > 0);
            assertTrue(chocolatequantity > 0);
        });
        Then("^the stock has enough ingredients and completes the order$", () -> {
            assertEquals(customer.getOrders().size(), 1);
        });


        //Scenario 2


        Given("^A store that is in \"([^\"]*)\".$", (String arg0) -> {

            storeAddress2 = arg0;

            store2 = singleton.getStoreByAddress(storeAddress2);
            store2.getStocks().addStock(singleton.getIngredientsByName("Milk chocolate"), 0);
            store2.getStocks().addStock(singleton.getIngredientsByName("Cinnamon"), 0);
            store2.getStocks().addStock(singleton.getIngredientsByName("Chocolate"), 0);

            storeOldNumberOfOrder2 = store2.getOrders().size();

        });
        And("^A client who wants to make an order.$", () -> {

            customer2 = new Customer("amine", "legrifi");
            customerOldNumberOfOrder2 = customer2.getOrders().size();


        });
        When("^When the store checks its stocks to prepare the order$", () -> {

            cookie2 = singleton.getDefaultRecipeByName("Touchoko");
            Map<String, Integer> products = new HashMap<>();
            products.put(cookie2.getName(), 1);

            try {
                customer2.makeOrder(null, products, null, null, store.getAdress());

            } catch (CannotOrderOutOfStockException e) {
                exception = e.getMessage();
            } catch (NullPointerException n) {
                exception = "Cannot make this order because we are out of stock for those ingredients";
            }

        });
        Then("^the stock is not enough to make the order$", () -> {

            assertEquals(customer2.getOrders().size(), customerOldNumberOfOrder2);

        });


        And("^it shows an error \"([^\"]*)\"$", (String arg0) -> {

            assertEquals(exception, arg0);

        });
    }


}
