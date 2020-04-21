package fonctional;

import cucumber.api.java8.En;
import main.BDSingleton;
import order.items.OrderFactory;
import order.items.ProductOrder;
import products.consomables.Drink;
import store.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PackStepdefs implements En {


    List<ProductOrder> orders = new ArrayList<>();
    BDSingleton singleton = BDSingleton.getSINGLETON();
    Store store;
    private OrderFactory of = new OrderFactory();

    public PackStepdefs() {


        Given("^A store in \"([^\"]*)\"$", (String arg0) -> {
            store = singleton.getStoreByAddress(arg0);
        });


        When("^a large order is made of \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\"$", (Integer arg0, String arg1, Integer arg2, String arg3) -> {
            store.addStock(singleton.getDefaultRecipeByName(arg1).getConsomableToUse());
            store.addStock(singleton.getDefaultRecipeByName(arg1).getConsomableToUse());
            HashMap<String, Integer> dc = new HashMap<String, Integer>() {{
                put(arg1, arg0);
            }};
            Drink drink = singleton.getDrinkByName(arg3);

            store.addStock(drink.getConsomableToUse());
            store.addStock(drink.getConsomableToUse());

            HashMap<String, Integer> dd = new HashMap<String, Integer>() {{
                put(arg3, arg2);
            }};
            orders = of.GetOrderList(dd, store, dc);
        });


        Then("^the orders are devided to a \"([^\"]*)\" pack and Drink_in_pack\" pack$", (String arg0) -> {

            assertTrue(this.orders.get(0).isApack);
            assertEquals(this.orders.get(0).typeDupack, "GRAND");
            assertTrue(this.orders.get(1).isApack);
            assertEquals(this.orders.get(1).typeDupack, "Drink_in_pack");
            assertFalse(this.orders.get(2).isApack);
            assertEquals(this.orders.get(2).typeDupack, "n'est pas un pack");
        });

    }

}
