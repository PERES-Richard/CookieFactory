package unit;


import exceptions.CannotChangeClosingTimeException;
import exceptions.CannotChangeOpeningTimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.Store;

import java.time.DayOfWeek;
import java.util.HashMap;

import static java.time.DayOfWeek.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class StoreTest {

    public HashMap<DayOfWeek, Integer[]> s1 = new HashMap<DayOfWeek, Integer[]>();
    public HashMap<DayOfWeek, Integer[]> s2 = new HashMap<DayOfWeek, Integer[]>();
    private Store store1;
    private Store store2;

    @BeforeEach
    void setUp() {


        s1.put(MONDAY, new Integer[]{8, 16});
        s1.put(TUESDAY, new Integer[]{8, 16});
        s1.put(WEDNESDAY, new Integer[]{8, 16});
        s1.put(THURSDAY, new Integer[]{8, 16});
        s1.put(FRIDAY, new Integer[]{8, 16});
        s1.put(SATURDAY, new Integer[]{8, 16});

        s2.put(MONDAY, new Integer[]{10, 16});
        s2.put(TUESDAY, new Integer[]{10, 16});
        s2.put(WEDNESDAY, new Integer[]{10, 16});
        s2.put(THURSDAY, new Integer[]{10, 16});
        s2.put(FRIDAY, new Integer[]{10, 16});
        s2.put(SATURDAY, new Integer[]{10, 16});


        store1 = new Store("23 Rue de Robert Latouch , Nice", s1, 0.6);
        store2 = new Store("120 Rue de Lacouz , Nice", s2, 1.2);

    }

    @Test
    void changeOpeningHour1() throws CannotChangeOpeningTimeException {
        int newTime1 = 9;

        store1.changeOpeningTime(MONDAY, newTime1);

        assertEquals(this.store1.getOpeningHour(MONDAY), newTime1);

        assertNotEquals(this.store1.getOpeningHour(MONDAY), newTime1 + 3);
    }

    @Test
    void changeOpeningHour2() throws CannotChangeOpeningTimeException {
        int oldTime = store2.getOpeningHour(MONDAY);
        int newTime1 = 17;

        try {
            store2.changeOpeningTime(MONDAY, newTime1);
        } catch (CannotChangeOpeningTimeException e) {
        }


        assertEquals(this.store2.getOpeningHour(MONDAY), oldTime);

        assertNotEquals(this.store2.getOpeningHour(MONDAY), newTime1);
    }


    @Test
    void changeClosingHour1() throws CannotChangeClosingTimeException {
        int newTime1 = 19;

        store1.changeClosingTime(TUESDAY, newTime1);

        assertEquals(this.store1.getClosingHour(TUESDAY), newTime1);

        assertNotEquals(this.store1.getClosingHour(TUESDAY), newTime1 + 3);
    }

    @Test
    void changeClosingHour2() throws CannotChangeClosingTimeException {
        int oldTime = store2.getClosingHour(TUESDAY);
        int newTime1 = 9;

        try {
            store2.changeClosingTime(TUESDAY, newTime1);
        } catch (CannotChangeClosingTimeException e) {
        }


        assertEquals(this.store2.getClosingHour(TUESDAY), oldTime);

        assertNotEquals(this.store2.getClosingHour(TUESDAY), newTime1);
    }


}
