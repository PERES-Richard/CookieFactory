package fonctional;

import cucumber.api.java8.En;
import exceptions.CannotChangeClosingTimeException;
import exceptions.CannotChangeOpeningTimeException;
import store.Store;

import java.time.DayOfWeek;
import java.util.HashMap;

import static java.time.DayOfWeek.MONDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StoreStepDef implements En {

    public Store store;
    public Store store2;
    public Store store3;
    public Store store4;

    public HashMap<DayOfWeek, Integer[]> s1 = new HashMap<DayOfWeek, Integer[]>();
    public HashMap<DayOfWeek, Integer[]> s2 = new HashMap<DayOfWeek, Integer[]>();
    public HashMap<DayOfWeek, Integer[]> s3 = new HashMap<DayOfWeek, Integer[]>();
    public HashMap<DayOfWeek, Integer[]> s4 = new HashMap<DayOfWeek, Integer[]>();

    public String exception1;
    public String exception2;
    public String exception3;
    public String exception4;

    public StoreStepDef() {

        //Scenario1:  changing the opening time with a valid one

        Given("^A store that opens at \"([^\"]*)\" and closes at \"([^\"]*)\", has a tax of \"([^\"]*)\"% and is located on \"([^\"]*)\"$", (Integer arg0, Integer arg1, Double arg2, String arg3) -> {

            s1.put(MONDAY, new Integer[]{arg0, arg1});
            store = new Store(arg3, s1, arg2);
        });


        When("^The store manager decides to change the opening time to \"([^\"]*)\"$", (Integer arg0) -> {

            try {
                store.changeOpeningTime(MONDAY, arg0);
            } catch (CannotChangeOpeningTimeException e) {
                exception3 = e.getMessage();
            }

        });


        Then("^The store now will show that it opens at \"([^\"]*)\" instead of \"([^\"]*)\" on the interface$", (Integer arg0, Integer arg1) -> {


            assertEquals(this.store.getOpeningHour(MONDAY), arg0);

            assertNotEquals(this.store.getOpeningHour(MONDAY), arg1);

        });


        //Scenario2:  changing the closing time with a valid one


        Given("^A store that opens at \"([^\"]*)\" and closes at \"([^\"]*)\", has a tax rate of \"([^\"]*)\"% and is located on \"([^\"]*)\"$", (Integer arg0, Integer arg1, Double arg2, String arg3) -> {

            s2.put(MONDAY, new Integer[]{arg0, arg1});
            store2 = new Store(arg3, s2, arg2);
        });


        When("^The store manager decides to change the closing time to \"([^\"]*)\"$", (Integer arg0) -> {

            try {
                store2.changeClosingTime(MONDAY, arg0);
            } catch (CannotChangeClosingTimeException e) {
                exception4 = e.getMessage();
            }

        });


        Then("^The store now will show that it closes at \"([^\"]*)\" instead of \"([^\"]*)\" on the interface$", (Integer arg0, Integer arg1) -> {

            assertEquals(this.store2.getClosingHour(MONDAY), arg0);

            assertNotEquals(this.store2.getClosingHour(MONDAY), arg1);
        });


        //Scenario4:  changing the opening time with an invalid one


        Given("^A store that usually opens at \"([^\"]*)\" and closes at \"([^\"]*)\", has a tax of \"([^\"]*)\"% and is located on \"([^\"]*)\"$", (Integer arg0, Integer arg1, Double arg2, String arg3) -> {

            s3.put(MONDAY, new Integer[]{arg0, arg1});
            store3 = new Store(arg3, s3, arg2);
        });


        When("^The store manager decides to change the store's opening time to \"([^\"]*)\"$", (Integer arg0) -> {
            try {
                store3.changeOpeningTime(MONDAY, arg0);
            } catch (CannotChangeOpeningTimeException e) {
                exception1 = e.getMessage();
            }
        });


        Then("^He gets the error message \"([^\"]*)\" and the opening time stays \"([^\"]*)\"$", (String arg0, Integer arg1) -> {

            assertEquals(exception1, arg0);
            assertEquals(this.store3.getOpeningHour(MONDAY), arg1);

        });

        //Scenario4:  changing the closing time with an invalid one


        Given("^A store opens at \"([^\"]*)\" and closes at \"([^\"]*)\", has a tax of \"([^\"]*)\"% and is located on \"([^\"]*)\"$", (Integer arg0, Integer arg1, Double arg2, String arg3) -> {

            s4.put(MONDAY, new Integer[]{arg0, arg1});
            store4 = new Store(arg3, s4, arg2);
        });

        When("^The store manager decides to change the store's closing time to \"([^\"]*)\"$", (Integer arg0) -> {
            try {
                store4.changeClosingTime(MONDAY, arg0);
            } catch (CannotChangeClosingTimeException e) {
                exception2 = e.getMessage();
            }
        });
        Then("^He gets the error message \"([^\"]*)\" and the closing time stays \"([^\"]*)\"$", (String arg0, Integer arg1) -> {

            assertEquals(exception2, arg0);
            assertEquals(this.store4.getClosingHour(MONDAY), arg1);

        });


    }
}
