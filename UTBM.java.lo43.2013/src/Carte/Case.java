package Carte;

import Enumeration.EnumEnvironnement;

public class Case {

	private EnumEnvironnement environnement;
	protected Coordonnee coordonnee;
	protected boolean vide;

	/**
	 * Constructeurs
	 */
	public Case(){
		this.environnement= EnumEnvironnement.plaine; 
		this.vide = true;
	}
	
	public Case(Coordonnee coordonnee, EnumEnvironnement environnement, boolean vide){
		this.coordonnee = coordonnee;
		this.environnement = environnement;
		this.vide = true;
	}
	
	
	/**
	 * Getters et setters
	 */
	public EnumEnvironnement getEnvironnement (){
		return this.environnement;
	}
	public void setEnvironnement(EnumEnvironnement environnement){
		this.environnement = environnement;
	}
	public Boolean getVide(){
		return this.vide;
	}
	public void setVide(Boolean vide){
		this.vide = vide;
	}
	
}
