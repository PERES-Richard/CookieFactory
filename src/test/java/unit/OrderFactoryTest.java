package unit;

import main.BDSingleton;
import order.items.OrderFactory;
import order.items.ProductOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import products.consomables.Drink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderFactoryTest {
    List<ProductOrder> orders = new ArrayList<>();
    BDSingleton singleton = BDSingleton.getSINGLETON();
    private OrderFactory of = new OrderFactory();

    @BeforeEach
    void setup() {
        singleton.getStoreByAddress("Hidafurukawa").addStock(singleton.getDefaultRecipeByName("mnms").getConsomableToUse());
        singleton.getStoreByAddress("Hidafurukawa").addStock(singleton.getDefaultRecipeByName("mnms").getConsomableToUse());
        HashMap<String, Integer> dc = new HashMap<String, Integer>() {{
            put("mnms", 10);
        }};
        Drink drink = singleton.getDrinkByName("Beer");

        singleton.getStoreByAddress("Hidafurukawa").addStock(drink.getConsomableToUse());
        singleton.getStoreByAddress("Hidafurukawa").addStock(drink.getConsomableToUse());

        HashMap<String, Integer> dd = new HashMap<String, Integer>() {{
            put("Beer", 2);
        }};
        orders = of.GetOrderList(dd, singleton.getStoreByAddress("Hidafurukawa"), dc);
    }

    @Test
    void test() {
        assertTrue(this.orders.get(0).isApack);
        assertEquals(this.orders.get(0).typeDupack, "GRAND");
        assertTrue(this.orders.get(1).isApack);
        assertEquals(this.orders.get(1).typeDupack, "Drink_in_pack");
        assertFalse(this.orders.get(2).isApack);
        assertEquals(this.orders.get(2).typeDupack, "n'est pas un pack");
    }
}
