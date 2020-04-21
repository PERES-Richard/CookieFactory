package products;

import main.BDSingleton;
import products.consomables.Drink;
import products.recipes.CookTechnique;
import products.recipes.CustomRecipe;
import products.recipes.DefaultRecipe;

import java.util.Map;

public class ProductFactory {

    private boolean dough;
    private boolean flavour;
    private boolean topping;

    private boolean mix;
    private boolean cooking;

    public ProductFactory() {
        dough = false;
        flavour = false;
        topping = false;

        mix = false;
        cooking = false;
    }

    public CustomRecipe build(Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>> cookie) {
        checkCookTech(cookie.getKey());
        checkConso(cookie.getValue());
        clear();

        return new CustomRecipe(cookie.getKey(), cookie.getValue());
    }

    public DefaultRecipe build(Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>> cookie, String name) {
        checkCookTech(cookie.getKey());
        checkConso(cookie.getValue());
        clear();

        return new DefaultRecipe(cookie.getKey(), cookie.getValue(), name);
    }

    private void clear() {
        dough = false;
        flavour = false;
        topping = false;

        mix = false;
        cooking = false;
    }

    private void checkCookTech(Map<CookTechnique, Integer> cookTechs) {
        if (cookTechs.size() != 2) {
            try {
                throw new Exception("Custom recip must have 2 cooktechniques" + cookTechs.size() + " provided.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Integer value : cookTechs.values()) {
            if (value != 1) {
                try {
                    throw new Exception("Custom recip must have 2 cooktechniques with 1 occurence" + value + "occurences provided.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        for (CookTechnique cookTechnique : cookTechs.keySet()) {
            if (BDSingleton.getSINGLETON().getMixs().contains(cookTechnique))
                mix = true;
            else if (BDSingleton.getSINGLETON().getCookings().contains(cookTechnique))
                cooking = true;
            else {
                try {
                    throw new Exception("Not a correct cooktechnique '" + cookTechnique + "'");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (!(mix && cooking)) {
            try {
                throw new Exception("Error: need 2 specifics cooktechniques.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkConso(Map<Consomable, Integer> consos) {
        if (consos.size() != 3) {
            try {
                throw new Exception("Custom recip must have 3 consommables" + consos.size() + " provided.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Integer value : consos.values()) {
            if (value != 1) {
                try {
                    throw new Exception("Custom recip must have 3 consommables with 1 occurence" + value + "occurences provided.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        for (Consomable conso : consos.keySet()) {

            if (BDSingleton.getSINGLETON().getToppings().contains(conso))
                topping = true;
            else if (BDSingleton.getSINGLETON().getDoughs().contains(conso))
                dough = true;
            else if (BDSingleton.getSINGLETON().getFlavours().contains(conso))
                flavour = true;
            else {
                try {
                    throw new Exception("Not a correct consommable '" + conso + "'");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (!(dough && flavour && topping)) {
            try {
                throw new Exception("Error: need 3 specifics consommables.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public DefaultRecipe buildDefaultRecipe(String name) {
        return BDSingleton.getSINGLETON().getDefaultRecipeByName(name);
    }


    public Drink buildDrink(String name) {
        return BDSingleton.getSINGLETON().getDrinkByName(name);
    }
}
