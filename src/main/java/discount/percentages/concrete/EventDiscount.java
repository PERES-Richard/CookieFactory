package discount.percentages.concrete;

import discount.percentages.PercentageDiscount;

public class EventDiscount extends PercentageDiscount {
    public static final String EVENT_CODE = "EVENT";

    public EventDiscount() {
        this.value = 0.1;
        this.code = EVENT_CODE;
    }

    public double applyEventDiscount(double price, int quantity) {
        if (quantity >= 100) {
            return super.applyDiscountOn(price);
        } else {
            return price;
        }
    }
}
