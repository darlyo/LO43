package Vivarium;

import Carte.Coordonnee;
import Carte.Map;
import Enumeration.EnumEnvironnement;
import GUI.InterfaceGraphique;
import GUI.Vue;

public class Partie {

	private int tempsDeJeux;
	private int score;
	private Map map;
	private static Coordonnee cord;
	private static EnumEnvironnement env;
	
	private static boolean modEnv;
	private static boolean play;
	
	public Partie(Map map) {
		this.map = map;
		score = 0;
		play = false;
		modEnv =false;
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
				//si play on fait bouger les entiter en appliquer la methode live
				if(play)
				{
					tempsDeJeux++; //on incrémente le nombre de tour jouer
					vue.setNbTour(tempsDeJeux);
				}
				if(modEnv)
				{
					map.setEnvironnement(cord, env);
					
					modEnv =false;
				}
				vue.dessineMap(map);

			try {
				new Thread();
				Thread.sleep(300);
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
	
	public void supprEntite(Coordonnee cord) {
		if(play)
			return; //le jeux doit etre en pause on ne peut pas récupere un element en mouvement
		
		//recherche de l'entite au coordonée donnée
		
		//affichage d'une box de confiration
		
	}

	public void ajouteEntite() {

	}

	public void modiferEnv(Coordonnee coordonne, EnumEnvironnement environnement) {
		cord = coordonne;
		env = environnement;
		modEnv =true;
	}

}
