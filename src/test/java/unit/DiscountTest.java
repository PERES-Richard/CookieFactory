package unit;

import customer.Customer;
import discount.Discount;
import discount.fixed.FixedDiscount;
import discount.fixed.concretes.CouponDiscount;
import discount.percentages.PercentageDiscount;
import discount.percentages.concrete.CustomDiscount;
import discount.percentages.concrete.DayOffDiscount;
import main.BDSingleton;
import order.Order;
import order.items.ProductOrder;
import order.items.SimpleCookieOrder;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountTest {

    private final BDSingleton singleton = BDSingleton.getSINGLETON();
    private Customer customer;
    private Store store;
    private List<PercentageDiscount> discounts = new ArrayList<>();
    private List<FixedDiscount> coupons = new ArrayList<>();
    private Order order;
    private DefaultRecipe defaultRecipe;
    private CustomRecipe customRecipe;
    private List<ProductOrder> orderItems = new ArrayList<>();

    @BeforeEach
    void setup() {
        // CUSTOMER INIT
        this.customer = new Customer("Customer", "customer@mail.com");

        // STORE INIT
        Integer[] hours = {10, 19};
        Map<DayOfWeek, Integer[]> workingHours = new HashMap<>();
        workingHours.put(DayOfWeek.MONDAY, hours);
        workingHours.put(DayOfWeek.TUESDAY, hours);
        workingHours.put(DayOfWeek.WEDNESDAY, hours);
        workingHours.put(DayOfWeek.THURSDAY, hours);
        workingHours.put(DayOfWeek.FRIDAY, hours);
        workingHours.put(DayOfWeek.SATURDAY, hours);
        workingHours.put(DayOfWeek.SUNDAY, hours);
        this.store = new Store("Store Adress", workingHours, 0.2);

        // STOCK INIT
        Drink drink = singleton.getDrinkByName("Coke");
        Flavour flavour = new Flavour("Flavour", 1);
        Dough dough = new Dough("Dough", 1);
        Topping topping = new Topping("Topping", 1);
        store.addStock(drink, 100);
        store.addStock(flavour, 100);
        store.addStock(dough, 100);
        store.addStock(topping, 100);

        // RECIPE INIT
        Mix mix = new Mix("Mix", 1);
        Cooking cooking = new Cooking("Cooking", 1);
        Map<Product, Integer> composed = new HashMap<>();
        composed.put(mix, 1);
        composed.put(cooking, 1);
        Map<Consomable, Integer> consomables = new HashMap<>();
        consomables.put(flavour, 1);
        consomables.put(dough, 1);
        consomables.put(topping, 1);
        this.defaultRecipe = new DefaultRecipe(composed, consomables, "default-recipe-test");
        this.customRecipe = new CustomRecipe(composed, consomables);

        this.orderItems = new ArrayList<>();
    }

    @Test
    void test_dayOff_discount_application() {
        double priceHT;
        double priceTTC;
        double priceDiscounted;

        orderItems = new ArrayList<>();

        int drinkQuantity = 10;
        Drink drink = singleton.getDrinkByName("Coke");
        int drinkPrice = (int) drink.getPriceTTC();
        store.addStock(drink, 100);
        discounts.add(new DayOffDiscount());
//        orderItems.add(new ProductOrder(drink, drinkQuantity, store.getTax())); TODO
        order = new Order(orderItems, store, customer, discounts, coupons);

        priceHT = drinkPrice * drinkQuantity;
        priceTTC = priceHT + priceHT * store.getTax();
        priceDiscounted = priceTTC;

//        assertEquals(priceHT, order.getPriceHT()); TODO
//        assertEquals(priceTTC, order.getPriceTTC()); TODO
//        assertEquals(priceDiscounted, order.getPriceDiscounted()); TODO

        orderItems.clear();
        discounts.clear();
        coupons.clear();

        int cookiePrice = 5;
        int cookieQuantity = 10;
        double customRecipeMargin = CustomRecipe.MARGIN;

        Flavour flavour = new Flavour("Flavour", 1);
        store.addStock(flavour, 100);
        Dough dough = new Dough("Dough", 1);
        store.addStock(dough, 100);
        Topping topping = new Topping("Topping", 1);
        store.addStock(topping, 100);
        Mix mix = new Mix("Mix", 1);
        Cooking cooking = new Cooking("Cooking", 1);
        Map<Product, Integer> composed = new HashMap<>();
        composed.put(mix, 1);
        composed.put(cooking, 1);
        Map<Consomable, Integer> consomables = new HashMap<>();
        consomables.put(flavour, 1);
        consomables.put(dough, 1);
        consomables.put(topping, 1);
        CustomRecipe recipe = new CustomRecipe(composed, consomables);

        discounts.add(new DayOffDiscount());
//        orderItems.add(new SimpleCookieOrder(recipe, cookieQuantity, store.getTax())); TODO
        order = new Order(orderItems, store, customer, discounts, coupons);

        priceHT = 0;
        for (int i = 0; i < cookieQuantity; ++i) {
            priceHT += cookiePrice;
        }
        priceHT += priceHT * customRecipeMargin;
        priceTTC = priceHT + priceHT * store.getTax();
        priceDiscounted = priceTTC;

//        assertEquals(priceHT, order.getPriceHT()); TODO
//        assertEquals(priceTTC, order.getPriceTTC()); TODO
//        assertEquals(priceDiscounted, order.getPriceDiscounted()); TODO
    }

    @Test
    void test_common_discount_application() {
        double priceHT;
        double priceTTC;
        double priceDiscounted = 0;

        List<ProductOrder> items = new ArrayList<>();
        List<PercentageDiscount> discounts = new ArrayList<>();
        List<FixedDiscount> coupons = new ArrayList<>();


        int drinkQuantity = 10;
        Drink drink = singleton.getDrinkByName("Coke");
        int drinkPrice = (int) drink.getPriceHT();
        store.addStock(drink, 100);
        discounts.add(new CustomDiscount(0.1, "001"));
//        items.add(new ProductOrder(drink, drinkQuantity, store.getTax())); TODO
        Order order = new Order(items, store, customer, discounts, coupons);

        priceHT = drinkPrice * drinkQuantity;
        priceTTC = priceHT + priceHT * store.getTax();
        for (Discount discount : discounts) {
            priceDiscounted = priceTTC - priceTTC * discount.getValue();
        }
//        assertEquals(priceHT, order.getPriceHT()); TODO
//        assertEquals(priceTTC, order.getPriceTTC()); TODO
//        assertEquals(priceDiscounted, order.getPriceDiscounted()); TODO
    }

    @Test
    void test_recipe_price_computation() {
        assertEquals(5, defaultRecipe.getPriceHT());
        assertEquals(6, customRecipe.getPriceHT());
    }

    @Test
    void test_order_prices_computations() {
        this.coupons.add(new CouponDiscount(0.8, "002"));
        this.discounts.add(new CustomDiscount(0.1, "003"));
        Order order = new Order(orderItems, store, customer, discounts, coupons);
        double price = 10;
        double priceTTC = price + price * 0.2;
        double discounted_price = 0;
        for (ProductOrder item : orderItems) {
            for (Discount discount : discounts) {
                discounted_price += item.getDiscountedPrice(discount);
            }
        }
        for (Discount discount : coupons) {
            discounted_price = discount.applyDiscountOn(discounted_price);
        }
//        assertEquals(price, order.getPriceHT()); TODO
//        assertEquals(priceTTC, order.getPriceTTC()); TODO
        assertEquals(discounted_price, order.getPriceDiscounted());

        this.orderItems.clear();
        this.orderItems.add(new SimpleCookieOrder(defaultRecipe, 1));
        this.orderItems.add(new SimpleCookieOrder(defaultRecipe, 1));
        this.coupons.add(new CouponDiscount(0.8, "004"));
        this.discounts.add(new CustomDiscount(0.1, "005"));
        this.order = new Order(orderItems, store, customer, discounts, coupons);
        price = 10;
        priceTTC = price + price * 0.2;
        discounted_price = 0;
        for (ProductOrder item : orderItems) {
            for (Discount discount : discounts) {
                discounted_price += item.getDiscountedPrice(discount);
            }
        }
        for (Discount discount : coupons) {
            discounted_price = discount.applyDiscountOn(discounted_price);
        }
//        assertEquals(price, order.getPriceHT()); TODO
//        assertEquals(priceTTC, order.getPriceTTC()); TODO
//        assertEquals(discounted_price, order.getPriceDiscounted()); TODO
        this.coupons.add(new CouponDiscount(0.8, "006"));
        this.discounts.add(new CustomDiscount(0.1, "007"));
    }

//    @Test
//    void test_drink_discount() {
//        double price = 1;
//        Drink drink = new Drink(price);
//        DayOffDiscount dayOff = new DayOffDiscount();
//        CouponDiscount coupon = new CouponDiscount(0.5);
//        LoyaltyDiscount loyalty = new LoyaltyDiscount();
//        assertEquals(drink.getPrice(), drink.getDiscountedPrice(dayOff, drink.getPrice()));
//        assertEquals(price-coupon.getValue(), drink.getDiscountedPrice(coupon, drink.getPrice()));
//        assertEquals(price-(price*loyalty.getValue()), drink.getDiscountedPrice(loyalty, drink.getPrice()));
//    }

    @Test
    void test_recipe_discount() {
        DayOffDiscount dayOff = new DayOffDiscount();
        CouponDiscount coupon = new CouponDiscount(0.5, "008");
        CustomDiscount discount = new CustomDiscount(0.1, "009");

        double price = defaultRecipe.getPriceHT();
        double discounted_price = defaultRecipe.getDiscountedPrice(discount, price);
        assertEquals(price - price * discount.getValue(), discounted_price);
        assertEquals(price, defaultRecipe.getPriceHT());
        discounted_price = defaultRecipe.getDiscountedPrice(coupon, price);
        assertEquals(price - coupon.getValue(), discounted_price);
        assertEquals(price, defaultRecipe.getPriceHT());
        discounted_price = defaultRecipe.getDiscountedPrice(dayOff, price);
        assertEquals(price - price * dayOff.getValue(), discounted_price);
        assertEquals(price, defaultRecipe.getPriceHT());

        price = customRecipe.getPriceHT();
        discounted_price = customRecipe.getDiscountedPrice(discount, price);
        assertEquals(price - price * discount.getValue(), discounted_price);
        assertEquals(price, customRecipe.getPriceHT());
        discounted_price = customRecipe.getDiscountedPrice(coupon, price);
        assertEquals(price - coupon.getValue(), discounted_price);
        assertEquals(price, customRecipe.getPriceHT());
//        discounted_price = customRecipe.getDiscountedPrice(dayOff); TODO
//        assertEquals(price, discounted_price); TODO
        assertEquals(price, customRecipe.getPriceHT());
    }
}
