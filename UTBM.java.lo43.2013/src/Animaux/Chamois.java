package Animaux;

import java.util.ArrayList;
import java.util.List;
import Animaux.interfaces.Vegetarienable;
import Carte.Coordonnee;
import Vegetaux.Vegetal;


public class Chamois extends Animal implements Vegetarienable {
	/**
	 * @see Animaux.interface.Vegetarienable#manger(Vegetal)
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
			enfants.add(new Chamois());
		}
		return enfants;				
	}

	@Override
	public void live() {
		// TODO Auto-generated method stub
		
	}

}
