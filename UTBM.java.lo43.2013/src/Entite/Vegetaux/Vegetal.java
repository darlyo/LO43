package Entite.Vegetaux;

import Carte.Coordonnee;
import Entite.Entite;

public abstract class Vegetal extends Entite {

	protected int cycle;

	protected int quantiteMax;//quantite max de fruit 
	
	private int quantiteNow;//quantite de fruit maintenant
	

	
	public Vegetal() {
		super();
		this.quantiteNow = 0;
		this.cycle = 5;
	}

	public Vegetal(int valeurEnergetique, int age, Coordonnee coordonnee) {
		super(valeurEnergetique, age, coordonnee);
		this.quantiteNow = 0;
	}

	public Vegetal(int valeurEnergetic, int age, int x, int y) {
		super(valeurEnergetic, age, x, y);
		this.quantiteNow = 0;
	}

	public void produire() {
		this.setQuantiteNow(this.quantiteMax);

	}
	
	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	
	public void setQuantiteMax(int x){
		this.quantiteMax = x;
	}
	
	public int getQuantiteMax(){
		return this.quantiteMax;
	}

	
	public int getQuantiteNow() {
		return quantiteNow;
	}

	
	public void setQuantiteNow(int quantiteNow) {
		this.quantiteNow = quantiteNow;
	}

	
}
