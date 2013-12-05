package Animaux;

import Carte.Entite;
import Enumeration.EnumModeDeVie;
import Enumeration.EnumSexe;
import Enumeration.EnumStade;

public abstract class Animal extends Entite {

	protected int dureeVie;

	protected EnumModeDeVie modeDeVie;

	protected EnumSexe sexe;

	protected int portee;

	protected int faim;

	protected int rayon;

	protected int fatigue;

	protected EnumStade stade;

	protected abstract void deplacement();

	protected abstract void reproduction();

	protected void grandir() {

	}

	public abstract void live();

	protected void perception() {

	}

	protected void reposer() {

	}

}
