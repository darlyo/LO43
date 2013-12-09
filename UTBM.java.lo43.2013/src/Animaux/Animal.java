package Animaux;

import Carte.Cordonnee;
import Carte.Entite;
import Enumeration.EnumModeDeVie;
import Enumeration.EnumSexe;
import Enumeration.EnumStade;

public abstract class Animal extends Entite {

	protected int dureeVie;

	protected EnumModeDeVie modeDeVie; /* model de vie: 1/3 Jeune, 1/3 adulte et 1/3 Vieux */

	protected EnumSexe sexe;

	protected int portee;

	protected int faim;

	protected int rayon;

	protected int fatigue;

	protected EnumStade stade;
	
	public Animal(){
		//this.cordonee(int x,int y,int z);
	
		this.age = 0;
		//80 <= Duree de Vie <= 100: random()*(N-M)+M
		this.dureeVie = (int) (Math.random()*20+80);
	
		//sexe random
		this.sexe = (Math.random()>0.5)?EnumSexe.male:EnumSexe.femelle;
		
		this.portee = 5 ; //qu'est-ce que c'est?
		
		this.faim = 0;
		
		this.rayon = 5; //Definir les rayon (max, min) scale.
		
		this.fatigue = 0;
		
		this.stade = EnumStade.jeune;
	}
	
	Animal(int dureeVie,EnumModeDeVie modedevie, EnumSexe sexe, int portee, int faim, int rayon, int fatigue){
		this.age = 0;
		//this.cordonee();
		this.dureeVie = dureeVie;
		
		this.faim = faim;
		
		this.fatigue = fatigue;
		
		this.modeDeVie = modedevie;
		
		this.portee = portee;
		
		this.rayon = rayon;
		
		this.sexe = sexe;
		
		this.stade = EnumStade.jeune;
		
		this.valeurEnergetique = 15; //Il faut corriger cet valeur et creer la scale de valeurs posibles.
	}

	protected abstract void deplacement(Cordonnee nouveauPosition);

	protected abstract void reproduction();

	protected void grandir() {
		/* Augmenter l'age */
		this.age = this.age + 1;
		
		/*Changer de stade*/
		if (this.age/this.dureeVie > 1/3)
			this.stade = EnumStade.jeune;
		else 
			if (this.age /this.dureeVie < 2/3)
				this.stade = EnumStade.adulte;
			else
				this.stade = EnumStade.vieux;
	}

	public abstract void live();

	protected void perception() {

	}

	protected void reposer(int temp) {
		/*
		 * If faut changer ce methode, il faut avoir un timer.swing
		 * L'animal ne peut rien faire pendant il dort
		 *fatigue et temp ont la même unite de meassure. 
		 */
		this.fatigue=this.fatigue-temp;
	}
	
	//Geters y Seters de fatigue et faim
	public void setFatigue(int x){
		this.fatigue = x;
	}
	
	public int getFatigue(){
		return this.fatigue;
	}
	
	public void setFaim(int x){
		this.faim = x;
	}
	
	public int getFaim(){
		return this.faim;
	}

}
