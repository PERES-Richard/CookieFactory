package fonctional;

import cucumber.api.java8.En;
import customer.Customer;
import customer.CustomerType;
import exceptions.CannotAdhereException;
import exceptions.CannotRegisterException;
import exceptions.CannotUnadhereException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientRegisterStepdefs implements En {

    private Customer anonyme1;
    private Customer anonyme2;
    private Customer anonyme3;
    private Customer registered1;
    private Customer registered2;
    private Customer member1;
    private Customer adherent;
    private Customer adherent2;
    private String exception;
    private String adexception;
    private String adexception2;
    private String unadexception1;
    private String unadexception2;
    private String unadexception3;

    public ClientRegisterStepdefs() {

        //Scenario 1 : Create a Registered account with a valid email

        Given("^The user \"([^\"]*)\"'s first time visiting the app and decides to use a visitor account with a new email : \"([^\"]*)\"$", (String arg0, String arg1) -> {
            anonyme1 = new Customer(arg0, arg1);
        });

        When("^The customer adds his \"([^\"]*)\" and clicks on register$", (String arg0) -> {

            anonyme1.register(arg0);

        });
        Then("^The customer has a valid \"([^\"]*)\" account$", (CustomerType arg0) -> {

            assertEquals(this.anonyme1.getCustomerType(), arg0);

        });


        // Scenario 2 : A registered client wants to re_register himself


        Given("^The user \"([^\"]*)\" is already registered with the email : \"([^\"]*)\" and with the password : \"([^\"]*)\"$", (String arg0, String arg1, String arg2) -> {

            registered1 = new Customer(arg0, arg1);
            try {
                registered1.register(arg2);
            } catch (CannotRegisterException e) {
            }

        });
        When("^The customer tries to register himself with a password : \"([^\"]*)\" and clicks on register$", (String arg0) -> {

            try {
                registered1.register(arg0);
            } catch (CannotRegisterException e) {
                exception = e.getMessage();
            }

        });

        Then("^The customer gets an error message saying \"([^\"]*)\" but he is still a \"([^\"]*)\" client$", (String arg0, CustomerType arg1) -> {

            assertEquals(exception, arg0);

            assertEquals(this.registered1.getCustomerType(), arg1);

        });


        //Scenario 3: A registered client wants to adhere to the membership program

        Given("^The user \"([^\"]*)\" is already registered with the email :\"([^\"]*)\" and with password : \"([^\"]*)\"$", (String arg0, String arg1, String arg2) -> {

            member1 = new Customer(arg0, arg1);
            try {
                member1.register(arg2);
            } catch (CannotRegisterException e) {
            }
        });


        When("^The customer tries to adhere himself to the membership program$", () -> {

            try {
                member1.adhere();
            } catch (CannotAdhereException e) {
                exception = e.getMessage();
            }

        });
        Then("^The customer's account is now a valid \"([^\"]*)\" account$", (CustomerType arg0) -> {

            assertEquals(this.member1.getCustomerType(), arg0);

        });


        //Scenario 4: A member client wants to adhere to the membership program


        Given("^The user \"([^\"]*)\" is already registered with the email :\"([^\"]*)\" and with password : \"([^\"]*)\" and adrehent to the Membership program$", (String arg0, String arg1, String arg2) -> {
            adherent = new Customer(arg0, arg1);

            try {
                adherent.register(arg2);
            } catch (CannotRegisterException e) {
            }

            try {
                adherent.adhere();
            } catch (CannotAdhereException e) {

            }

        });
        When("^The adherent tries to adhere himself to the membership program$", () -> {

            try {
                adherent.adhere();
            } catch (CannotAdhereException e) {
                adexception = e.getMessage();
            }

        });
        Then("^The customer gets an error message saying \"([^\"]*)\" but he stays a \"([^\"]*)\" client$", (String arg0, CustomerType arg1) -> {


            assertEquals(adexception, arg0);

            assertEquals(this.adherent.getCustomerType(), arg1);


        });


        //Scenario 5: A visitor client wants to adhere to the membership program

        Given("^The user \"([^\"]*)\"'s first time visiting the app and is using a visitor account with a his email : \"([^\"]*)\"$", (String arg0, String arg1) -> {

            anonyme2 = new Customer(arg0, arg1);

        });

        When("^The user tries to adhere to the membership program$", () -> {

            try {
                anonyme2.adhere();
            } catch (CannotAdhereException e) {
                adexception2 = e.getMessage();
            }
        });

        Then("^The user receives an error message saying \"([^\"]*)\" so he stays as an \"([^\"]*)\" client$", (String arg0, CustomerType arg1) -> {


            assertEquals(adexception2, arg0);

            assertEquals(this.anonyme2.getCustomerType(), arg1);

        });


        //Scenario 6: A Visitor client wants to unadhere from the membership program


        Given("^The user \"([^\"]*)\"'s first time visiting the app and is currently using a visitor account with a his email : \"([^\"]*)\"$", (String arg0, String arg1) -> {

            anonyme3 = new Customer(arg0, arg1);

        });
        When("^The user wants to unadhere himself from the membership program$", () -> {

            try {
                anonyme3.unadhere();
            } catch (CannotUnadhereException e) {
                unadexception1 = e.getMessage();
            }
        });


        Then("^The user gets the error message \"([^\"]*)\" then he stays as an \"([^\"]*)\" client$", (String arg0, CustomerType arg1) -> {


            assertEquals(unadexception1, arg0);

            assertEquals(this.anonyme3.getCustomerType(), arg1);

        });


        //Scenario7: A Registered client wants to unadhere from the membership program


        Given("^The user \"([^\"]*)\" is already registered with the email : \"([^\"]*)\" and with password : \"([^\"]*)\"$", (String arg0, String arg1, String arg2) -> {

            registered2 = new Customer(arg0, arg1);
            try {
                registered2.register(arg2);
            } catch (CannotRegisterException e) {
            }


        });
        When("^The registered user wants to unadhere himself from the membership program$", () -> {

            try {
                registered2.unadhere();
            } catch (CannotUnadhereException e) {
                unadexception2 = e.getMessage();
            }

        });
        Then("^The user gets the error saying \"([^\"]*)\" then he stays as a \"([^\"]*)\" client$", (String arg0, CustomerType arg1) -> {

            assertEquals(unadexception2, arg0);

            assertEquals(this.registered2.getCustomerType(), arg1);

        });


        //Scenario8: A Member client wants to unadhere from the membership program


        Given("^The user \"([^\"]*)\" is already registered with the email :\"([^\"]*)\" and with password : \"([^\"]*)\" and belongs to the Membership program$", (String arg0, String arg1, String arg2) -> {

            adherent2 = new Customer(arg0, arg1);

            try {
                adherent2.register(arg2);
            } catch (CannotRegisterException e) {
            }

            try {
                adherent2.adhere();
            } catch (CannotAdhereException e) {

            }

        });

        When("^The member user tries to unadhere himself from the membership program$", () -> {

            try {
                adherent2.unadhere();
            } catch (CannotUnadhereException e) {
                unadexception3 = e.getMessage();
            }

        });

        Then("^The user gets to unadhere himself from the membership program then he becomes a \"([^\"]*)\" client$", (CustomerType arg0) -> {

            assertEquals(this.adherent2.getCustomerType(), arg0);

        });


    }


}
