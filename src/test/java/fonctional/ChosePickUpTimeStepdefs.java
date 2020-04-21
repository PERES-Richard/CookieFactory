package fonctional;

import cucumber.api.java8.En;
import customer.Customer;
import exceptions.CannotOrderStoreWillBeClosedException;
import main.BDSingleton;
import order.OrderStatutState;
import order.items.ProductOrder;
import products.consomables.Ingredients;
import products.recipes.CookTechnique;
import products.recipes.DefaultRecipe;
import products.recipes.cookingTechnique.Mix;
import store.Store;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChosePickUpTimeStepdefs implements En {


    public HashMap<DayOfWeek, Integer[]> s1 = new HashMap<DayOfWeek, Integer[]>();
    public Customer customer;
    public Customer customer2;
    public Ingredients ingredient1;
    public Ingredients ingredient2;
    public Ingredients ingredient3;
    public CookTechnique ctech1;
    public CookTechnique ctech2;
    public Mix mix1;
    public String exception;
    DefaultRecipe cookie1;


    List<ProductOrder> commande = new ArrayList<ProductOrder>();


    BDSingleton singleton = BDSingleton.getSINGLETON();
    private Store store;
    private DefaultRecipe defaultRecipe;
    private DefaultRecipe defaultRecipe2;


    public ChosePickUpTimeStepdefs() {


        Given("^A \"([^\"]*)\" store that opens at \"([^\"]*)\" and closes at \"([^\"]*)\"$", (String arg0, Integer arg1, Integer arg2) -> {


            store = singleton.getStoreByAddress(arg0);

        });


        And("^two clients called \"([^\"]*)\" that uses the email address \"([^\"]*)\"$", (String arg0, String arg1) -> {

            customer = new Customer(arg0, arg1);

        });


        And("^one called \"([^\"]*)\" that uses the email address \"([^\"]*)\"$", (String arg0, String arg1) -> {

            customer2 = new Customer(arg0, arg1);
        });


        When("^The customer want to make an order in the \"([^\"]*)\" store, he decides to pick it up at \"([^\"]*)\"$", (String arg0, Integer arg1) -> {

            defaultRecipe = singleton.getDefaultRecipeByName("Touchoko");
            store.addStock(singleton.getIngredientsByName("Milk chocolate"), 10);
            store.addStock(singleton.getIngredientsByName("Cinnamon"), 10);
            store.addStock(singleton.getIngredientsByName("Chocolate"), 10);

            Map<String, Integer> products = new HashMap<>();
            products.put(defaultRecipe.getName(), 1);
            customer.makeOrderwithtime(null, products, null, null, store.getAdress(), arg1);

        });


        Then("^the order is correctly made without an issue$", () -> {
            assertEquals(customer.getOrders().size(), 1);
        });


        And("^the order's state is \"([^\"]*)\"$", (OrderStatutState arg0) -> {

            assertEquals(customer.getOrders().get(0).getState(), arg0);
        });


//Scenario 2

        When("^The customer wants to make an order in the \"([^\"]*)\" store, he decides to pick it up at \"([^\"]*)\"$", (String arg0, Integer arg1) -> {
            defaultRecipe2 = singleton.getDefaultRecipeByName("Touchoko");
            store.addStock(singleton.getIngredientsByName("Milk chocolate"), 10);
            store.addStock(singleton.getIngredientsByName("Cinnamon"), 10);
            store.addStock(singleton.getIngredientsByName("Chocolate"), 10);


            Map<String, Integer> products2 = new HashMap<>();
            products2.put(defaultRecipe2.getName(), 1);
            try {
                customer2.makeOrderwithtime(null, products2, null, null, store.getAdress(), arg1);
            } catch (CannotOrderStoreWillBeClosedException e) {
                exception = e.getMessage();
            }
        });
        Then("^the order is not made$", () -> {

            assertEquals(customer.getOrders().size(), 0);
        });
        And("^the system tells the customer that it \"([^\"]*)\"$", (String arg0) -> {
            assertEquals(exception, arg0);

        });


    }


}
