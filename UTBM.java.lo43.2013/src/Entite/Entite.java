package Entite;

import Carte.Coordonnee;


public abstract class Entite {

	public int valeurEnergetique;

	protected int age;
	
	public Coordonnee coordonee;

	public void delete() {

	}

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

	public Coordonnee getCoordonee() {
		return coordonee;
	}

	public void setCoordonee(Coordonnee coordonee) {
		this.coordonee = coordonee;
	}

	

}
