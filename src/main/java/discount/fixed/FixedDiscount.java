package discount.fixed;

import discount.Discount;

public abstract class FixedDiscount extends Discount {
    @Override
    public boolean isFixed() {
        return true;
    }

    @Override
    public double applyDiscountOn(double price) {
        return price - this.value;
    }
}
