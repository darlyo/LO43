package Entite.Animaux;

import java.util.ArrayList;
import java.util.List;

import Carte.Coordonnee;
import Carte.Map;
import Entite.Animaux.interfaces.Carnivorable;
import Enumeration.EnumModeDeVie;
import Enumeration.EnumSexe;

public class Loup extends Animal implements Carnivorable {


	/**
	 * @see Animaux.interface.Carnivorable#manger(Animaux.Animal)
	 */
	
	public Loup(){
		super();
		this.modeDeVie=EnumModeDeVie.terrestre;	
		this.valeurEnergetique = 80;
		this.portee = 5;
	}
	
	public Loup(int dureeVie
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
		this.valeurEnergetique = 80;
		this.portee = 5;
	}
	
	public Loup(int dureeVie
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
		this.valeurEnergetique = 80; //valeurEnergetique entre 0 et 100
		this.portee = 5; //nro max des enfants
	}
	public void manger(Animal animal) {
		int quantiteaManger;
		
		if (animal.valeurEnergetique > this.faim)
			quantiteaManger = this.faim;
		else 
			quantiteaManger = animal.valeurEnergetique;
		
		/* Animal est mort */
		animal.delete();
		
		/* Après manger il a mois faim */
		this.faim = this.faim -quantiteaManger;
		
		/*Apres manger il a plus fatigue*/
		this.fatigue = this.fatigue + quantiteaManger;

	}

	@Override
	protected void deplacement(Coordonnee nouveauPosition) {
		// Distance entre deux points
		double distance = this.coordonee.distance(nouveauPosition);
		// Augmente la fatigue
		this.fatigue = (int) (this.fatigue + distance);
		this.coordonee = nouveauPosition;
		
	}

	

	@Override
	public void live(Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<Animal> reproduction(Coordonnee coord) 
	{
		//Nombre des enfants
				int nombreEnfants = (int) (Math.random()*(portee));
				//Créer des enfans
				List<Animal> enfants = new ArrayList <Animal>();
				for (int i= 0; i<nombreEnfants;i++)
				{
					enfants.add(new Loup());
					enfants.get(i).setCoordonee(coord);
				}
				return enfants;
	}

}
