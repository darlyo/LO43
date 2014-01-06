package Entite.Animaux;

import java.util.List;

import Carte.Coordonnee;
import Carte.Map;
import Entite.Entite;
import Entite.Vegetaux.Vegetal;
import Enumeration.EnumModeDeVie;
import Enumeration.EnumSexe;
import Enumeration.EnumStade;


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

	public void deplacement(Coordonnee nouveauPosition) {
			/*
			 * movimiento de un casillero en un casillero, en dirección a la nueva posición
			 * 
			 * Calculo para el eje de las 'x'
			 */
				if ((nouveauPosition.getX()-this.getCoordonnee().getX())>0)
				{
					nouveauPosition.setX(this.getCoordonnee().getX()+1);
				}
				else if ((nouveauPosition.getX()-this.getCoordonnee().getX())<0) {
					nouveauPosition.setX(this.getCoordonnee().getX()-1);
				}
				else
				{
					nouveauPosition.setX(this.getCoordonnee().getX());
				}
			//Calculo para el eje de las 'y'	
				if ((nouveauPosition.getY()-this.getCoordonnee().getY())>0)
				{
					nouveauPosition.setY(this.getCoordonnee().getY()+1);
				}
				else if ((nouveauPosition.getY()-this.getCoordonnee().getY())<0) {
					nouveauPosition.setY(this.getCoordonnee().getY()-1);
				}
				else
				{
					nouveauPosition.setY(this.getCoordonnee().getY());
				}
				
				//las coordenadas no pueden ser negativas
				if (nouveauPosition.getX()<0)
				{
					nouveauPosition.setX(0);
				}
				if (nouveauPosition.getY()<0)
				{
					nouveauPosition.setY(0);
				}
				
				if(nouveauPosition.getX() > Map.getTaille())
					nouveauPosition.setX(Map.getTaille()-1);
				if(nouveauPosition.getY() > Map.getTaille())
					nouveauPosition.setY(Map.getTaille()-1);
				
				this.setFatigue(this.getFatigue()+2);//aumento de la fatiga
				this.setFaim(this.getFaim()+1);//aumento el hambre
				this.setCoordonnee(nouveauPosition);//el animal se movió hasta esa nueva posición
	
	}

	public void deplacementHasard(){
		Coordonnee nouveauPosition = this.getCoordonnee();
		
		double i = Math.random();
		double j = Math.random();
		//para el eje de las x
		if (i>=0.6)  //x = x+1
			nouveauPosition.setX(nouveauPosition.getX()+1);
		else if (i<=0.3) //x = x-1
			nouveauPosition.setX(nouveauPosition.getX()-1);
		//para el eje de las y
		if (j>=0.6) //y = y+1
			nouveauPosition.setY(nouveauPosition.getY()+1);
		else if (j<=0.3) //y = y-1
			nouveauPosition.setY(nouveauPosition.getY()-1);
			
		this.deplacement(nouveauPosition);//el animal se mueve hacia esa nueva posición
	}
	
	protected abstract List<Animal> reproduction();//Chaque animal doit naitre dans le meme place que les parents

	protected void grandir() {
		/* Augmenter l'age */
		this.setAge(this.getAge()+1);
		
		float veillese = ((float)this.getAge()/(float)this.getDureeVie());
		
		/*Changer de stade*/
		if (veillese < 0.3)
			this.setStade(EnumStade.jeune);
		else 
			if (veillese < 0.6)
				this.setStade(EnumStade.adulte);
			else
				this.setStade(EnumStade.vieux);
	}

	public abstract void live(Map map);

	protected List<Entite> perception(Coordonnee coord, int ray, Map map)//le resultat est une liste avec tout les entites prochaines
	{
		List <Entite> entitesProchaine = map.perception(coord, ray); //Reviser quand utilise cet List
		return entitesProchaine;
	}

	public void reposer(int temp) {
		/*
		 * If faut changer ce methode, il faut avoir un timer.swing
		 * L'animal ne peut rien faire pendant il dort
		 *fatigue et temp ont la même unite de meassure. 
		 */
		//this.setFatigue(this.getFatigue()-temp*10);
		this.setFatigue(0);
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

	public void manger(Vegetal vegetal){};
	
	public void manger(Animal animal){};
	
	
}
