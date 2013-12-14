package Vivarium.Test;

import Entite.Entite;
import Enumeration.EnumEnvironnement;
import GUI.InterfaceGraphique;
import GUI.Vue;

public class TestGraphique {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Vue vue = new InterfaceGraphique();
		vue.fenetre();
		
		System.out.println("" + Entite.class.getSimpleName());
	}

}
