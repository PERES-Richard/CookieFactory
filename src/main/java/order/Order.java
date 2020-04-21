package order;

import customer.Customer;
import discount.Discount;
import discount.fixed.FixedDiscount;
import discount.percentages.PercentageDiscount;
import discount.percentages.concrete.EventDiscount;
import exceptions.CannotPickUPOrderNotReadyException;
import order.items.ProductOrder;
import store.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {

    private double priceET;
    private double priceIT;
    private double priceDiscounted;
    private Store store;
    private OrderStatutState state;
    private List<ProductOrder> orders;
    private Customer customer;
    private List<PercentageDiscount> discounts = new ArrayList<>();
    private List<FixedDiscount> coupons = new ArrayList<>();

    public Order(List<ProductOrder> productOrders, Store store, Customer customer, List<PercentageDiscount> discounts, List<FixedDiscount> coupons) {
        this.store = store;
        orders = productOrders;
        this.customer = customer;
        this.discounts.addAll(discounts);
        this.coupons.addAll(coupons);

        priceET = calculatePriceET();
        priceIT = calculatePriceIT();
        calculatePriceDiscounted();
        state = OrderStatutState.WAITINGFORPAYMENT;

        store.newOrder(this);
    }

    private void calculatePriceDiscounted() {
        this.priceDiscounted = this.priceIT;
        for (PercentageDiscount discount : discounts) {
            if (discount.getCode().equals(EventDiscount.EVENT_CODE)) {
                EventDiscount event = new EventDiscount();
                int quantity = getTotalQuantity();
                this.priceDiscounted = event.applyEventDiscount(priceDiscounted, quantity);
            } else {
                this.priceDiscounted = discount.applyDiscountOn(priceDiscounted);
            }
        }
        for (Discount discount : this.coupons) {
            this.priceDiscounted = discount.applyDiscountOn(this.priceDiscounted);
        }
    }

    public double calculatePriceIT() {
        return priceET * (1 + store.getTax());
    }

    public double calculatePriceET() {
        double totalPriceHT = 0;
        for (ProductOrder co : this.orders) totalPriceHT += co.getPriceTTC();
        return totalPriceHT;
    }

    public int getTotalQuantity() {
        int result = 0;
        for (ProductOrder product : orders) {
            result += product.getQuantity();
        }
        return result;
    }

    /**
     * Retrait de la commande par le client.
     * Request le paiement s'il n'a pas encore était effectué.
     * Soulève une erreur si le statut ne permet pas un retrait.
     */
    public void pickUp() throws CannotPickUPOrderNotReadyException {

        if (state.equals(OrderStatutState.PREPARING)) {
            throw new CannotPickUPOrderNotReadyException();
        }

        if (state.equals(OrderStatutState.WAITINGFORPAYMENT)) {
            requestPayment();
        }

        // if order is ready && status is not payment failed
        if (state.equals(OrderStatutState.WAITINGFORPICKUP)) {
            state = OrderStatutState.DELIVERED;
        }
    }

    /**
     * Lance la requête du paiement auprès du tier.
     */
    public void requestPayment() {
        boolean success;

        // À remplacer par le résultat obenu auprès de l'api du tier chargé du paiement
        success = true; // par exemple paymentApi.requestPayment()

        if (success)
            this.state = OrderStatutState.WAITINGFORPICKUP;
        else
            this.state = OrderStatutState.PAYMENTFAILED;
    }
    /* ################ Getters, Setters & Equals/Hashcode ####################### */


    public List<ProductOrder> getOrders() {
        return orders;
    }

    public double getPriceHT() {
        return this.priceET;
    }

    public double getPriceTTC() {
        return this.priceIT;
    }

    public double getPriceDiscounted() {
        return priceDiscounted;
    }

    public OrderStatutState getState() {
        return this.state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.priceET, priceET) == 0 &&
                Double.compare(order.priceIT, priceIT) == 0 &&
                Double.compare(order.priceDiscounted, priceDiscounted) == 0 &&
                store.equals(order.store) &&
                state == order.state &&
                orders.equals(order.orders) &&
                customer.equals(order.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priceET, priceIT, priceDiscounted, store, state, orders, customer);
    }
}