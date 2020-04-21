package main;

import exceptions.CannotOrderOutOfStockException;

public class Main {

    public static void main(String[] args) throws CannotOrderOutOfStockException {
        /**
         * TODO FIX TESTS
         * TODO factory pour les discount + string
         * TODO manager facade singleton
         * TODO factory de recipe/ProductOrder
         * TODO matcher nom/discount
         * TODO pack
         * TODO refractor test for store, order
         **/
//        BDSingleton singleton = BDSingleton.getSINGLETON();
//
//        System.out.println(singleton.getStores().get(0).getAdress() + " " + singleton.getStores().get(0).getTax());
//        System.out.println(singleton.getDefaultRecipeByName("MnMs").getPriceHT() + " " + singleton.getDefaultRecipeByName("MnMs").getPriceTTC());
//
//        singleton.getStoreByAddress("Hidafurukawa").addStock(singleton.getDefaultRecipeByName("mnms").getConsomableToUse());
//        singleton.getStoreByAddress("Hidafurukawa").addStock(singleton.getDefaultRecipeByName("mnms").getConsomableToUse());
//
//        Customer c = new Customer("test", "test");
//
//        HashMap<String, Integer> dc = new HashMap<String, Integer>() {{
//            put("mnms", 2);
//        }};
//
//        c.makeOrder(null, dc, null, null, "Hidafurukawa");
//        System.out.println(c.getOrders().get(c.getOrders().size() - 1).getPriceHT() + " " + c.getOrders().get(c.getOrders().size() - 1).getPriceTTC());
//
//        // ############
//
////        ingredients.add(new Dough("Peanut butter", 2.0));
////        ingredients.add(new Topping("MnMs", 2.5));
////        ingredients.add(new Flavour("Chili", 2.2));
////        cookTechniques.add(new Mix("Topped", 1.3));
////        cookTechniques.add(new Cooking("Crunchy", 1.3));
//
//        HashMap<CookTechnique, Integer> cooktec = new HashMap<CookTechnique, Integer>() {{
//            put(singleton.getCookTechniqueByName("Topped"), 1);
//            put(singleton.getCookTechniqueByName("Crunchy"), 1);
//        }};
//        HashMap<Consomable, Integer> conso = new HashMap<Consomable, Integer>() {{
//            put(singleton.getIngredientsByName("mnms"), 1);
//            put(singleton.getIngredientsByName("peanut butter"), 1);
//            put(singleton.getIngredientsByName("chili"), 1);
//        }};
//
//        singleton.getStoreByAddress("Hidafurukawa").addStock(conso);
//        singleton.getStoreByAddress("Hidafurukawa").addStock(conso);
//
//        HashMap<Map.Entry<Map<CookTechnique, Integer>, Map<Consomable, Integer>>, Integer> cc = new HashMap<>();
//
//        HashMap<Map<CookTechnique, Integer>, Map<Consomable, Integer>> cr = new HashMap<>();
//        cr.put(cooktec, conso);
//
//        cc.put(cr.entrySet().iterator().next(), 2);
//
//
//        c.makeOrder(null, null, cc, null, "Hidafurukawa");
//        System.out.println("\n" + c.getOrders().get(c.getOrders().size() - 1).getPriceHT() + " " + c.getOrders().get(c.getOrders().size() - 1).getPriceTTC());
//
//
//        Drink drink = singleton.getDrinkByName("Beer");
//
//        singleton.getStoreByAddress("Hidafurukawa").addStock(drink.getConsomableToUse());
//        singleton.getStoreByAddress("Hidafurukawa").addStock(drink.getConsomableToUse());
//
//        System.out.println(drink.getPriceTTC());
//        HashMap<String, Integer> dd = new HashMap<String, Integer>() {{
//            put("Beer", 2);
//        }};
//        c.makeOrder(dd, null, null, null, "Hidafurukawa");
//        System.out.println("\n" + c.getOrders().get(c.getOrders().size() - 1).getPriceHT() + " " + c.getOrders().get(c.getOrders().size() - 1).getPriceTTC());

    }

}
