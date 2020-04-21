package unit;

import customer.Customer;
import customer.CustomerType;
import exceptions.CannotAdhereException;
import exceptions.CannotRegisterException;
import exceptions.CannotUnadhereException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    private Customer anonymeCustomer;
    private Customer registeredCustomer;
    private Customer memberCustomer;

    @BeforeEach
    void setUp() {
        this.anonymeCustomer = new Customer("John", "john.doe@mail.com");
        assertSame(anonymeCustomer.getCustomerType(), CustomerType.ANONYME);
        this.registeredCustomer = new Customer("John", "john.doe@mail.com");
        try {
            this.registeredCustomer.register("password");
            assertSame(registeredCustomer.getCustomerType(), CustomerType.REGISTERED);
        } catch (CannotRegisterException e) {
            fail();
        }
        this.memberCustomer = new Customer("John", "john.doe@mail.com");
        try {
            this.memberCustomer.register("password");
            this.memberCustomer.adhere();
            assertSame(memberCustomer.getCustomerType(), CustomerType.MEMBER);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void makeOrder() {
    }

    @Test
    void register_on_anonyme() {
        try {
            anonymeCustomer.register("password");
            assertSame(anonymeCustomer.getCustomerType(), CustomerType.REGISTERED);
        } catch (CannotRegisterException e) {
            fail();
        }
    }

    @Test
    void register_on_registered() {
        try {
            registeredCustomer.register("password");
            fail();
        } catch (CannotRegisterException e) {
            assertSame(registeredCustomer.getCustomerType(), CustomerType.REGISTERED);
        }
    }

    @Test
    void register_on_member() {
        try {
            memberCustomer.register("password");
            fail();
        } catch (CannotRegisterException e) {
            assertSame(memberCustomer.getCustomerType(), CustomerType.MEMBER);
        }
    }

    @Test
    void adhere_on_registered() {
        try {
            registeredCustomer.adhere();
            assertSame(registeredCustomer.getCustomerType(), CustomerType.MEMBER);
            assertEquals(0, registeredCustomer.getCounterOrder());
        } catch (CannotAdhereException e) {
            fail();
        }
    }

    @Test
    void adhere_on_anonyme() {
        try {
            anonymeCustomer.adhere();
            fail();
        } catch (CannotAdhereException e) {
            assertSame(anonymeCustomer.getCustomerType(), CustomerType.ANONYME);
        }
    }

    @Test
    void adhere_on_member() {
        try {
            memberCustomer.adhere();
            fail();
        } catch (CannotAdhereException e) {
            assertSame(memberCustomer.getCustomerType(), CustomerType.MEMBER);
        }
    }

    @Test
    void unadhere_on_member() {
        try {
            memberCustomer.unadhere();
            assertSame(memberCustomer.getCustomerType(), CustomerType.REGISTERED);
        } catch (CannotUnadhereException e) {
            fail();
        }
    }

    @Test
    void unadhere_on_registered() {
        try {
            registeredCustomer.unadhere();
            fail();
        } catch (CannotUnadhereException e) {
            assertSame(registeredCustomer.getCustomerType(), CustomerType.REGISTERED);
        }
    }

    @Test
    void unadhere_on_anonyme() {
        try {
            anonymeCustomer.unadhere();
            fail();
        } catch (CannotUnadhereException e) {
            assertSame(anonymeCustomer.getCustomerType(), CustomerType.ANONYME);
        }
    }
}