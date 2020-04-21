package discount;

public abstract class Discount {

    public static final Double EVENT_DISCOUNT_VALUE = 10.0;
    public static final Double FIDELITY_DISCOUNT_VALUE = 20.0;
    public static final Double CE_DISCOUNT_VALUE = 5.0;

    protected double value;
    protected String code;

    public abstract double applyDiscountOn(double price);

    public abstract boolean isFixed();

    public Double getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }
}
