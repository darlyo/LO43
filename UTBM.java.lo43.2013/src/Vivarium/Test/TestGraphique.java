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

		Vue vue = new InterfaceGraphique();
		Map map = new Map(50);

		vue.fenetre(new Partie(map));
		
		for(int i =0;i<20;i++)
			map.getGrilleDeJeu()[5][i].setEnvironnement(EnumEnvironnement.eau);
		for(int i =0;i<50;i++)
			map.getGrilleDeJeu()[24][i].setEnvironnement(EnumEnvironnement.montagne);
		
		map.getGrilleDeJeu()[1][1].setEnvironnement(EnumEnvironnement.montagne);
		map.getGrilleDeJeu()[1][49].setEnvironnement(EnumEnvironnement.montagne);
		map.getGrilleDeJeu()[49][49].setEnvironnement(EnumEnvironnement.montagne);

		vue.dessineMap(map);			
		
	}

}
