package order;

import customer.Customer;
import discount.Discount;
import exceptions.CannotOrderOutOfStockException;
import main.BDSingleton;
import products.Consomable;
import products.recipes.CookTechnique;
import store.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface OrderInterface {

    OrderBuilder orderBuilder = new OrderBuilder();

    /**
     * Génere et retourne la liste des stores pouvant
     *
     * @return
     */
    default List<Store> getValidStore() {
        List<Store> valids = new ArrayList<>();

        BDSingleton bdSingleton = BDSingleton.getSINGLETON();
        List<Store> stores = bdSingleton.getStores();

        for (Store s : stores)
            valids.add(s);

        return valids;
    }

    /**
     * Valide et sélectionne le store de la commande
     *
     * @param address L'adresse du store choisi
     * @return true si le store est valide et que la commande poursuie, false sinon
     */
    default boolean selectStore(String address) throws CannotOrderOutOfStockException {
        BDSingleton bdSingleton = BDSingleton.getSINGLETON();
        Store store = bdSingleton.getStoreByAddress(address);

        // Possibilité de tester uniquement la validation du store choisit
        // Mais ne permets pas de proposé d'autres stores pouvant préparer la commande par exemple
        if (getValidStore().contains(store)) {
            if (store.canHandleThisOrder(orderBuilder.getOrders())) {
                orderBuilder.setStore(store);
                return true;
            } else {
                throw new CannotOrderOutOfStockException();
            }
        }

        return false;
    }


    default Store getStore(String address) {
        return BDSingleton.getSINGLETON().getStoreByAddress(address);
    }

    default void addDiscountsCode(List<String> codes) {
        if (codes != null) {
            for (String code : codes) {
                Discount discount = BDSingleton.getSINGLETON().getDiscountByCode(code);
                orderBuilder.addDiscount(discount);
            }
        }
    }

    /**
     * Finalise la commande et fait les actions supplémentaires si nécessaire
     *
     * @param customer Customer qui passe la commande
     * @return L'order crée
     */
    Order makeOrder(Customer customer);

    default void addProducts(Map<String, Integer> drinks, Map<String, Integer> defaultRecipes, Map<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> customsCookies) {
        orderBuilder.addProducts(drinks, defaultRecipes, customsCookies);
    }

}
