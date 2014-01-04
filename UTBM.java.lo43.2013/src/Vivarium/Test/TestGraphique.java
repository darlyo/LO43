package Vivarium.Test;

import Vivarium.Partie;
import Carte.Map;
import Enumeration.EnumEnvironnement;
import GUI.InterfaceGraphique;
import GUI.Vue;

public class TestGraphique {


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Map map = new Map(50);
		Partie partie = new Partie(map);
		partie.run();
	}
	
}
