package Animaux;

import Animaux.interfaces.Carnivorable;
import Carte.Cordonnee;

public class Loup extends Animal implements Carnivorable {


	/**
	 * @see Animaux.interface.Carnivorable#manger(Animaux.Animal)
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
