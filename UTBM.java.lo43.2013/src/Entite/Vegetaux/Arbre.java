package Entite.Vegetaux;

import Carte.Coordonnee;

public class Arbre extends Vegetal {

        public Arbre() {
                super();
                this.valeurEnergetique = 80;
                this.quantiteMax = 70;
        }

        public Arbre(int valeurEnergetique, int age, Coordonnee coordonnee) {
                super(valeurEnergetique, age, coordonnee);
                this.valeurEnergetique = 80;
                this.quantiteMax = 70;
        }

        public Arbre(int valeurEnergetic, int age, int x, int y) {
                super(valeurEnergetic, age, x, y);
                this.valeurEnergetique = 80;
                this.quantiteMax = 70;
        }

        

}