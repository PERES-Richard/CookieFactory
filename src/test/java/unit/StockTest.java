package unit;


import exceptions.CannotOrderOutOfStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import products.consomables.Ingredients;
import products.consomables.ingredients.Flavour;
import store.Store;

import java.time.DayOfWeek;
import java.util.HashMap;

import static java.time.DayOfWeek.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockTest {


    public Ingredients ingredient1;
    public Ingredients ingredient2;
    public Ingredients ingredient3;
    public HashMap<DayOfWeek, Integer[]> s1 = new HashMap<DayOfWeek, Integer[]>();
    public HashMap<DayOfWeek, Integer[]> s2 = new HashMap<DayOfWeek, Integer[]>();
    public HashMap<DayOfWeek, Integer[]> s3 = new HashMap<DayOfWeek, Integer[]>();
    private Store store1;
    private Store store2;
    private Store store3;

    @BeforeEach
    void setUp() {

        s1.put(MONDAY, new Integer[]{9, 16});
        s1.put(TUESDAY, new Integer[]{9, 16});
        s1.put(WEDNESDAY, new Integer[]{9, 16});
        s1.put(THURSDAY, new Integer[]{9, 16});
        s1.put(FRIDAY, new Integer[]{9, 16});
        s1.put(SATURDAY, new Integer[]{9, 16});

        s2.put(MONDAY, new Integer[]{8, 16});
        s2.put(TUESDAY, new Integer[]{8, 16});
        s2.put(WEDNESDAY, new Integer[]{8, 16});
        s2.put(THURSDAY, new Integer[]{8, 16});
        s2.put(FRIDAY, new Integer[]{8, 16});
        s2.put(SATURDAY, new Integer[]{8, 16});


        s3.put(MONDAY, new Integer[]{9, 17});
        s3.put(TUESDAY, new Integer[]{9, 17});
        s3.put(WEDNESDAY, new Integer[]{9, 17});
        s3.put(THURSDAY, new Integer[]{9, 17});
        s3.put(FRIDAY, new Integer[]{9, 17});
        s3.put(SATURDAY, new Integer[]{9, 17});


        store1 = new Store("23 Rue de Robert Latouch , Nice", s1, 0.6);
        store2 = new Store("120 Rue de Lacouz , Nice", s2, 1.2);
        store3 = new Store("7bis Avenue du renard, Kyoto", s3, 0.4);
        ingredient1 = new Flavour("Chili", 2.1);
        ingredient2 = new Flavour("Cinnamon", 3.3);
        ingredient3 = new Flavour("Vanilla", 3.2);

    }


    @Test
    public void addStockTest() {
        store1.addStock(ingredient1, 20);
        assertEquals(store1.getStocks().getAmountOf(ingredient1), 20);
        store2.addStock(ingredient2, 30);
        assertEquals(store2.getStocks().getAmountOf(ingredient2), 30);
        store3.addStock(ingredient3, 40);
        assertEquals(store3.getStocks().getAmountOf(ingredient3), 40);
        store1.addStock(ingredient1, 5);
        assertEquals(store1.getStocks().getAmountOf(ingredient1), 25);
        store2.addStock(ingredient2, 5);
        assertEquals(store2.getStocks().getAmountOf(ingredient2), 35);
        store3.addStock(ingredient3, 5);
        assertEquals(store3.getStocks().getAmountOf(ingredient3), 45);
    }


    @Test
    public void useStockTest() throws CannotOrderOutOfStockException {
        store1.addStock(ingredient1, 20);
        store2.addStock(ingredient2, 30);
        store3.addStock(ingredient3, 40);
        store1.useStock(ingredient1, 7);
        assertEquals(store1.getStocks().getAmountOf(ingredient1), 13);
        store2.useStock(ingredient2, 2);
        assertEquals(store2.getStocks().getAmountOf(ingredient2), 28);
        store3.useStock(ingredient3, 23);
        assertEquals(store3.getStocks().getAmountOf(ingredient3), 17);
    }

    @Test
    public void cannotUseStockTest() {
        store1.addStock(ingredient1, 20);
        store2.addStock(ingredient2, 30);
        store3.addStock(ingredient3, 40);
        try {
            store1.useStock(ingredient1, 25);
        } catch (CannotOrderOutOfStockException e) {
        }
        assertEquals(store1.getStocks().getAmountOf(ingredient1), 20);

        try {
            store2.useStock(ingredient2, 31);
        } catch (CannotOrderOutOfStockException e) {
        }
        assertEquals(store2.getStocks().getAmountOf(ingredient2), 30);

        try {
            store3.useStock(ingredient3, 900);
        } catch (CannotOrderOutOfStockException e) {
        }
        assertEquals(store3.getStocks().getAmountOf(ingredient3), 40);
    }


/*
    @Test
    void canHandleThisOrderTest(){
        store1.addStock(ingredient1,20);
        store1.addStock(ingredient2,20);
        store1.addStock(ingredient3,20);
    }
*/

}
