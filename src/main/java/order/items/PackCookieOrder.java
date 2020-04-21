package order.items;

import products.recipes.Recipe;

public class PackCookieOrder extends ProductOrder {
    PackCookieOrder.TYPE type;

    public PackCookieOrder(TYPE type, Recipe recipe, boolean isApack) {
        super(recipe, type.getTaille(), isApack, type.getname());
        this.type = type;
    }

    public String getName() {
        return type.getname();
    }

    public int getTaille() {
        return type.getTaille();
    }

    public enum TYPE {
        PETIT("PETIT", 3, 4),
        MOYEN("MOYEN", 6, 6),
        GRAND("GRAND", 10, 9);

        private String name = "";
        private int taille = 0;
        private double price = 0;

        //Constructeur
        TYPE(String name, int taille, double price) {
            this.name = name;
            this.taille = taille;
            this.price = price;
        }

        public String getname() {
            return this.name;
        }

        public double getPrice() {
            return this.price;
        }

        public int getTaille() {
            return this.taille;
        }

    }

}
