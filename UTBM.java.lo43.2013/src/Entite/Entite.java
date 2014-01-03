package Entite;

import Carte.Coordonnee;
import Carte.Map;

public abstract class Entite {
    //atributes
        public int valeurEnergetique;// de 0 a 100.

        protected int age;
        
        public Coordonnee coordonee = new Coordonnee();//composition (class de dans une class)

        //constructeurs
        public Entite(){
                this.valeurEnergetique = 0;
                this.age = 0;
                this.coordonee = new Coordonnee();
        }
        
        public Entite(int valeurEnergetic, int age, int x, int y){
                this.valeurEnergetique = valeurEnergetic;
                this.age = age;
                this.coordonee = new Coordonnee(x,y);
        }
        
        public Entite(int valeurEnergetique, int age, Coordonnee coordonnee){
                this.valeurEnergetique = valeurEnergetique;
                this.age = age;
                this.coordonee = coordonnee;
        }
        
        //methodes
        public void delete() {}
        
        public int getValeurEnergetique() {
                return valeurEnergetique;
        }

        public void setValeurEnergetique(int valeurEnergetique) {
                this.valeurEnergetique = valeurEnergetique;
        }

        public int getAge() {
                return age;
        }

        public void setAge(int age) {
                this.age = age;
        }

        public Coordonnee getCoordonnee() {
                return this.coordonee;
        }
        
        public void setCoordonee(Coordonnee coord){
                this.coordonee.setX(coord.getX());
                this.coordonee.setY(coord.getY());
        }
        
        public abstract void live(Map map); 
}