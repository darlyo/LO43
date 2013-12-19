package Vivarium.Test;

import Carte.Map;
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
		
		Map map = new Map(5);
		map.getGrilleDeJeu()[1][1].setEnvironnement(EnumEnvironnement.eau);
		vue.dessineMap(map);
		
	}

}
