package main;

import customer.Customer;
import discount.Discount;
import discount.percentages.concrete.CompanyDiscount;
import discount.percentages.concrete.EventDiscount;
import discount.percentages.concrete.LoyaltyDiscount;
import products.Consomable;
import products.Product;
import products.ProductFactory;
import products.consomables.Drink;
import products.consomables.Ingredients;
import products.consomables.ingredients.Dough;
import products.consomables.ingredients.Flavour;
import products.consomables.ingredients.Topping;
import products.recipes.CookTechnique;
import products.recipes.DefaultRecipe;
import products.recipes.cookingTechnique.Cooking;
import products.recipes.cookingTechnique.Mix;
import store.Store;

import java.time.DayOfWeek;
import java.util.*;

public final class BDSingleton {

    private static final BDSingleton SINGLETON = new BDSingleton();
    private List<Store> stores;
    private List<Customer> customers;
    private List<DefaultRecipe> defaultRecipes;
    private List<Ingredients> ingredients;
    private List<CookTechnique> cookTechniques;
    private List<Drink> drinks;
    private List<Discount> discounts;

    private BDSingleton() {
        loadAll();
    }

    public static BDSingleton getSINGLETON() {
        return SINGLETON;
    }

    private void loadAll() {
        //TODO compelte
        loadStores();
        loadCustomers();
        loadIngredients();
        loadCookTechnique();
        loadDefaultRecipes();
        loadDiscounts();
        loadDrink();
    }

    /**
     * ##########  LOADS ########
     **/

    private void loadStores() {
        stores = new ArrayList<>();

        HashMap<DayOfWeek, Integer[]> wh = new HashMap<>();

        wh.put(DayOfWeek.MONDAY, new Integer[]{9, 16});
        wh.put(DayOfWeek.TUESDAY, new Integer[]{9, 16});
        wh.put(DayOfWeek.WEDNESDAY, new Integer[]{9, 16});
        wh.put(DayOfWeek.THURSDAY, new Integer[]{9, 16});
        wh.put(DayOfWeek.FRIDAY, new Integer[]{9, 16});
        wh.put(DayOfWeek.SATURDAY, new Integer[]{9, 16});

        stores.add(new Store("Hidafurukawa", wh, 0.2));

        wh.clear();
        wh.put(DayOfWeek.MONDAY, new Integer[]{10, 12});
        wh.put(DayOfWeek.WEDNESDAY, new Integer[]{12, 15});
        wh.put(DayOfWeek.FRIDAY, new Integer[]{10, 15});

        stores.add(new Store("Osaka", wh, 0.3));

        wh.clear();
        wh.put(DayOfWeek.MONDAY, new Integer[]{8, 13});
        wh.put(DayOfWeek.TUESDAY, new Integer[]{8, 14});
        wh.put(DayOfWeek.WEDNESDAY, new Integer[]{8, 13});
        wh.put(DayOfWeek.THURSDAY, new Integer[]{8, 15});
        wh.put(DayOfWeek.FRIDAY, new Integer[]{9, 14});

        stores.add(new Store("Tokyo", wh, 0.5));

        wh.clear();
        wh.put(DayOfWeek.MONDAY, new Integer[]{7, 20});
        wh.put(DayOfWeek.TUESDAY, new Integer[]{6, 24});
        wh.put(DayOfWeek.SUNDAY, new Integer[]{8, 22});

        stores.add(new Store("Kyoto", wh, 0.42));
    }

    private void loadCustomers() {
        Customer ri = new Customer("Richard", "richard@gmail.com"); // Adherant
        Customer ro = new Customer("Romain", "romain@gmail.com"); // Register
        Customer am = new Customer("Amine", "amine@gmail.com"); // Register
        Customer an = new Customer("Anass", "anass@gmail.com"); // Anonyme

        try {

            ri.register("pass");
            ri.adhere();

            ro.register("pass");
            am.register("pass");
        } catch (Exception e) {
            e.printStackTrace();
        }

        customers = new ArrayList<>(Arrays.asList(ri, ro, am, an));
    }

