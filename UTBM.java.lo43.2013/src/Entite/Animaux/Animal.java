package Entite.Animaux;

import java.util.List;

import Carte.Coordonnee;
import Carte.Map;
import Entite.Entite;
import Enumeration.EnumModeDeVie;
import Enumeration.EnumSexe;
import Enumeration.EnumStade;

public abstract class Animal extends Entite {

	protected int dureeVie;

	/**
	 * model de vie: 1/3 Jeune, 1/3 adulte et 1/3 Vieux
	 */
	protected EnumModeDeVie modeDeVie;

	protected EnumSexe sexe;

	protected int portee;

	/**
	 * Faim de 0 a 100, 0 il n'a pas faim, 100 il a trop faim
	 */
	protected int faim;

	protected int rayon;

	/**
	 * Fatigue de 0 a 100, parei faim
	 */
	protected int fatigue;

	protected EnumStade stade;

	public Animal() {
		super();

		this.dureeVie = (int) (Math.random() * 20 + 80);

		// sexe random
		this.sexe = (Math.random() > 0.5) ? EnumSexe.male : EnumSexe.femelle;

		// Nombre max des enfants
		this.portee = 5;

		this.faim = 0;

		// Definir les rayon (max, min) scale.
		this.rayon = 5;

		this.fatigue = 0;

		this.stade = EnumStade.jeune;
	}

	Animal(int dureeVie, EnumModeDeVie modedevie, EnumSexe sexe, int portee,
			int faim, int rayon, int fatigue, int age, int valeurEnergetic,
			int x, int y) {

		super(valeurEnergetic, age, x, y);

		this.dureeVie = dureeVie;

		this.faim = faim;

		this.fatigue = fatigue;

		this.modeDeVie = modedevie;

		this.portee = portee;

		this.rayon = rayon;

		this.sexe = sexe;

		this.stade = EnumStade.jeune;
	}// fin contructeur

	Animal(int dureeVie, EnumModeDeVie modedevie, EnumSexe sexe, int portee,
			int faim, int rayon, int fatigue, int age, int valeurEnergetic,
			Coordonnee coordonnee) {

		super(valeurEnergetic, age, coordonnee);

		this.dureeVie = dureeVie;

		this.faim = faim;

		this.fatigue = fatigue;

		this.modeDeVie = modedevie;

		this.portee = portee;

		this.rayon = rayon;

		this.sexe = sexe;

		this.stade = EnumStade.jeune;
	}// fin contructeur

	protected abstract void deplacement(Coordonnee nouveauPosition);

	/**
	 * Chaque animal doit naitre dans la meme place que les parents
	 * 
	 * @param coord
	 * @return
	 */
	protected abstract List<Animal> reproduction(Coordonnee coord);

	protected void grandir() {
		/* Augmenter l'age */
		this.age = this.age + 1;

		/* Changer de stade */
		if (this.age / this.dureeVie > 1 / 3)
			this.stade = EnumStade.jeune;
		else if (this.age / this.dureeVie < 2 / 3)
			this.stade = EnumStade.adulte;
		else
			this.stade = EnumStade.vieux;
	}

	

	/**
	 * le resultat est une liste avec tout les entites prochaines
	 * 
	 * @param coord
	 * @param ray
	 * @param map
	 * @return
	 */
	protected List<Entite> perception(Coordonnee coord, int ray, Map map) {
		List<Entite> entitesProchaine = map.perception(coord, ray); // Reviser
																	// quand
																	// utilise
																	// cet List
		return entitesProchaine;
	}

	protected void reposer(int temp) {
		/*
		 * If faut changer ce methode, il faut avoir un timer.swing L'animal ne
		 * peut rien faire pendant il dortfatigue et temp ont la m�me unite de
		 * meassure.
		 */
		this.fatigue = this.fatigue - temp;
	}

	// Geters y Seters de fatigue et faim
	public void setFatigue(int x) {
		this.fatigue = x;
	}

	public int getFatigue() {
		return this.fatigue;
	}

	public void setFaim(int x) {
		this.faim = x;
	}

	public int getFaim() {
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

}
