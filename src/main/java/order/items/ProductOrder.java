package order.items;

import discount.Discount;
import discount.percentages.concrete.DayOffDiscount;
import products.Consomable;
import products.Product;

import java.util.Map;

public class ProductOrder {

    public final Product product;
    public final int quantity;
    public double priceHT;
    public double priceTTC;
    public boolean isApack = false;
    public String typeDupack = "n'est pas un pack";

    public ProductOrder(Product product, int quantity, double priceHT, double tax) {
        this.product = product;
        this.quantity = quantity;
        this.priceHT = priceHT;
        this.priceTTC = priceHT + priceHT * tax;
    }

    public ProductOrder(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.priceHT = product.getPriceHT() * quantity;
        this.priceTTC = product.getPriceTTC() * quantity;
    }

    public ProductOrder(Product product, int quantity, boolean isApack, String typePack) {
        this.product = product;
        this.quantity = quantity;
        this.priceHT = product.getPriceHT() * quantity;
        this.priceTTC = product.getPriceTTC() * quantity;
        this.isApack = isApack;
        this.typeDupack = typePack;
    }

    public Map<Consomable, Integer> getConsomablesToUse() {
        return product.getConsomableToUse();
    }

    public double getPriceHT() {
        return priceHT;
    }

    public double getPriceTTC() {
        return priceTTC;
    }

    public double getDiscountedPrice(Discount discount) {
        return this.product.getDiscountedPrice(discount, this.priceTTC);
    }

    public double getDiscountedPrice(DayOffDiscount discount) {
        return this.product.getDiscountedPrice(discount, this.priceTTC);
    }

    public Product getproduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", priceHT=" + priceHT +
                ", priceTTC=" + priceTTC +
                ",Pack=" + isApack +
                ",TypePack=" + typeDupack +
                '}';
    }
}
