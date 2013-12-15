package Carte;

import Enumeration.EnumEnvironnement;


public class Case {

	private EnumEnvironnement environnement;
	protected Coordonnee coordonnee;
	protected boolean vide;

	/**
	 * Constructeurs
	 */
	public Case (){
		
	}
	public Case(Coordonnee coordonnee) {
		this.coordonnee = coordonnee;
		this.environnement = EnumEnvironnement.plaine;
		this.vide = vide;
	}

	public Case (int x, int y){
		this.coordonnee.setX(x);
		this.coordonnee.setY(y);
	}
	public Case(Coordonnee coordonnee, EnumEnvironnement environnement, boolean vide) {
		this.coordonnee = coordonnee;
		this.environnement = environnement;
		this.vide = vide;
	}

	/**
	 * Getters et setters
	 */
	public EnumEnvironnement getEnvironnement() {
		return this.environnement;
	}

	public void setEnvironnement(EnumEnvironnement environnement) {
		this.environnement = environnement;
	}

	public Boolean getVide() {
		return this.vide;
	}

	public void setVide(Boolean vide) {
		this.vide = vide;
	}

}
