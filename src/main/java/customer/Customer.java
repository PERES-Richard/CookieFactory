package customer;

import exceptions.*;
import order.Order;
import order.OrderInterface;
import order.UserOrderInterface;
import products.Consomable;
import products.recipes.CookTechnique;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Customer {

    List<Order> orders;
    OrderInterface orderInterface;
    LocalDate registrationDate = null;
    /**
     * Registered et Member uniquement
     */
    String password;
    /**
     * Nombre de commande passé (incrémenté uniquement par les membres)
     */
    Integer counterOrder = 0;
    private String name;
    private String mail;
    /**
     * Type du customer (Anonymous, Registered ou Member) par default Anonymous
     */
    private CustomerState state;


    public Customer(String name, String mail) {
        this.name = name;
        this.mail = mail;
        this.orderInterface = new UserOrderInterface();
        orders = new ArrayList<>();
        this.state = new AnonymousCustomerState(this);
    }

    /**
     * Passe une nouvelle commande
     * TODO
     *
     * @param drinks
     * @param defaultRecipes
     * @param customsCookies
     * @param discounts
     * @param store
     */
    public void makeOrder(Map<String, Integer> drinks, Map<String, Integer> defaultRecipes, Map<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> customsCookies, List<String> discounts, String store) throws CannotOrderOutOfStockException {
        if (drinks == null && defaultRecipes == null && customsCookies == null) {
            try {
                throw new NoProductProvidedException();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.state.makeOrder(drinks, defaultRecipes, customsCookies, discounts, store);
    }


    public void makeOrderwithtime(Map<String, Integer> drinks, Map<String, Integer> defaultRecipes, Map<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> customsCookies, List<String> discounts, String store, int pickup) throws CannotOrderStoreWillBeClosedException {

        this.state.makeOrderwithtime(drinks, defaultRecipes, customsCookies, discounts, store, pickup);
    }

    /**
     * Package-private : Pour permettre aux état de faire changer d'état le customer
     *
     * @param newState
     */
    void changeStateTo(CustomerState newState) {
        this.state = newState;
    }

    /**
     * @param password
     * @throws CannotRegisterException
     */
    public void register(String password) throws CannotRegisterException {
        this.state.register(password);
    }

    /**
     * @throws CannotAdhereException
     */
    public void adhere() throws CannotAdhereException {
        this.state.adhere();
    }

    /**
     * @throws CannotUnadhereException
     */
    public void unadhere() throws CannotUnadhereException {
        this.state.unadhere();
    }

    public void resetCounterOrder() {
        this.counterOrder = 0;
    }

    public CustomerType getCustomerType() {
        return this.state.getCustomerType();
    }

    /* ################ Getters, Setters & Equals/Hashcode ####################### */

    public List<Order> getOrders() {
        return orders;
    }

    public int getCounterOrder() {
        return counterOrder;
    }

    public void setCounterOrder(Integer counterOrder) {
        this.state.setCounterOrder(counterOrder);
    }

    public String getName() {
        return this.name;
    }

    public String getMail() {
        return this.mail;
    }

    public LocalDate getDateRegistration() {
        return registrationDate;
    }
}