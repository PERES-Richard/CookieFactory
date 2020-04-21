package discount.percentages;

import discount.Discount;

public abstract class PercentageDiscount extends Discount {
    @Override
    public boolean isFixed() {
        return false;
    }

    @Override
    public double applyDiscountOn(double price) {
        return price - (price * this.value);
    }
}