    private void loadIngredients() {
        ingredients = new ArrayList<>();

        ingredients.add(new Dough("Plain", 1.2));
        ingredients.add(new Dough("Chocolate", 1.8));
        ingredients.add(new Dough("Peanut butter", 2.0));
        ingredients.add(new Dough("Oatmeal", 1.6));

        ingredients.add(new Flavour("Vanilla", 1.4));
        ingredients.add(new Flavour("Cinnamon", 1.8));
        ingredients.add(new Flavour("Chili", 2.2));

        ingredients.add(new Topping("White chocolate", 1.3));
        ingredients.add(new Topping("Milk chocolate", 1.9));
        ingredients.add(new Topping("MnMs", 2.5));
        ingredients.add(new Topping("Reeses buttercup", 3.1));
    }

    private void loadCookTechnique() {
        cookTechniques = new ArrayList<>();

        cookTechniques.add(new Mix("Mixed", 2));
        cookTechniques.add(new Mix("Topped", 1.3));

        cookTechniques.add(new Cooking("Crunchy", 1.3));
        cookTechniques.add(new Cooking("Chewy", 1.7));
    }

    private void loadDefaultRecipes() {
        defaultRecipes = new ArrayList<>();

        defaultRecipes.add(new DefaultRecipe(
                new HashMap<Product, Integer>() {
                    {
                        put(getCookTechniqueByName("Mixed"), 1);
                        put(getCookTechniqueByName("Crunchy"), 1);
                    }
                },
                new HashMap<Consomable, Integer>() {
                    {
                        put(getIngredientsByName("Milk chocolate"), 1);
                        put(getIngredientsByName("Cinnamon"), 1);
                        put(getIngredientsByName("Chocolate"), 1);
                    }
                },
                "Touchoko"));

        defaultRecipes.add(new DefaultRecipe(
                new HashMap<Product, Integer>() {
                    {
                        put(getCookTechniqueByName("Topped"), 1);
                        put(getCookTechniqueByName("Crunchy"), 1);
                    }
                },
                new HashMap<Consomable, Integer>() {
                    {
                        put(getIngredientsByName("Peanut butter"), 1);
                        put(getIngredientsByName("Vanilla"), 1);
                        put(getIngredientsByName("MnMs"), 1);
                    }
                },
                "MnMs"));

        defaultRecipes.add(new DefaultRecipe(
                new HashMap<Product, Integer>() {
                    {
                        put(getCookTechniqueByName("Topped"), 1);
                        put(getCookTechniqueByName("Chewy"), 1);
                    }
                },
                new HashMap<Consomable, Integer>() {
                    {
                        put(getIngredientsByName("Oatmeal"), 1);
                        put(getIngredientsByName("Chili"), 1);
                        put(getIngredientsByName("Reeses buttercup"), 1);
                    }
                },
                "Exotic"));

    }

    private void loadDrink() {
        drinks = new ArrayList<>();
        drinks.add(new Drink("Coke", 1, new HashMap<Consomable, Integer>() {{
            put(new Drink("Coke", 1, null), 1);
        }}));
        drinks.add(new Drink("Beer", 3.1, new HashMap<Consomable, Integer>() {{
            put(new Drink("Beer", 3.1, null), 1);
        }}));
        drinks.add(new Drink("Pepsi", 1.8, new HashMap<Consomable, Integer>() {{
            put(new Drink("Pepsi", 1.8, null), 1);
        }}));
    }

    private void loadDiscounts() {
        discounts = new ArrayList<>();
        discounts.add(new EventDiscount());
        discounts.add(new LoyaltyDiscount());
        discounts.add(new CompanyDiscount("Airbus"));   // CE_AIRBUS
        discounts.add(new CompanyDiscount("Athos"));    // CE_ATHOS
        discounts.add(new CompanyDiscount("Amadeus"));  // CE_AMADEUS
    }


