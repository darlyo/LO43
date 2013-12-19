package Vivarium.Test;

import Carte.Case;
import Carte.Map;
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
		
		Map map = new Map(20);
		map.getGrilleDeJeu()[1][1].setEnvironnement(EnumEnvironnement.eau);
		vue.dessineMap(map);
		
	}

}
