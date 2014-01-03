package Entite.Vegetaux;

import Carte.Coordonnee;

public class Herbe extends Vegetal{

	public Herbe() {
		super();
		this.valeurEnergetique = 50;
		this.quantiteMax = 20;
	}

	public Herbe(int valeurEnergetique, int age, Coordonnee coordonnee) {
		super(valeurEnergetique, age, coordonnee);
		this.valeurEnergetique = 50;
		this.quantiteMax = 20;
	}

	public Herbe(int valeurEnergetic, int age, int x, int y) {
		super(valeurEnergetic, age, x, y);
		this.valeurEnergetique = 50;
		this.quantiteMax = 20;
	}

	

}
