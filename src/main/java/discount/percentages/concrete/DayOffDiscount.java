package discount.percentages.concrete;

import discount.percentages.PercentageDiscount;

public class DayOffDiscount extends PercentageDiscount {
    public DayOffDiscount() {
        this.value = 0.3;
        this.code = "DAY-OFF";
    }
}
