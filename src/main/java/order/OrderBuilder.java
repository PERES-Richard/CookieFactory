package order;

import customer.Customer;
import discount.Discount;
import discount.fixed.FixedDiscount;
import discount.percentages.PercentageDiscount;
import discount.percentages.concrete.FidelityDscount;
import discount.percentages.concrete.LoyaltyDiscount;
import exceptions.IncompleteOrderException;
import order.items.ProductOrder;
import products.Consomable;
import products.recipes.CookTechnique;
import store.Store;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderBuilder {

    private List<ProductOrder> orders;

    private List<PercentageDiscount> discounts;
    private List<FixedDiscount> coupons;

    private Customer customer;
    private Store store;

    private ProductOrderFactory productOrderFactory;

    public OrderBuilder() {
        orders = new ArrayList<>();
        discounts = new ArrayList<>();
        coupons = new ArrayList<>();
        productOrderFactory = new ProductOrderFactory();
    }

    public void addDiscount(Discount discount) {
        if (discount.isFixed()) {
            coupons.add((FixedDiscount) discount);
        } else {
            discounts.add((PercentageDiscount) discount);
        }
    }

    private void applyFidelity() {
        if (this.customer.getCounterOrder() >= 30) {
            this.discounts.add(new LoyaltyDiscount());
            this.customer.resetCounterOrder();
        }
        if (this.customer.getDateRegistration() != null) {
            Period period = Period.between(LocalDate.now(), this.customer.getDateRegistration());
            if (period.getYears() >= 1) {
                this.discounts.add(new FidelityDscount(period.getYears()));
            }
        }
    }

    /**
     * Construit la commande et la renvoie. Renvoie null si elle est incomplète.
     * Au niveau de l'application des discounts, on applique al somme de tout les discounts
     *
     * @return La commande si elle est complète, null sinon
     */
    public Order build(Customer customer) {
        if (customer.getCounterOrder() >= 30) {
            this.discounts.add(new LoyaltyDiscount());
            customer.resetCounterOrder();
        }
        if (customer.getDateRegistration() != null) {
            Period period = Period.between(LocalDate.now(), customer.getDateRegistration());
            if (period.getYears() >= 1) {
                this.discounts.add(new FidelityDscount(period.getYears()));
            }
        }
        if (!orders.isEmpty() && store != null) {
            Order order = new Order(orders, store, customer, discounts, coupons);
            clear();
            return order;
        } else {
            try {
                throw new IncompleteOrderException(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Annule la construction de la commande. Réinitialise le builder
     */
    public void clear() {
        discounts.clear();
        orders.clear();
        store = null;
        customer = null;
    }

    /* ################ Getters, Setters & Equals/Hashcode ####################### */

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ProductOrder> getOrders() {
        return orders;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void addProducts(Map<String, Integer> drinks, Map<String, Integer> defaultRecipes, Map<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> customsCookies) {
        orders.addAll(productOrderFactory.build(drinks, defaultRecipes, customsCookies));
    }
}
