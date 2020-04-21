package order.items;

import main.BDSingleton;
import products.Consomable;
import products.recipes.CookTechnique;
import products.recipes.CustomRecipe;
import products.recipes.Recipe;
import store.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFactory {

    BDSingleton BD = BDSingleton.getSINGLETON();
    int nombre_Pack = 0;

    /* cette méthode prend des defaultRecipe et le nombre de ces recipe meme chose pour les boissons et nous construit
     une list d'ordre en fonction du nombre de defaultrecipe passé les order peuvent soit etre des simple order ou des pack  */


    public List<ProductOrder> GetOrderList(HashMap<String, Integer> Boisson, Store store, HashMap<String, Integer> DefaultRecipe) {
        List<ProductOrder> All_Order = new ArrayList<>();
        All_Order = GetDefaultRecipeOrder(DefaultRecipe, All_Order, store);
        GetDrinkOrder(Boisson, All_Order, store);
        return All_Order;
    }

    /* cette méthode prend des defaultRecipe et le nombre de ces recipe meme chose pour les boissons et nous construit et aussi des list des consomable et produit pour créer un Recipe personalisé
     une list d'ordre en fonction du nombre de defaultrecipe passé les order peuvent soit etre des simple order ou des pack  */


    public List<ProductOrder> GetOrderList(Store store, HashMap<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> cc, HashMap<String, Integer> DefaultRecipe) {
        int nombre_Pack = 0;
        List<ProductOrder> All_Order = new ArrayList<>();
        GetPersonalisedOrder(cc, All_Order);
        All_Order = GetDefaultRecipeOrder(DefaultRecipe, All_Order, store);
        return All_Order;
    }

    // une méthode qui prend un int et le décompose en 10,6,3 qui correspond à la taille des packs


    public List<Integer> decomposition_cookie(int i) {
        List<Integer> decomposition = new ArrayList<>();
        decomposition.add(0, i / 10);
        i = i % 10;
        decomposition.add(1, i / 6);
        i = i % 6;
        decomposition.add(2, i / 3);
        decomposition.add(3, i % 3);
        return decomposition;
    }

    // cette méthode nous construit des DrinkOrder en fonction du nombre de pack et le nombre de boisson


    public void GetDrinkOrder(HashMap<String, Integer> Boisson, List<ProductOrder> All_Order, Store store) {
        for (Map.Entry<String, Integer> entry : Boisson.entrySet()) {
            int nombre_boisson = entry.getValue();
            if (nombre_boisson <= this.nombre_Pack) {
                All_Order.add(new ProductOrder(BD.getDrinkByName(entry.getKey()), entry.getValue(), true, "Drink_in_pack"));
                this.nombre_Pack -= nombre_boisson;
            }
            if (this.nombre_Pack == 0) {
                All_Order.add(new ProductOrder(BD.getDrinkByName(entry.getKey()), entry.getValue()));
            } else {
                All_Order.add(new ProductOrder(BD.getDrinkByName(entry.getKey()), this.nombre_Pack, true, "Drink_in_pack"));
                All_Order.add(new ProductOrder(BD.getDrinkByName(entry.getKey()), entry.getValue() - nombre_Pack));
            }
        }
    }
    // cette méthode nous construit des DefaultRecipeOrder en fonction du nombre du DefaultRecipe


    public List<ProductOrder> GetDefaultRecipeOrder(HashMap<String, Integer> DefaultRecipe, List<ProductOrder> All_Order, Store store) {
        for (Map.Entry<String, Integer> entry : DefaultRecipe.entrySet()) {
            Recipe defaultrecipe = BD.getDefaultRecipeByName(entry.getKey());
            int nombre_Recipe = entry.getValue();
            if (nombre_Recipe > 3) {
                List<Integer> N = decomposition_cookie(entry.getValue());
                for (int i = 0; i <= 2; i++) {
                    System.out.println(N.get(i));
                    if (N.get(i) != 0) {
                        switch (i) {
                            case 0:
                                for (int t = 0; t < N.get(i); t++) {
                                    PackCookieOrder P1 = new PackCookieOrder(PackCookieOrder.TYPE.GRAND, defaultrecipe, true);
                                    All_Order.add(P1);
                                    this.nombre_Pack += 1;

                                }
                                break;
                            case 1:
                                for (int t = 0; t < N.get(i); t++) {
                                    PackCookieOrder P2 = new PackCookieOrder(PackCookieOrder.TYPE.MOYEN, defaultrecipe, true);
                                    All_Order.add(P2);
                                    this.nombre_Pack += 1;

                                }
                                break;
                            case 2:
                                for (int t = 0; t < N.get(i); t++) {
                                    PackCookieOrder P3 = new PackCookieOrder(PackCookieOrder.TYPE.PETIT, defaultrecipe, true);
                                    All_Order.add(P3);
                                    this.nombre_Pack += 1;
                                }
                                break;
                            default:
                                System.out.println();
                        }
                    }
                }
            } else {
                SimpleCookieOrder SO = new SimpleCookieOrder(defaultrecipe, nombre_Recipe);
                All_Order.add(SO);
            }
        }
        return All_Order;
    }


    // cette méthode nous construit des PersonalisedOrder en fonction du nombre des Recipe personnalisé passé en paramètre


    public void GetPersonalisedOrder(HashMap<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> cc, List<ProductOrder> All_Order) {
        for (Map.Entry<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> entry : cc.entrySet()) {
            CustomRecipe CR = new CustomRecipe(entry.getKey().getKey(), entry.getKey().getValue());
            int nombre_Recipe = entry.getValue();
            if (nombre_Recipe > 3) {
                List<Integer> N = decomposition_cookie(nombre_Recipe);
                for (int j = 0; j <= 2; j++) {
                    if (N.get(j) != 0) {
                        switch (j) {
                            case 0:
                                for (int w = 0; w < N.get(w); w++) {
                                    PackCookieOrder P1 = new PackCookieOrder(PackCookieOrder.TYPE.GRAND, CR, true);
                                    All_Order.add(P1);
                                    this.nombre_Pack++;

                                }
                                break;
                            case 1:
                                for (int w = 0; w < N.get(w); w++) {
                                    PackCookieOrder P2 = new PackCookieOrder(PackCookieOrder.TYPE.MOYEN, CR, true);
                                    All_Order.add(P2);
                                    this.nombre_Pack++;

                                }
                                break;
                            case 2:
                                for (int w = 0; w < N.get(w); w++) {
                                    PackCookieOrder P3 = new PackCookieOrder(PackCookieOrder.TYPE.PETIT, CR, true);
                                    All_Order.add(P3);
                                    this.nombre_Pack++;

                                }
                                break;
                        }
                    }
                }
            } else {
                SimpleCookieOrder SO = new SimpleCookieOrder(CR, nombre_Recipe);
                All_Order.add(SO);
            }
        }
    }
}

