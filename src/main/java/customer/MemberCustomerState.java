package customer;

import exceptions.*;
import products.Consomable;
import products.recipes.CookTechnique;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public class MemberCustomerState implements CustomerState {
    private Customer customer;

    public MemberCustomerState(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void makeOrder(Map<String, Integer> drinks, Map<String, Integer> defaultRecipes, Map<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> customsCookies, List<String> discounts, String store) throws CannotOrderOutOfStockException {

        customer.orderInterface.addProducts(drinks, defaultRecipes, customsCookies);
        customer.orderInterface.addDiscountsCode(discounts);

        if (!customer.orderInterface.selectStore(store))
            try {
                throw new StoreCannotHandleOrder(store);
            } catch (Exception e) {
                e.printStackTrace();
            }

        /**
         * Lance le build de l'order, la récupère et l'ajoute à la liste des commandes du customer
         */
        customer.orders.add(customer.orderInterface.makeOrder(customer));
        customer.counterOrder++;
    }


    public void makeOrderwithtime(Map<String, Integer> drinks, Map<String, Integer> defaultRecipes, Map<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> customCookies, List<String> discounts, String store, int pickup) throws CannotOrderStoreWillBeClosedException {
        int opening = customer.orderInterface.getStore(store).getOpeningHour(DayOfWeek.MONDAY);
        int closing = customer.orderInterface.getStore(store).getClosingHour(DayOfWeek.MONDAY);
        if (opening < pickup && closing > pickup) {

            try {
                makeOrder(drinks, defaultRecipes, customCookies, discounts, store);
            } catch (CannotOrderOutOfStockException e) {
                e.printStackTrace();
            }

        } else throw new CannotOrderStoreWillBeClosedException(pickup);

    }


    @Override
    public void register(String password) throws CannotRegisterException {
        throw new CannotRegisterException(CustomerType.MEMBER);
    }

    @Override
    public void adhere() throws CannotAdhereException {
        throw new CannotAdhereException(CustomerType.MEMBER);
    }

    @Override
    public void unadhere() {
        this.customer.counterOrder = 0;
        // TODO this.customer.orderInterface = new RegisteredUserOrderInterface();
        this.customer.changeStateTo(new RegisteredCustomerState(this.customer));
    }

    @Override
    public void setCounterOrder(int value) {
        customer.counterOrder = value;
    }

    @Override
    public CustomerType getCustomerType() {
        return CustomerType.MEMBER;
    }
}
