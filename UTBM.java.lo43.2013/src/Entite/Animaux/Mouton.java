package Entite.Animaux;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Carte.Coordonnee;
import Carte.Map;
import Entite.Entite;
import Entite.Animaux.interfaces.Vegetarienable;
import Entite.Vegetaux.Vegetal;
import Enumeration.EnumModeDeVie;
import Enumeration.EnumSexe;
import Enumeration.EnumStade;

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
		if (this.getFaim() > vegetal.getQuantiteNow())
			 quantiteaManger= vegetal.getQuantiteNow();
		else
			quantiteaManger= this.getFaim();
		
		/* Reduce la quantite vegetal */
		vegetal.setQuantiteNow(vegetal.getQuantiteNow()-quantiteaManger);
		
		/* Après manger il a mois faim */
		this.setFaim(this.getFaim() -quantiteaManger);
		
		/*Apres manger il a plus fatigue*/
		this.setFatigue(this.getFatigue() + quantiteaManger);

	}

	@Override
	public void live(Map map) {
		//recuperer tout les entites prochaines (animaux ou vegetal)
				List<Entite> entitesProchaines = this.perception(this.getCoordonnee(), this.getRayon(), map);
				Coordonnee nouveauPosition = new Coordonnee();
				if (this.getFaim() >= 80 ) //L'animal a faim quand faim > = 80;
				{		
					//recherche le répas;
					for (int i = 0; i < entitesProchaines.size();i++)
					{
						//comme il vegetarien, on cherche un vegetal
						if(entitesProchaines.get(i) instanceof Vegetal)
						{
							//recupere les coordonnee du Vegetal
							nouveauPosition = entitesProchaines.get(i).getCoordonnee();
							if (nouveauPosition != this.getCoordonnee())
								this.deplacement(nouveauPosition);//l'animal se deplace en direction au vegetal
							else //la norriture et l'animal se trouvent dans le même case
								this.manger((Vegetal) entitesProchaines.get(i));//manger le vegetaux
						}
						else //il a faim mais il n'y a pas de repas => mouvement a l'hazard
							{
								this.deplacementHasard();//Desplazamiento al hasard
							}
					}
				}
				else //il n'a pas faim 
					{
						if (this.getFatigue() > 50) // A-t-il fatigue?
						{
							this.reposer(this.getFatigue());//comme ca, fatigue arrive à 0 après de reposer
						}
						else //s'el n'a pas faim et il n'est pas fatigue => il peut se reproduire?
							if (this.getStade() != EnumStade.jeune) //si l'animal est jeune il ne peut pas se reprodruire
							{
								for (int i = 0; i < entitesProchaines.size(); i++)
								{
									//recherche d'autre animaux, il faut chercher le meme sous classe d'animaux
									if (entitesProchaines.get(i) instanceof Mouton)						
									{
										Mouton couple = (Mouton) entitesProchaines.get(i);
										if (couple.getSexe() != this.getSexe() && couple.getStade()!= EnumStade.jeune)
										{
											//recupere les coordonnee d'animal
											nouveauPosition = couple.getCoordonnee();
											if (this.getCoordonnee() != nouveauPosition)
											{
												this.deplacement(nouveauPosition);//se déplacer jusqu'au l'autre animaux
											}
											else 
											{
											List<Animal> enfants = this.reproduction();// se reproduire
											//cet liste des enfants il faut l'ajouter dans la liste des animaux 
											Iterator<Animal> it = enfants.iterator();
											while (it.hasNext())
											{
												Entite entite = (Entite) it.next();
												Map.ajoutEntite(entite);
											}
											}
										}
									}
								}
							}	 
							else // Il n'ai pas faim, il n'est pas fatigué, et il ne peux pas se reproduire => movement a l'hazard
								this.deplacementHasard();
							
					}
					this.grandir();
					if (this.getAge() > this.getDureeVie()) 
						this.delete();
				}

	@Override
	protected List<Animal> reproduction() {
		//Nombre des enfants
				int nombreEnfants = (int) (Math.random()*(portee));
				//Créer des enfans
				List<Animal> enfants = new ArrayList <Animal>();
				for (int i= 0; i<nombreEnfants;i++)
				{
					enfants.add(new Mouton());
					enfants.get(i).setCoordonnee(this.getCoordonnee());
				}
				return enfants;
	}

}
