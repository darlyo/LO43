package Vivarium;

import Carte.Coordonnee;
import Carte.Map;
import Enumeration.EnumEntite;
import Enumeration.EnumEnvironnement;
import GUI.InterfaceGraphique;
import GUI.Vue;

public class Partie {

	private int tempsDeJeux;
	private int score;
	private Map map;
	private boolean deletEnt;

	private static EnumEntite ent;
	private static Coordonnee cord;
	private static EnumEnvironnement env;

	private static boolean addEnt;
	private static boolean modEnv;
	private static boolean play;

	public Partie(Map map) {
		this.map = map;
		score = 0;
		play = false;
		modEnv = false;
	}

	public void run() {
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

		while (true) {
			// si play on fait bouger les entiter en appliquer la methode live
			if (play) {
				tempsDeJeux++; // on incrémente le nombre de tour jouer
				vue.setNbTour(tempsDeJeux);
			}
			if (modEnv) {
				map.setEnvironnement(cord, env);
				modEnv = false;
			}
			if (addEnt) {
				// ajout de lentité a la liste d'entité sur cord

				addEnt = false;
			}
			if (deletEnt) {
				// suppresion de l'entité positionner sur cord

				deletEnt = false;
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

	/**
	 * Modifie l'etat de la partie en cours ou en pause
	 * @param bool: etat désirer
	 */
	public void setPlay(boolean bool) {
		System.out.println("set play" + bool);
		play = bool;
	}

	/**
	 * Ajoute une entité sur une case si possible
	 * 
	 * @param selectedCord
	 *            : case où l'on désire faire l'insertion
	 * @param entite
	 *            : entité désirer
	 * @return: 1 en case de réussite sinon 0;
	 */
	public int ajouteEntite(Coordonnee selectedCord, EnumEntite entite) {
		// test si la case est libre

		addEnt = true;
		cord = selectedCord;
		ent = entite;
		return 1;
	}

	/**
	 * Test si l'on peut l'entiter existe et la supprimer dana le cas possitif
	 * 
	 * @param selectedCord
	 *            : la case ou l'on veut supprimer une entité
	 * @return null si impossible sinon retourne le type d'entité supprimer
	 */
	public EnumEntite supprimeEntite(Coordonnee selectedCord) {
		// test si il y a une entiter sur la case

		deletEnt = true;
		cord = selectedCord;
		return null;
	}

	/**
	 * Modifie l'environnement d'une case
	 * 
	 * @param coordonne
	 *            : case que l'on désire modifier
	 * @param environnement
	 *            : environnement désirer
	 */
	public void modiferEnv(Coordonnee coordonne, EnumEnvironnement environnement) {
		cord = coordonne;
		env = environnement;
		modEnv = true;
	}

}
