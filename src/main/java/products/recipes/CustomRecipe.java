package products.recipes;

import discount.Discount;
import discount.percentages.concrete.DayOffDiscount;
import products.Consomable;
import products.Product;

import java.util.Map;

public class CustomRecipe extends Recipe {

    public static final double MARGIN = 0.2;

    public CustomRecipe(Map<? extends Product, Integer> composedWith, Map<Consomable, Integer> consomableNeeded) {
        super((Map<Product, Integer>) composedWith, consomableNeeded);
    }

    @Override
    protected double calculateTTCPrice() {
        return this.priceHT += this.priceHT * MARGIN;
    }

    @Override
    public double getDiscountedPrice(Discount discount, double priceTTC) {
        // Pas trouvé d'autre moyens pour bien appliquer le discount de fin de journée
        // le problème à été identifié dans la méthode calculateDiscountePrice() de Order
        if (discount.getClass() == DayOffDiscount.class) {
            return priceTTC;
        }
        return super.getDiscountedPrice(discount, priceTTC);
    }

    public double getDiscountedPrice(DayOffDiscount discount, double priceTTC) {
        return priceTTC;
    }
}
