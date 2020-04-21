package fonctional;

import cucumber.api.java8.En;
import customer.Customer;
import main.BDSingleton;
import order.Order;
import products.Consomable;
import products.consomables.Ingredients;
import products.recipes.CookTechnique;
import products.recipes.DefaultRecipe;
import products.recipes.cookingTechnique.Mix;
import store.Store;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderStepDefs implements En {

    public Ingredients ingredient1;
    public Ingredients ingredient2;
    public Ingredients ingredient3;
    public CookTechnique ctech1;
    public CookTechnique ctech2;
    public Mix mix1;
    public HashMap<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> cc = new HashMap<>();
    public HashMap<Map<CookTechnique, Integer>, Map<Consomable, Integer>> cr = new HashMap<>();
    public Customer getCustomer2;
    Store store2;
    Store store;
    String storeAddress;
    String storeAddress2;
    String customerName;
    Customer customer2;
    DefaultRecipe cookie1;
    DefaultRecipe cookie3;
    Map<Consomable, Integer> stockOldAmounts;
    Map<Consomable, Integer> stockOldAmounts2;
    List<String> order;
    int customerOldNumberOfOrder;
    int customerOldNumberOfOrder2;
    int storeOldNumberOfOrder;
    int storeOldNumberOfOrder2;
    BDSingleton singleton = BDSingleton.getSINGLETON();


    public OrderStepDefs() {

        Given("^An anonymous user called \"([^\"]*)\" that uses the email \"([^\"]*)\"$", (String arg0, String arg1) -> {
            customerName = arg0;
            singleton.add(new Customer(arg0, arg1));

            assert (singleton.getCustomerByName(arg0) != null);
        });

        And("^a store located in \"([^\"]*)\"$", (String arg0) -> {
            storeAddress = arg0;
            store = singleton.getStoreByAddress(storeAddress);
        });

        And("^two cookies recepes called \"([^\"]*)\" and \"([^\"]*)\"$", (String arg0, String arg1) -> {
            assert (singleton.getDefaultRecipeByName(arg0) != null);
            assert (singleton.getDefaultRecipeByName(arg1) != null);

            order = Arrays.asList(arg0, arg1);
        });


        // Scenario 1


        When("^Add stock of (\\d+) \"([^\"]*)\" and (\\d+) \"([^\"]*)\" at store \"([^\"]*)\"$", (Integer arg0, String arg1, Integer arg2, String arg3, String arg4) -> {
            Store store = singleton.getStoreByAddress(arg4);
            DefaultRecipe recipe1 = singleton.getDefaultRecipeByName(arg1);
            DefaultRecipe recipe2 = singleton.getDefaultRecipeByName(arg3);
            stockOldAmounts = new HashMap<>();


            for (Consomable consomable : recipe1.getConsomableToUse().keySet()) {
                stockOldAmounts.putIfAbsent(consomable, store.getAmountOf(consomable));
            }
            for (Consomable consomable : recipe2.getConsomableToUse().keySet()) {
                stockOldAmounts.computeIfPresent(consomable, (key, value) -> value += store.getAmountOf(consomable));
                stockOldAmounts.putIfAbsent(consomable, store.getAmountOf(consomable));
            }

            for (Map.Entry<Consomable, Integer> entry : recipe1.getConsomableToUse().entrySet()) {
                store.addStock(entry.getKey(), entry.getValue() * arg0);
            }

            for (Map.Entry<Consomable, Integer> entry : recipe2.getConsomableToUse().entrySet()) {
                store.addStock(entry.getKey(), entry.getValue() * arg2);
            }

        });

        And("^The client \"([^\"]*)\" makes an order of (\\d+) \"([^\"]*)\" and (\\d+) \"([^\"]*)\" at store \"([^\"]*)\"$",
                (String clientName, Integer qteOrder1, String recipeOrder1, Integer qteOrder2, String recipeOrder2, String storeAddress) -> {
                    customerOldNumberOfOrder = singleton.getCustomerByName(clientName).getOrders().size();
                    storeOldNumberOfOrder = singleton.getStoreByAddress(storeAddress).getOrders().size();

                    HashMap<String, Integer> defaultCookieOrder = new HashMap<>();
                    defaultCookieOrder.put(recipeOrder1, qteOrder1);
                    defaultCookieOrder.put(recipeOrder2, qteOrder2);

                    singleton.getCustomerByName(clientName).makeOrder(null, defaultCookieOrder, null, null, storeAddress);
                });

        Then("^The client \"([^\"]*)\" has (\\d+) additional order$", (String arg0, Integer arg1) -> {
            assertEquals(customerOldNumberOfOrder + arg1, singleton.getCustomerByName(arg0).getOrders().size());
        });

        And("^the store \"([^\"]*)\" has (\\d+) more order to make$", (String arg0, Integer arg1) -> {
            assertEquals(storeOldNumberOfOrder + arg1, singleton.getStoreByAddress(arg0).getOrders().size());
        });

        And("^\"([^\"]*)\"'s last order is the same as last \"([^\"]*)\"'s store order$", (String arg0, String arg1) -> {
            assertEquals(singleton.getCustomerByName(arg0).getOrders().get(customerOldNumberOfOrder),
                    singleton.getStoreByAddress(arg1).getOrders().get(storeOldNumberOfOrder));
        });

        And("^the total price of this order is now \"([^\"]*)\" euros HT and \"([^\"]*)\" TTC for client \"([^\"]*)\"$", (String arg0, String arg1, String arg2) -> {
            Order order = singleton.getCustomerByName(arg2).getOrders().get(customerOldNumberOfOrder);

            assertEquals(order.getPriceHT(), Double.parseDouble(arg0), 0.1);
            assertEquals(order.getPriceTTC(), Double.parseDouble(arg1), 0.1);
        });

        And("^\"([^\"]*)\"'store stocks is the same as before the order for consomables needed in recipes \"([^\"]*)\" and \"([^\"]*)\"$", (String arg0, String arg1, String arg2) -> {
            DefaultRecipe recipe1 = singleton.getDefaultRecipeByName(arg1);
            DefaultRecipe recipe2 = singleton.getDefaultRecipeByName(arg2);

            for (Consomable consomable : recipe1.getConsomableToUse().keySet()) {
                assertEquals(stockOldAmounts.get(consomable), singleton.getStoreByAddress(arg0).getAmountOf(consomable));
            }
            for (Consomable consomable : recipe2.getConsomableToUse().keySet()) {
                assertEquals(stockOldAmounts.get(consomable), singleton.getStoreByAddress(arg0).getAmountOf(consomable));
            }
        });

        //Scenario 2:

        Given("^An registered user called \"([^\"]*)\" that uses the email \"([^\"]*)\" and password \"([^\"]*)\"$", (String arg0, String arg1, String arg2) -> {
            customer2 = new Customer(arg0, arg1);
            customer2.register(arg2);
            customerOldNumberOfOrder2 = customer2.getOrders().size();
        });

        And("^a store that's located in \"([^\"]*)\"$", (String arg0) -> {

            storeAddress2 = arg0;

            store2 = singleton.getStoreByAddress(storeAddress2);


            storeOldNumberOfOrder2 = store2.getOrders().size();
        });
        And("^One cookie recepe called \"([^\"]*)\"$", (String arg0) -> {

            cookie3 = singleton.getDefaultRecipeByName(arg0);
        });
        When("^The user makes an order of (\\d+) \"([^\"]*)\"$", (Integer arg0, String arg1) -> {
            stockOldAmounts2 = new HashMap<>();
            for (Map.Entry<Consomable, Integer> c : cookie3.getConsomableToUse().entrySet()) {
                stockOldAmounts2.putIfAbsent(c.getKey(), store2.getAmountOf(c.getKey()));
//                if (!enough3) TODO
                store2.addStock(c.getKey(), c.getValue() * arg0);
            }

            Map<String, Integer> products3 = new HashMap<>();
            products3.put(cookie3.getName(), arg0);


            customer2.makeOrder(null, products3, null, null, store2.getAdress());

        });
        Then("^Now the client has one additional order$", () -> {
            assertEquals(customerOldNumberOfOrder2 + 1, customer2.getOrders().size());
        });
        And("^the total price of this order is now \"([^\"]*)\" € HT and \"([^\"]*)\" TTC$", (Double arg0, Double arg1) -> {
            Order orderCust2 = customer2.getOrders().get(customerOldNumberOfOrder2);
            Order orderStore2 = store2.getOrders().get(storeOldNumberOfOrder2);

            assert (orderCust2.equals(orderStore2));
            assertEquals(arg0, orderCust2.getPriceHT(), 0.01);
            assertEquals(arg1, orderStore2.getPriceTTC(), 0.01);
        });
        And("^the store has one more order in pending to make$", () -> {
            assertEquals(storeOldNumberOfOrder2 + 1, store2.getOrders().size());
        });
        And("^It's stock is was used$", () -> {

            for (Map.Entry<Consomable, Integer> c : cookie3.getConsomableToUse().entrySet()) {
                assertEquals(store2.getAmountOf(c.getKey()), stockOldAmounts2.get(c.getKey()));
            }

        });


        //Scenario 3


        And("^a user called \"([^\"]*)\" that goes with the email \"([^\"]*)\"$", (String arg0, String arg1) -> {
            customer2 = new Customer(arg0, arg1);
        });

        When(("^the user creates his custom recepe composed of \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\" and is \"([^\"]*)\" and \"([^\"]*)\"$"), (String arg0, String arg1, String arg2, String arg3, String arg4) -> {

            store.addStock(singleton.getIngredientsByName(arg0), 3);
            store.addStock(singleton.getIngredientsByName(arg1), 3);
            store.addStock(singleton.getIngredientsByName(arg2), 3);


            HashMap<CookTechnique, Integer> cooktec = new HashMap<CookTechnique, Integer>() {{
                put(singleton.getCookTechniqueByName(arg3), 1);
                put(singleton.getCookTechniqueByName(arg4), 1);
            }};
            HashMap<Consomable, Integer> conso = new HashMap<Consomable, Integer>() {{
                put(singleton.getIngredientsByName(arg0), 1);
                put(singleton.getIngredientsByName(arg1), 1);
                put(singleton.getIngredientsByName(arg2), 1);
            }};

            cr.put(cooktec, conso);

            cc.put(cr.entrySet().iterator().next(), 2);

        });

        Then("^the user makes the order of his custom made cookies$", () -> {
            storeOldNumberOfOrder = store.getOrders().size();
            customer2.makeOrder(null, null, cc, null, store.getAdress());
        });
        And("^the store starts making the order$", () -> {
            assertEquals(storeOldNumberOfOrder + 1, store.getOrders().size());
        });

    }

}
