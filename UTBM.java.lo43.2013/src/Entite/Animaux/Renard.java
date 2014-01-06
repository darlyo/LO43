package Entite.Animaux;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Carte.Coordonnee;
import Carte.Map;
import Entite.Entite;
import Entite.Animaux.interfaces.Omnivorable;
import Entite.Vegetaux.Vegetal;
import Enumeration.EnumModeDeVie;
import Enumeration.EnumSexe;
import Enumeration.EnumStade;

public class Renard extends Animal implements Omnivorable {


	/**
	 * @see Animaux.interface.Omnivorable#manger(Animaux.Animal)
	 */
	public void manger(Animal animal) {
		int quantiteaManger;
		
		if (animal.getValeurEnergetique() > this.getFaim())
			quantiteaManger = this.getFaim();
		else 
			quantiteaManger = animal.getValeurEnergetique();

		/* Animal est mort */
		animal.delete();
		
		/* Après manger il a mois faim */
		this.setFaim(this.getFaim() -quantiteaManger);
		
		/*Apres manger il a plus fatigue*/
		this.setFatigue(this.getFatigue() + quantiteaManger);

	}


	/**
	 * @see Animaux.interface.Omnivorable#manger(Vegetal)
	 */
	
	public Renard(){
		super();
		this.modeDeVie=EnumModeDeVie.terrestre;	
		this.valeurEnergetique = 70;
		this.portee = 3;
	}
	
	public Renard(int dureeVie
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
		this.valeurEnergetique = 70;
		this.portee = 3;
	}
	
	public Renard(int dureeVie
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
		this.valeurEnergetique = 70; //valeurEnergetique entre 0 et 100
		this.portee = 3; //nro max des enfants
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
	protected List<Animal> reproduction() {
		//Nombre des enfants
				int nombreEnfants = (int) (Math.random()*(this.getPortee()));
				//Créer des enfans
				List<Animal> enfants = new ArrayList <Animal>();
				for (int i= 0; i<nombreEnfants;i++)
				{
					enfants.add(new Renard());
					enfants.get(i).setCoordonnee(this.getCoordonnee());
				}
				return enfants;			
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
						if (entitesProchaines.get(i) instanceof Vegetal)
						{
							//recupere les coordonnee du Vegetal
							nouveauPosition = entitesProchaines.get(i).getCoordonnee();
							if (nouveauPosition != this.getCoordonnee())
								this.deplacement(nouveauPosition);//l'animal se deplace en direction au vegetal
							else //la norriture et l'animal se trouvent dans le même case
								this.manger((Vegetal)entitesProchaines.get(i));//manger le vegetaux
						}
						else if ((entitesProchaines.get(i)) instanceof Lapin || (entitesProchaines.get(i)) instanceof Chamois)
						{
							nouveauPosition = entitesProchaines.get(i).getCoordonnee();
							if ((Math.abs(this.getCoordonnee().getX() - nouveauPosition.getX()) <2)
									|| (Math.abs(this.getCoordonnee().getY()- nouveauPosition.getY()) <2))
								this.manger((Animal)entitesProchaines.get(i));//manger l'animal

							else //la norriture et l'animal se trouvent dans le même case
								this.deplacement(nouveauPosition);//l'animal se deplace en direction à l'animal
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
									if (entitesProchaines.get(i) instanceof Renard)						
									{
										Renard couple = (Renard) entitesProchaines.get(i);
										if (couple.getSexe() != this.getSexe() && couple.getStade()!= EnumStade.jeune)
										{
											//recupere les coordonnee d'animal
											nouveauPosition = couple.getCoordonnee();
											if ((Math.abs(this.getCoordonnee().getX() - nouveauPosition.getX()) <2)
													|| (Math.abs(this.getCoordonnee().getY()- nouveauPosition.getY()) <2)) {
												// se
													List<Animal> enfants = this.reproduction();								// déplacer
													Iterator<Animal> it = enfants.iterator();
												while (it.hasNext()) {
													Entite entite = (Entite) it.next();
													Map.ajoutEntite(entite);
													this.setFaim(100);
													this.setFatigue(100);
												}								
											} else {
												this.deplacement(nouveauPosition);										
												
											}
											i = entitesProchaines.size();
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

}
