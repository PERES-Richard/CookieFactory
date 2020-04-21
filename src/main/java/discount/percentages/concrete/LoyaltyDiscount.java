package discount.percentages.concrete;

import discount.percentages.PercentageDiscount;

public class LoyaltyDiscount extends PercentageDiscount {
    public LoyaltyDiscount() {
        this.value = 0.1;
        this.code = "LOYALTY-PROGRAM";
    }
}