    /**
     * ##########  SPECIAL GETTERS  ########
     **/

    public Store getStoreByAddress(String address) {
        for (Store s : stores)
            if (s.getAdress().equalsIgnoreCase(address))
                return s;

        try {
            throw new Exception("The store at address'" + address + "' does not exists.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer getCustomerByName(String name) {
        for (Customer c : customers)
            if (c.getName().equalsIgnoreCase(name))
                return c;

        try {
            throw new Exception("The customer'" + name + "' does not exists.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DefaultRecipe getDefaultRecipeByName(String name) {
        for (DefaultRecipe d : defaultRecipes)
            if (d.name.equalsIgnoreCase(name))
                return d;

        try {
            throw new Exception("The default recipe'" + name + "' does not exists.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Discount getDiscountByCode(String code) {
        for (Discount discount : discounts)
            if (discount.getCode().equals(code))
                return discount;

        try {
            throw new Exception("The discount'" + code + "' does not exists.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CookTechnique getCookTechniqueByName(String name) {
        for (CookTechnique d : cookTechniques)
            if (d.name.equalsIgnoreCase(name))
                return d;

        try {
            throw new Exception("The cook technique'" + name + "' does not exists.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Ingredients getIngredientsByName(String name) {
        for (Ingredients d : ingredients)
            if (d.name.equalsIgnoreCase(name))
                return d;

        try {
            throw new Exception("The ingredient'" + name + "' does not exists.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Drink getDrinkByName(String name) {
        for (Drink d : drinks)
            if (d.name.equalsIgnoreCase(name))
                return d;

        try {
            throw new Exception("The drink'" + name + "' does not exists.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ##########  GETTERS ########
     **/

    public List<Store> getStores() {
        return new ArrayList<>(stores);
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }

    public List<DefaultRecipe> getDefaultRecipes() {
        return new ArrayList<>(defaultRecipes);
    }

    public List<Discount> getDiscounts() {
        return new ArrayList<>(discounts);
    }

    public List<Dough> getDoughs() {
        List<Dough> dougs = new ArrayList<>();

        dougs.add(new Dough("Plain", 1.2));
        dougs.add(new Dough("Chocolate", 1.8));
        dougs.add(new Dough("Peanut butter", 2.0));
        dougs.add(new Dough("Oatmeal", 1.6));

        return dougs;
    }

    public List<Topping> getToppings() {
        List<Topping> topping = new ArrayList<>();

        topping.add(new Topping("White chocolate", 1.3));
        topping.add(new Topping("Milk chocolate", 1.9));
        topping.add(new Topping("MnMs", 2.5));
        topping.add(new Topping("Reeses buttercup", 3.1));

        return topping;
    }

    public List<Flavour> getFlavours() {
        List<Flavour> flavour = new ArrayList<>();

        flavour.add(new Flavour("Vanilla", 1.4));
        flavour.add(new Flavour("Cinnamon", 1.8));
        flavour.add(new Flavour("Chili", 2.2));

        return flavour;
    }

    public List<Cooking> getCookings() {
        List<Cooking> cooking = new ArrayList<>();

        cooking.add(new Cooking("Crunchy", 1.3));
        cooking.add(new Cooking("Chewy", 1.7));

        return cooking;
    }

    public List<Mix> getMixs() {
        List<Mix> mix = new ArrayList<>();

        mix.add(new Mix("Mixed", 2));
        mix.add(new Mix("Topped", 1.3));

        return mix;
    }

    public void add(Customer customer) {
        customers.add(customer);
    }

    /**
     * Add daily/mounthly recipe (as a default recipe)
     *
     * @param recipe
     * @param name
     */
    public void add(Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>> recipe, String name) {
        ProductFactory factory = new ProductFactory();
        defaultRecipes.add(factory.build(recipe, name));
    }
}
