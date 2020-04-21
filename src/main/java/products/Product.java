package products;

import discount.Discount;
import discount.percentages.concrete.DayOffDiscount;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Product {
    public String name;
    protected double priceHT;
    protected double priceTTC = -1;
    protected Map<Product, Integer> composedWith = new HashMap<>();
    protected Map<Consomable, Integer> consomableNeeded = new HashMap<>();

    public Product(double price) {
        this.priceHT = price;
    }

    public Product(Map<Product, Integer> composedWith) {
        this.composedWith.putAll(composedWith);
        this.priceHT = calculatePrice();
        this.priceTTC = calculateTTCPrice();
    }

    public Product(Map<Product, Integer> composedWith, Map<Consomable, Integer> consomableNeeded) {
        this.composedWith.putAll(composedWith);
        this.consomableNeeded.putAll(consomableNeeded);
        this.priceHT = calculatePrice();
        this.priceTTC = calculateTTCPrice();
    }

    public Product(String name, double price) {
        this.name = name;
        this.priceHT = price;
        this.priceTTC = calculateTTCPrice();
    }

    public double getPriceHT() {
        return priceHT;
    }

    protected double calculateTTCPrice() {
        if (priceTTC > 0)
            return priceTTC;

        if (composedWith.isEmpty() && consomableNeeded.isEmpty()) {
            return priceHT;
        }

        double finalPrice = 0;

        for (Map.Entry<Product, Integer> entry : composedWith.entrySet())
            finalPrice += entry.getKey().getPriceTTC() * entry.getValue();

        for (Map.Entry<Consomable, Integer> entry : consomableNeeded.entrySet())
            finalPrice += entry.getKey().getPriceTTC() * entry.getValue();

        return finalPrice;
    }

    protected double calculatePrice() {
        double finalPrice = 0;

        for (Map.Entry<Product, Integer> entry : composedWith.entrySet())
            finalPrice += entry.getKey().getPriceHT() * entry.getValue();

        for (Map.Entry<Consomable, Integer> entry : consomableNeeded.entrySet())
            finalPrice += entry.getKey().getPriceHT() * entry.getValue();

        return finalPrice;
    }

    public double getDiscountedPrice(Discount discount, double priceTTC) {
        return discount.applyDiscountOn(priceTTC);
    }

    public double getDiscountedPrice(DayOffDiscount discount, double priceTTC) {
        return discount.applyDiscountOn(priceTTC);
    }

    public Map<Consomable, Integer> getConsomableToUse() {
        return consomableNeeded;
    }

    public String getName() {
        return name;
    }

    public double getPriceTTC() {
        return priceTTC;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", priceHT=" + priceHT +
                ", priceTTC=" + priceTTC +
                ", composedWith=" + composedWith +
                ", consomableNeeded=" + consomableNeeded +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return composedWith.equals(product.composedWith) &&
                consomableNeeded.equals(product.consomableNeeded) &&
                name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(composedWith, consomableNeeded, name);
    }
}
