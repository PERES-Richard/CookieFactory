package discount.percentages.concrete;

import discount.percentages.PercentageDiscount;

public class FidelityDscount extends PercentageDiscount {
    private final static double yearValue = 0.01;

    public FidelityDscount(int years) {
        this.value = yearValue * years;
        this.code = "FIDELITY";
    }
}
