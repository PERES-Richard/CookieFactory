package order;

import customer.Customer;

public class UserOrderInterface implements OrderInterface {

    @Override
    public Order makeOrder(Customer customer) {
        return orderBuilder.build(customer);
    }
}
