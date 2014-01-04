package Vivarium;

import Carte.Map;
import Enumeration.EnumEnvironnement;
import GUI.InterfaceGraphique;
import GUI.Vue;

public class Partie {

	private int tempsDeJeux;
	private int score;
	private Map map;

	private boolean start;

	private static boolean play;
	
	public Partie(Map map) {
		this.map = map;
		this.start = false;
		play = false;
	}
	
	public void run()
	{
		Vue vue = new InterfaceGraphique(this);

		vue.fenetre(this);

		for (int i = 0; i < 20; i++)
			map.getGrilleDeJeu()[5][i].setEnvironnement(EnumEnvironnement.eau);
		for (int i = 0; i < 50; i++)
			map.getGrilleDeJeu()[24][i]
					.setEnvironnement(EnumEnvironnement.montagne);

		map.getGrilleDeJeu()[1][1].setEnvironnement(EnumEnvironnement.montagne);
		map.getGrilleDeJeu()[1][49]
				.setEnvironnement(EnumEnvironnement.montagne);
		map.getGrilleDeJeu()[49][49]
				.setEnvironnement(EnumEnvironnement.montagne);

		vue.dessineMap(map);

		while (true)
		{
				vue.dessineMap(map);
				
				//si play on fait bouger les entiter en appliquer la methode live
				if(play)
				{
					tempsDeJeux++; //on incrémente le nombre de tour jouer
					vue.setNbTour(tempsDeJeux);
				}
				
			try {
				new Thread();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void setPlay(boolean bool)
	{
		System.out.println("set play"+bool);
		play = bool;
	}
	
	public void stop()
	{
		this.start = false;
	}
	
	public void supprEntite() {

	}

	public void ajouteEntite() {

	}

}
