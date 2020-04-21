package discount.fixed.concretes;

import discount.fixed.FixedDiscount;

public class CouponDiscount extends FixedDiscount {
    public CouponDiscount(double value, String code) {
        this.value = value;
        this.code = code;
    }
}
