package Carte;

import Enumeration.EnumNourriture;

;

public class Nourriture {

	private int quantite;
	private EnumNourriture type;

	/**
	 * Constructeurs
	 */
	public Nourriture(int quantite, EnumNourriture type) {
		this.quantite = quantite;
		this.type = type;
	}

	/**
	 * Getters et setters
	 */
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public int getQuantite() {
		return this.quantite;
	}

	public void setType(EnumNourriture type) {
		this.type = type;
	}

	public EnumNourriture getType() {
		return this.type;
	}
}
