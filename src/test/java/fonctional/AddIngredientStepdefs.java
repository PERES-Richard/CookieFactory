package fonctional;

import cucumber.api.java8.En;
import main.BDSingleton;
import products.consomables.Drink;
import products.consomables.Ingredients;
import store.Store;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddIngredientStepdefs implements En {

    public Ingredients ingredient1;
    public Ingredients ingredient2;
    public Drink drink1;
    public Drink drink2;
    BDSingleton singleton = BDSingleton.getSINGLETON();
    private Store store1;
    private Store store2;
    private int oldStock1;
    private int oldStock2;


    public AddIngredientStepdefs() {


        //Scenario 1:

        Given("^A store located in \"([^\"]*)\" .$", (String arg0) -> {


            store1 = singleton.getStoreByAddress(arg0);
        });


        When("^the store adds a new ingredient to its stock called \"([^\"]*)\" that costs \"([^\"]*)\" euros$", (String arg0, Double arg1) -> {

            ingredient1 = singleton.getIngredientsByName(arg0);
            store1.addStock(ingredient1, 10);
        });

        Then("^the stock now contains the ingredient \"([^\"]*)\" .$", (String arg0) -> {
            assertEquals(store1.getStocks().getAmountOf(ingredient1), 10);
        });


        //Scenario 2:

        Given("^Another store located in \"([^\"]*)\"$", (String arg0) -> {

            store2 = singleton.getStoreByAddress(arg0);
        });

        And("^has a stock of \"([^\"]*)\"$", (String arg0) -> {
            oldStock1 = 10;
            ingredient2 = singleton.getIngredientsByName(arg0);

            store2.addStock(ingredient2, oldStock1);

        });


        When("^The owner adds \"([^\"]*)\" \"([^\"]*)\" to the stock$", (Integer arg0, String arg1) -> {
            ingredient2 = singleton.getIngredientsByName(arg1);

            store2.addStock(ingredient2, arg0);
        });
        Then("^The store has now \"([^\"]*)\" more \"([^\"]*)\" in its stock$", (Integer arg0, String arg1) -> {
            assertEquals(store2.getStocks().getAmountOf(ingredient2), arg0 + oldStock1);
        });


        //Scenario 3:

        Given("^A store located \"([^\"]*)\" .$", (String arg0) -> {

            store1 = singleton.getStoreByAddress(arg0);
        });


        When("^the store adds a new drink to its stock called \"([^\"]*)\" that costs \"([^\"]*)\" euros$", (String arg0, Double arg1) -> {
            drink1 = singleton.getDrinkByName(arg0);
            store1.addStock(drink1, 10);
        });
        Then("^the stock now contains the drink \"([^\"]*)\" .$", (String arg0) -> {
            assertEquals(store1.getStocks().getAmountOf(drink1), 10);
        });


        //Scenario 4:

        Given("^Another store is in \"([^\"]*)\"$", (String arg0) -> {

            store2 = singleton.getStoreByAddress(arg0);
        });

        And("^has a stock of the drink \"([^\"]*)\" that costs \"([^\"]*)\" euros$", (String arg0, Double arg1) -> {

            drink2 = singleton.getDrinkByName(arg0);
            oldStock2 = 10;
            store2.addStock(drink2, oldStock2);
        });


        When("^The owner adds \"([^\"]*)\" drinks to his stock$", (Integer arg0) -> {
            store2.addStock(drink2, arg0);
        });

        Then("^The store has now \"([^\"]*)\" more \"([^\"]*)\" drink in the stock$", (Integer arg0, String arg1) -> {
            assertEquals(store2.getStocks().getAmountOf(drink2), arg0 + oldStock2);
        });
    }

}