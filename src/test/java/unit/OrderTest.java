package unit;

import customer.Customer;
import discount.fixed.FixedDiscount;
import discount.percentages.PercentageDiscount;
import discount.percentages.concrete.DayOffDiscount;
import discount.percentages.concrete.FidelityDscount;
import discount.percentages.concrete.LoyaltyDiscount;
import exceptions.CannotPickUPOrderNotReadyException;
import main.BDSingleton;
import order.Order;
import order.OrderStatutState;
import order.items.CookieOrder;
import order.items.ProductOrder;
import order.items.SimpleCookieOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import products.Consomable;
import products.Product;
import products.consomables.Ingredients;
import products.recipes.CookTechnique;
import products.recipes.CustomRecipe;
import products.recipes.Recipe;
import products.recipes.cookingTechnique.Mix;
import store.Store;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class OrderTest {

    public Customer customer;
    public HashMap<Recipe, Integer> orderItems;
    public Store store;
    public Store store2;
    public HashMap<DayOfWeek, Integer[]> s1 = new HashMap<DayOfWeek, Integer[]>();
    public Ingredients ingredient1;
    public Ingredients ingredient2;
    public Ingredients ingredient3;
    public CookTechnique ctech1;
    public CookTechnique ctech2;
    public Mix mix1;
    List<ProductOrder> commande = new ArrayList<ProductOrder>();
    List<ProductOrder> commande2 = new ArrayList<ProductOrder>();
    Order order;
    Order order2;

    @BeforeEach
    void setUp() {
        customer = new Customer("Test", "test.tesuto@gmail.com");

        BDSingleton singleton = BDSingleton.getSINGLETON();

        store = singleton.getStoreByAddress("Tokyo");
        store2 = singleton.getStoreByAddress("Osaka");

        ingredient1 = singleton.getIngredientsByName("CHOCOLATE");
        ingredient2 = singleton.getIngredientsByName("CINNAMON");
        ingredient3 = singleton.getIngredientsByName("Cinnamon");

        ctech1 = singleton.getCookTechniqueByName("CHEWY");

        ctech2 = singleton.getCookTechniqueByName("CRUNCHY");

        mix1 = new Mix("TOOPED", 1.8);

        store.addStock(ingredient1, 20);
        store.addStock(ingredient2, 20);
        store.addStock(ingredient3, 20);

        store2.addStock(ingredient1, 20);
        store2.addStock(ingredient2, 20);
        store2.addStock(ingredient3, 20);

        orderItems = new HashMap<>();
        Map<Product, Integer> tech = new HashMap<Product, Integer>();
        tech.put(ctech1, 1);
        tech.put(mix1, 1);
        tech.put(ingredient1, 1);
        tech.put(ingredient2, 1);
        tech.put(ingredient3, 1);

        Map<Consomable, Integer> consos = new HashMap<>();
        consos.put(ingredient1, 1);
        consos.put(ingredient2, 1);
        consos.put(ingredient3, 1);


        CustomRecipe recipe = new CustomRecipe(tech, consos);


        Map<Product, Integer> tech2 = new HashMap<Product, Integer>();
        tech2.put(ctech2, 1);
        tech2.put(mix1, 1);
        tech2.put(ingredient1, 1);
        tech2.put(ingredient2, 1);
        tech2.put(ingredient3, 1);

        Map<Consomable, Integer> consos2 = new HashMap<>();
        consos2.put(ingredient1, 1);
        consos2.put(ingredient2, 1);
        consos2.put(ingredient3, 1);

        CustomRecipe recipe2 = new CustomRecipe(tech2, consos2);


        CookieOrder cOrder = new SimpleCookieOrder(recipe, 3);
        CookieOrder cOrder2 = new SimpleCookieOrder(recipe2, 2);

        CookieOrder cOrder3 = new SimpleCookieOrder(recipe, 5);
        CookieOrder cOrder4 = new SimpleCookieOrder(recipe2, 1);

        commande.add(cOrder);
        commande.add(cOrder2);

        commande2.add(cOrder3);
        commande2.add(cOrder4);

        List<PercentageDiscount> discounts = new ArrayList<>();
        discounts.add(new DayOffDiscount()); // 30% sur tout sauf les boissons et les cookies custom
        List<FixedDiscount> coupons = new ArrayList<>();

        List<PercentageDiscount> discounts2 = new ArrayList<>();
        discounts2.add(new LoyaltyDiscount()); // 10% sur tout les produit commandé
        discounts2.add(new FidelityDscount(5)); // 5% sur tout les produit commandé
        List<FixedDiscount> coupons2 = new ArrayList<>();

        order = new Order(commande, store, customer, discounts, coupons);
        order2 = new Order(commande2, store2, customer, discounts2, coupons2);
    }

    @Test
    void test_price_calculation() {

    }

    @Test
    void calculatePriceIT() {
        Double price1 = order.getPriceTTC();
        Double price2 = order2.getPriceTTC();

        assertEquals(94.86, price1, 0.01);
        assertEquals(99.52, price2, 0.01);
    }

    @Test
    void calculatePriceET() {

        Double price1 = order.getPriceHT();
        Double price2 = order2.getPriceHT();

        assertEquals(63.24, price1, 0.01);
        assertEquals(76.56, price2, 0.01);
    }


    @Test
    void waitingForPayement() {
        assertEquals(order.getState(), OrderStatutState.WAITINGFORPAYMENT);
        assertEquals(order2.getState(), OrderStatutState.WAITINGFORPAYMENT);
    }

    @Test
    void pickUp() {
        order.requestPayment();
        assertEquals(order.getState(), OrderStatutState.WAITINGFORPICKUP);
        assertNotEquals(order2.getState(), OrderStatutState.WAITINGFORPICKUP);
    }

    @Test
    void requestPayment() {
        order.requestPayment();
        try {
            order.pickUp();
        } catch (CannotPickUPOrderNotReadyException e) {
            e.printStackTrace();
        }

        assertEquals(order.getState(), OrderStatutState.DELIVERED);


        try {
            order2.pickUp();
        } catch (CannotPickUPOrderNotReadyException e) {
            e.printStackTrace();
        }

        assertNotEquals(order2, OrderStatutState.DELIVERED);
    }
}