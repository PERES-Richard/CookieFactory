package fonctional;

import cucumber.api.java8.En;
import customer.Customer;
import main.BDSingleton;
import order.OrderStatutState;
import products.recipes.DefaultRecipe;
import store.Store;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayOrderStepdefs implements En {

    public Customer customer;
    DefaultRecipe cookie1;
    String storeAddress;
    BDSingleton singleton = BDSingleton.getSINGLETON();
    private Store store;


    public PayOrderStepdefs() {


        Given("^A  \"([^\"]*)\" store$", (String arg0) -> {

            storeAddress = arg0;

            store = singleton.getStoreByAddress(storeAddress);
        });


        And("^A client called \"([^\"]*)\" that uses the email \"([^\"]*)\"$", (String arg0, String arg1) -> {

            customer = new Customer(arg0, arg1);
        });


        And("^the client has an order$", () -> {


            cookie1 = singleton.getDefaultRecipeByName("Touchoko");
            store.addStock(singleton.getIngredientsByName("Milk chocolate"), 10);
            store.addStock(singleton.getIngredientsByName("Cinnamon"), 10);
            store.addStock(singleton.getIngredientsByName("Chocolate"), 10);
            Map<String, Integer> products = new HashMap<>();
            products.put(cookie1.getName(), 1);


            customer.makeOrder(null, products, null, null, store.getAdress());

        });


        When("^The order is done$", () -> {
            assertEquals(customer.getOrders().get(0).getState(), OrderStatutState.WAITINGFORPAYMENT);
        });


        Then("^The client is asked to pay$", () -> {
            customer.getOrders().get(0).requestPayment();
        });


        And("^after paying the sate of the order is now \"([^\"]*)\"$", (OrderStatutState arg0) -> {

            System.out.print(customer.getOrders().get(0).getState() + "\n");
            assertEquals(customer.getOrders().get(0).getState(), arg0);
        });


    }
}
