package Animaux;

import Carte.Coordonnee;
import Carte.Entite;
import Carte.Map;
import Enumeration.EnumModeDeVie;
import Enumeration.EnumSexe;
import Enumeration.EnumStade;
import java.util.List;


public abstract class Animal extends Entite {

	protected int dureeVie;

	protected EnumModeDeVie modeDeVie; /* model de vie: 1/3 Jeune, 1/3 adulte et 1/3 Vieux */

	protected EnumSexe sexe;

	protected int portee;
	
	protected int faim;//Faim de 0 a 100, 0 il n'a pas faim, 100 il a trop faim;

	protected int rayon;

	protected int fatigue;//Fatigue de  0 a 100, parei faim;

	protected EnumStade stade;
	
	public Animal(){
		super();

		this.dureeVie = (int) (Math.random()*20+80);
	
		//sexe random
		this.sexe = (Math.random()>0.5)?EnumSexe.male:EnumSexe.femelle;
		
		//Nombre max des enfants
		this.portee = 5 ; 
		
		this.faim = 0;
		
		//Definir les rayon (max, min) scale.
		this.rayon = 5; 
		
		this.fatigue = 0;
		
		this.stade = EnumStade.jeune;
	}
	
	Animal(int dureeVie
			,EnumModeDeVie modedevie
			,EnumSexe sexe
			,int portee
			,int faim
			,int rayon
			,int fatigue
			,int age
			,int valeurEnergetic
			,int x
			,int y){
		
		super(valeurEnergetic, age, x, y);
		
		this.dureeVie = dureeVie;
		
		this.faim = faim;
		
		this.fatigue = fatigue;
		
		this.modeDeVie = modedevie;
		
		this.portee = portee;
		
		this.rayon = rayon;
		
		this.sexe = sexe;
		
		this.stade = EnumStade.jeune;
		}//fin contructeur
	
	Animal(int dureeVie
			,EnumModeDeVie modedevie
			,EnumSexe sexe
			,int portee
			,int faim
			,int rayon
			,int fatigue
			,int age
			,int valeurEnergetic
			,Coordonnee coordonnee){
		
		super(valeurEnergetic, age, coordonnee);
		
		this.dureeVie = dureeVie;
		
		this.faim = faim;
		
		this.fatigue = fatigue;
		
		this.modeDeVie = modedevie;
		
		this.portee = portee;
		
		this.rayon = rayon;
		
		this.sexe = sexe;
		
		this.stade = EnumStade.jeune;
		}//fin contructeur

	protected abstract void deplacement(Coordonnee nouveauPosition);

	protected abstract List<Animal> reproduction(Coordonnee coord);

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

	public abstract void live(Map map);

	protected List<Entite> perception(Coordonnee coord, int ray, Map map)//le resultat est une liste avec tout les entites prochaines
	{
		List <Entite> entitesProchaine = map.perception(coord, ray); //Reviser quand utilise cet List
		return entitesProchaine;
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

	public int getDureeVie() {
		return dureeVie;
	}

	public void setDureeVie(int dureeVie) {
		this.dureeVie = dureeVie;
	}

	public EnumModeDeVie getModeDeVie() {
		return modeDeVie;
	}

	public void setModeDeVie(EnumModeDeVie modeDeVie) {
		this.modeDeVie = modeDeVie;
	}

	public EnumSexe getSexe() {
		return sexe;
	}

	public void setSexe(EnumSexe sexe) {
		this.sexe = sexe;
	}

	public int getPortee() {
		return portee;
	}

	public void setPortee(int portee) {
		this.portee = portee;
	}

	public int getRayon() {
		return rayon;
	}

	public void setRayon(int rayon) {
		this.rayon = rayon;
	}

	public EnumStade getStade() {
		return stade;
	}

	public void setStade(EnumStade stade) {
		this.stade = stade;
	}

}
