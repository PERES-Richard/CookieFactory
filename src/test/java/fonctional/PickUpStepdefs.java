package fonctional;

import cucumber.api.java8.En;
import customer.Customer;
import main.BDSingleton;
import order.OrderStatutState;
import products.consomables.Ingredients;
import products.recipes.CookTechnique;
import products.recipes.DefaultRecipe;
import products.recipes.cookingTechnique.Mix;
import store.Store;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PickUpStepdefs implements En {


    public HashMap<DayOfWeek, Integer[]> s1 = new HashMap<DayOfWeek, Integer[]>();
    public Customer customer;
    public Ingredients ingredient1;
    public Ingredients ingredient2;
    public Ingredients ingredient3;
    public CookTechnique ctech1;
    public CookTechnique ctech2;
    public Mix mix1;
    DefaultRecipe cookie1;
    String storeAddress;
    BDSingleton singleton = BDSingleton.getSINGLETON();
    private Store store;


    public PickUpStepdefs() {


        Given("^A store is located in \"([^\"]*)\"$", (String arg0) -> {

            storeAddress = arg0;

            store = singleton.getStoreByAddress(storeAddress);

        });


        And("^A client \"([^\"]*)\" that uses the email \"([^\"]*)\"$", (String arg0, String arg1) -> {
            customer = new Customer(arg0, arg1);
        });


        And("^the client has an order that is ready to pick up$", () -> {

            cookie1 = singleton.getDefaultRecipeByName("Touchoko");
            store.addStock(singleton.getIngredientsByName("Milk chocolate"), 10);
            store.addStock(singleton.getIngredientsByName("Cinnamon"), 10);
            store.addStock(singleton.getIngredientsByName("Chocolate"), 10);
            Map<String, Integer> products = new HashMap<>();
            products.put(cookie1.getName(), 1);


            customer.makeOrder(null, products, null, null, store.getAdress());
        });


        When("^the client makes the payment and picks the order up$", () -> {
            customer.getOrders().get(0).requestPayment();
            customer.getOrders().get(0).pickUp();
        });
        Then("^the order is now finished and it's state is \"([^\"]*)\"$", (OrderStatutState arg0) -> {
            assertEquals(customer.getOrders().get(0).getState(), arg0);
        });


    }
}
