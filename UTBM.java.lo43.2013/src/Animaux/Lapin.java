package Animaux;

import java.util.ArrayList;
import java.util.List;

import Vegetaux.Vegetal;
import Animaux.interfaces.Vegetarienable;
import Carte.Coordonnee;
import Carte.Map;
import Enumeration.EnumModeDeVie;
import Enumeration.EnumSexe;
import Enumeration.EnumStade;
import Carte.Entite;

public class Lapin extends Animal implements Vegetarienable {


	/**
	 * @see Animaux.interface.Vegetarienable#manger(Vegetal)
	 */
	
	public Lapin(){
		super();
		this.modeDeVie=EnumModeDeVie.terrestre;	
		this.valeurEnergetique = 20;
		this.portee = 10;
	}
	
	public Lapin(int dureeVie
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
		this.valeurEnergetique = 20;
		this.portee = 10;
	}
	
	public Lapin(int dureeVie
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
		this.valeurEnergetique = 20; //valeurEnergetique entre 0 et 100
		this.portee = 10; //nro max des enfants
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
	protected void deplacement(Coordonnee nouveauPosition){
		// Distance entre deux points
		double distance = this.coordonee.distance(nouveauPosition);
		// Augmente la fatigue
		this.fatigue = (int) (this.fatigue + distance);
		this.coordonee = nouveauPosition;
		
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
			enfants.add(new Lapin());
			enfants.get(i).setCoordonee(coord);
		}
		return enfants;				
	}

	@Override
	public void live(Map map) {
		//recuperer tout les entites prochaines (animaux ou vegetal)
				List<Entite> entitesProchaines = this.perception(this.coordonee, this.rayon, map);
				Coordonnee nouveauPosition = new Coordonnee();
				//L'animal a faim quand faim > = 80;
				if (this.faim >= 80 ) 
				{		
					//recherche le repas;
					for (int i = 0; i < entitesProchaines.size();i++)
					{
						//si on veut manger un Vegetal
						if(entitesProchaines.get(i) instanceof Vegetal)
						{
							//recupere les coordonnee du Vegetal
							nouveauPosition.setX(entitesProchaines.get(i).getCoordonnee().getX());
							nouveauPosition.setY(entitesProchaines.get(i).getCoordonnee().getY());
							
							this.deplacement(nouveauPosition);//l'animal se deplace jusqua le vegetal
							
							this.manger((Vegetal) entitesProchaines.get(i));//manger le vegetaux
							
						}
							//il a faim mais il n'y a pas de repas => mouvement a l'hazard
							else 
							{
								nouveauPosition.setX((int)Math.random()*5);
								nouveauPosition.setY((int)Math.random()*5);
								
							}
					}
				}
					else //il nést pas faim
					{
						if (this.fatigue > 80)// A-t-il fatigue?
						{
							this.reposer(this.fatigue);//comme ca, fatigue arrive a 0 après de reposer
						}
						else //s'el n'a pas faim et il n'est pas fatigue => il peut se reproduire?
							if (this.stade == EnumStade.adulte)
							{
								for (int i = 0; i < entitesProchaines.size(); i++)
								{
									//recherche d'autre animaux
									if(entitesProchaines.get(i) instanceof Animal) //il faut chercher le meme classe d'animaux
									{
										//recupere les coordonnee d'animaux
										nouveauPosition.setX(entitesProchaines.get(i).getCoordonnee().getX());
										nouveauPosition.setY(entitesProchaines.get(i).getCoordonnee().getY());
										
										this.deplacement(nouveauPosition);//se déplacer jusqu'au l'autre animaux
										
										List<Animal> enfants = this.reproduction(this.coordonee);// se reproduire
										/*
										 * cet liste des enfants il faut l'ajouter dans la liste des animaux
										 * du logiciel principal 
										 */
									}
								
								}
							}
							/* 
							 * Il n'ai pas faim, il n'est pas fatigué, et il ne peux pas se reproduire => movement a l'hazard
							 */
							else 
							{
							nouveauPosition.setX((int)Math.random()*5);
							nouveauPosition.setY((int)Math.random()*5);	
							}
					}
				}
}
