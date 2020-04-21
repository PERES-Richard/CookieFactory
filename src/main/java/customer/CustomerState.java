package customer;

import exceptions.*;
import products.Consomable;
import products.recipes.CookTechnique;

import java.util.List;
import java.util.Map;

public interface CustomerState {
    void makeOrder(Map<String, Integer> drinks, Map<String, Integer> defaultRecipes, Map<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> customsCookies, List<String> discounts, String store) throws CannotOrderOutOfStockException;

    void makeOrderwithtime(Map<String, Integer> drinks, Map<String, Integer> defaultRecipes, Map<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> customsCookies, List<String> discounts, String store, int pickup) throws CannotOrderStoreWillBeClosedException;


    void register(String password) throws CannotRegisterException;

    void adhere() throws CannotAdhereException;

    void unadhere() throws CannotUnadhereException;

    void setCounterOrder(int value);

    CustomerType getCustomerType();
}
