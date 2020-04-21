package store;

import exceptions.CannotOrderOutOfStockException;
import order.items.ProductOrder;
import products.Consomable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stock {

    private Map<Consomable, Integer> stocks;

    public Stock() {
        stocks = new HashMap<>();
    }

    public boolean canHandleThisOrder(List<ProductOrder> orders) {
        for (ProductOrder co : orders)
            for (Map.Entry<Consomable, Integer> c : co.product.getConsomableToUse().entrySet())
                if (!enoughStocks(c.getKey(), c.getValue() * co.quantity))
                    return false;
        return true;
    }

    private boolean enoughStocks(Consomable need, Integer amount) {
        return stocks.containsKey(need) && stocks.get(need) >= amount;
    }

    public int addStock(Consomable need, Integer amount) {
        if (stocks.containsKey(need)) {
            int oldValue = stocks.get(need);
            stocks.replace(need, oldValue + amount);
        } else {
            stocks.put(need, amount);
        }
        return stocks.get(need);
    }

    public void addStock(Map<Consomable, Integer> needs) {
        for (Map.Entry<Consomable, Integer> entry : needs.entrySet())
            addStock(entry.getKey(), entry.getValue());
    }

    public void useStock(List<ProductOrder> orders) {
        for (ProductOrder co : orders) {
            try {
                useStock(co.product.getConsomableToUse(), co.quantity);
            } catch (CannotOrderOutOfStockException e) {
                e.printStackTrace();
            }
        }
    }

    public void useStock(Map<Consomable, Integer> co, int quantity) throws CannotOrderOutOfStockException {
        for (Map.Entry<Consomable, Integer> c : co.entrySet()) {
            if (enoughStocks(c.getKey(), c.getValue() * quantity)) {
                useStock(c.getKey(), c.getValue() * quantity);
            } else throw new CannotOrderOutOfStockException(c.getKey(), c.getValue() * quantity);
        }
    }

    public void useStock(Consomable need, Integer amount) throws CannotOrderOutOfStockException {
        int oldValue = stocks.get(need);
        if (oldValue >= amount) {
            stocks.replace(need, oldValue - amount);
        } else throw new CannotOrderOutOfStockException(need, amount);
    }

    public int getAmountOf(Consomable product) {
        return stocks.get(product) != null ? stocks.get(product) : 0;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stocks=" + stocks +
                '}';
    }
}
