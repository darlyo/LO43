package Animaux;

import Animaux.interfaces.Omnivorable;
import Carte.Cordonnee;
import Vegetaux.Vegetal;

public class Sanglier extends Animal implements Omnivorable {


	/**
	 * @see Animaux.interface.Omnivorable#manger(Animaux.Animal)
	 */
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


	/**
	 * @see Animaux.interface.Omnivorable#manger(Vegetal)
	 */
	public void manger(Vegetal vegetal) {
		int quantiteaManger;
		/* On ne peut pas manger plus que la quantite disponible
		 * on va dir aussi que les unités 
		 * de faim, quantite et fatigue sont égales*/
		if (this.faim > vegetal.getQuantite())
			 quantiteaManger= vegetal.getQuantite();
		else
			quantiteaManger= this.faim;
		
		/* Reduce la quantite vegetal */
		vegetal.setQuantite(vegetal.getQuantite()-quantiteaManger);
		
		/* Après manger il a mois faim */
		this.faim = this.faim -quantiteaManger;
		
		/*Apres manger il a plus fatigue*/
		this.fatigue = this.fatigue + quantiteaManger;

	}


	@Override
	protected void deplacement(Cordonnee nouveauPosition) {
		// TODO Auto-generated method stub
		// Distance entre deux points
		double distance = this.cordonee.distance(nouveauPosition);
		// Augmente la fatigue
		this.fatigue = (int) (this.fatigue + distance);
		this.cordonee = nouveauPosition;
		
	}


	@Override
	protected void reproduction() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void live() {
		// TODO Auto-generated method stub
		
	}

}
