package Animaux;

import java.util.ArrayList;
import java.util.List;

import Vegetaux.Vegetal;
import Animaux.interfaces.Vegetarienable;
import Carte.Coordonnee;
import Carte.Map;
import Enumeration.EnumModeDeVie;
import Enumeration.EnumSexe;

public class Mouton extends Animal implements Vegetarienable {


	/**
	 * @see Animaux.interface.Vegetarienable#manger(Vegetal)
	 */
	
	public Mouton(){
		super();
		this.modeDeVie=EnumModeDeVie.terrestre;	
		this.valeurEnergetique = 100;
		this.portee = 6;
	}
	
	public Mouton(int dureeVie
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
		super(dureeVie
				,modedevie
				,sexe
				,portee
				,faim
				,rayon
				,fatigue
				,age
				,valeurEnergetic
				,x
				,y);
		this.modeDeVie=EnumModeDeVie.terrestre;	
		this.valeurEnergetique = 100;
		this.portee = 6;
	}
	
	public Mouton(int dureeVie
			,EnumModeDeVie modedevie
			,EnumSexe sexe
			,int portee
			,int faim
			,int rayon
			,int fatigue
			,int age
			,int valeurEnergetic
			,Coordonnee coordonnee)
	{
		super(dureeVie
				,modedevie
				,sexe
				,portee
				,faim
				,rayon
				,fatigue
				,age
				,valeurEnergetic
				,coordonnee);
		this.modeDeVie=EnumModeDeVie.terrestre;
		this.valeurEnergetique = 100; //valeurEnergetique entre 0 et 100
		this.portee = 6; //nro max des enfants
	}
	public void manger(Vegetal vegetal) {
		int quantiteaManger;
		/* On ne peut pas manger plus que la quantite disponible
		 * on va dir aussi que les unités 
		 * de faim, quantite et fatigue sont égales*/
		if (this.faim > vegetal.getQuantiteNow())
			 quantiteaManger= vegetal.getQuantiteNow();
		else
			quantiteaManger= this.faim;
		
		/* Reduce la quantite vegetal */
		vegetal.setQuantiteNow(vegetal.getQuantiteNow()-quantiteaManger);
		
		/* Après manger il a mois faim */
		this.faim = this.faim -quantiteaManger;
		
		/*Apres manger il a plus fatigue*/
		this.fatigue = this.fatigue + quantiteaManger;

	}

	@Override
	protected void deplacement(Coordonnee nouveauPosition) {
		// TODO Auto-generated method stub
		// Distance entre deux points
		double distance = this.coordonee.distance(nouveauPosition);
		// Augmente la fatigue
		this.fatigue = (int) (this.fatigue + distance);
		this.coordonee = nouveauPosition;
		
	}

	@Override
	protected List<Animal> reproduction() {
		// TODO Auto-generated method stub
		//Nombre des enfants
		int nombreEnfants = (int) (Math.random()*(portee));
		//Créer des enfans
		List<Animal> enfants = new ArrayList <Animal>();
		for (int i= 0; i<nombreEnfants;i++){
			enfants.add(new Mouton());
		}
		return enfants;				
	}

	@Override
	public void live(Map map) {
		// TODO Auto-generated method stub
		
	}

}
