package Animaux;

import java.util.ArrayList;
import java.util.List;

import Animaux.interfaces.Carnivorable;
import Carte.Coordonnee;

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
			enfants.add(new Loup());
		}
		return enfants;				
	}

	@Override
	public void live() {
		// TODO Auto-generated method stub
		
	}

}
