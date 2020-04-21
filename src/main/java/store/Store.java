package store;

import exceptions.CannotChangeClosingTimeException;
import exceptions.CannotChangeOpeningTimeException;
import exceptions.CannotOrderOutOfStockException;
import order.Order;
import order.items.ProductOrder;
import products.Consomable;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Store {
    private final String address;
    private Map<DayOfWeek, Integer[]> workingHour;
    private double tax;
    private List<Order> orders;
    private Stock stocks;

    public Store(String address, Map<DayOfWeek, Integer[]> workingHour, double tax) {
        this.address = address;
        this.workingHour = workingHour;
        this.tax = tax;
        orders = new ArrayList<>();
        stocks = new Stock();
    }


    /**
     * Ajoute une nouvelle order à la liste du magasin
     *
     * @param order L'order à ajouter
     */
    public void newOrder(Order order) {
        orders.add(order);
        stocks.useStock(order.getOrders());
    }

    /* ################ Delegates Stocks ####################### */

    /**
     * Return true si le store a la capacité d'effectuer la commande
     *
     * @param orders La liste de CookieOrder contenant la commande
     * @return true s'il peut la préparer, false sinon
     */
    public boolean canHandleThisOrder(List<ProductOrder> orders) {
        return stocks.canHandleThisOrder(orders);
    }

    public int addStock(Consomable need, Integer amount) {
        return stocks.addStock(need, amount);
    }

    public void addStock(Map<Consomable, Integer> needs) {
        stocks.addStock(needs);
    }


    public void useStock(Consomable need, Integer amount) throws CannotOrderOutOfStockException {
        stocks.useStock(need, amount);
    }

    public int getAmountOf(Consomable product) {
        return stocks.getAmountOf(product);
    }

    /* ################ Getters, Setters & Equals/Hashcode ####################### */

    public String getAdress() {
        return this.address;
    }

    public double getTax() {
        return this.tax;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Stock getStocks() {
        return this.stocks;
    }

    public int getOpeningHour(DayOfWeek day) {
        return this.workingHour.get(day)[0];
    }

    public int getClosingHour(DayOfWeek day) {
        return this.workingHour.get(day)[1];
    }

    @Override
    public String toString() {
        return "Store{" +
                "address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Double.compare(store.tax, tax) == 0 &&
                address.equals(store.address) &&
                workingHour.equals(store.workingHour) &&
                Objects.equals(orders, store.orders) &&
                Objects.equals(stocks, store.stocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, workingHour, tax, orders, stocks);
    }


    public void changeClosingTime(DayOfWeek day, int newTime) throws CannotChangeClosingTimeException {
        if (newTime > this.workingHour.get(day)[0]) {

            int oldOpening = this.workingHour.get(day)[0];
            Integer[] newWorkingTime = {oldOpening, newTime};
            this.workingHour.replace(day, newWorkingTime);
        } else throw new CannotChangeClosingTimeException();

    }


    public void changeOpeningTime(DayOfWeek day, int newTime) throws CannotChangeOpeningTimeException {
        if (newTime < this.workingHour.get(day)[1]) {

            int oldClosing = this.workingHour.get(day)[0];
            Integer[] newWorkingTime = {newTime, oldClosing};
            this.workingHour.replace(day, newWorkingTime);
        } else throw new CannotChangeOpeningTimeException();

    }

}
