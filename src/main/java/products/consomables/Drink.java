package products.consomables;

import discount.Discount;
import discount.percentages.concrete.DayOffDiscount;
import products.Consomable;

import java.util.Map;

public class Drink extends Consomable {

    public Drink(String name, double v, Map<Consomable, Integer> consomableNeeded) {
        super(name, v);
        this.consomableNeeded = consomableNeeded;
    }

    @Override
    protected double calculatePrice() {
        return priceHT;
    }

    // Ne fais pas le job prévu dans le calcul du prix d'un order...
    @Override
    public double getDiscountedPrice(DayOffDiscount discount, double priceTTC) {
        return priceTTC;
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
}
