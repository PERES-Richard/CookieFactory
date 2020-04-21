package discount.percentages.concrete;

import discount.percentages.PercentageDiscount;

public class CustomDiscount extends PercentageDiscount {
    public CustomDiscount(double value, String code) {
        this.value = value;
        this.code = code;
    }
}
