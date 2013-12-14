package Entite.Vegetaux;

import Entite.Entite;

public abstract class Vegetal extends Entite {

	protected int cycle;

	protected int quantite;

	protected void produire() {

	}

	public void setQuantite(int x) {
		this.quantite = x;
	}

	public int getQuantite() {
		return this.quantite;
	}

}
