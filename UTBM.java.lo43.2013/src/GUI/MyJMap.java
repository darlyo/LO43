package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import Carte.Coordonnee;
import Carte.Map;
import Entite.Entite;
import Enumeration.EnumEntite;
import Enumeration.EnumEnvironnement;

@SuppressWarnings("serial")
public class MyJMap extends JPanel {

	private int heightCase, widhtCase;
	private int[][] defFond;
	private int taille;
	private int bordureLegende = 20;
	private int tailleMenu = 200;
	private List<Entite> lisEnt;

	public MyJMap(Map map, int height, int width) {

		super();
		setSize((int) (height * 1.10), (int) ((width - tailleMenu) * 1.1));
		setLocation(tailleMenu, 0);

		// on intialise la taille des cases
		taille = map.getTaille();
		heightCase = (height - bordureLegende) / (taille);
		widhtCase = (width - bordureLegende - tailleMenu) / (taille);
		lisEnt = new ArrayList<Entite>();

		// on configure le graphique de fond
		defFond = new int[taille][taille];
		for (int i = 0; i < taille; i++)
			for (int j = 0; j < taille; j++)
				setEnv(new Coordonnee(i, j),
						map.getGrilleDeJeu()[i][j].getEnvironnement());

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// moveEntite(e.getX(),e.getY());
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				// moveEntite(e.getX(),e.getY());
			}
		});

	}

	public void paintComponent(Graphics g) {
		for (int i = 0; i < defFond.length; i++) {
			for (int j = 0; j < defFond.length; j++) {
				switch (defFond[i][j]) {
				case 0:
					g.setColor(Color.green);
					break;
				case 1:
					g.setColor(Color.blue);
					break;
				case 2:
					g.setColor(Color.gray);
					break;
				}
				g.fillRect(i * widhtCase + bordureLegende, j * heightCase
						+ bordureLegende, widhtCase, heightCase);
				if ((i == 0) && (j % 5 == 0)) {
					g.setColor(Color.black);
					g.drawString("" + j, widhtCase * j + bordureLegende,
							bordureLegende - 5);
				}
			}
			if (i % 5 == 0) {
				g.setColor(Color.black);
				g.setColor(new Color(20));
				g.drawString("" + i, 2, (heightCase * i) + 2 * bordureLegende);
			}
		}
		g.setColor(Color.black);
		for (int i = 0; i < defFond.length + 1; i++) {
			// ligne verticale
			g.drawLine(i * widhtCase + bordureLegende, bordureLegende, i
					* widhtCase + bordureLegende, heightCase * taille
					+ bordureLegende);
			// ligne horizontale
			g.drawLine(bordureLegende, i * heightCase + bordureLegende, taille
					* widhtCase + bordureLegende, heightCase * i
					+ bordureLegende);
		}

		if (!lisEnt.isEmpty()) {
			Iterator<Entite> iteratorEntite = lisEnt.iterator();
			while (iteratorEntite.hasNext()) {
				Entite ent = iteratorEntite.next();
				g.setColor(EnumEntite.valueOf(ent.getClass().getSimpleName())
						.getColor());
				g.fillOval(bordureLegende + ent.getCoordonnee().getX()
						* widhtCase+widhtCase/4, bordureLegende + ent.getCoordonnee().getY()
						* heightCase+heightCase/4, widhtCase / 2, heightCase / 2);
			}
		}
	}

	protected void moveEntite(Coordonnee depart, Coordonnee arrivé) {
		// TODO Auto-generated method stub
	}

	/**
	 * Ajoute une entité sur la carte sous forme d'un composant
	 * 
	 * @param ent
	 */
	public void add(List<Entite> ent) {
		lisEnt =ent;
	}

	/**
	 * Suprimme une entité de la carte (entité morte)
	 * 
	 * @param ent
	 */
	public void supprEntite(Entite ent) {

	}

	/**
	 * Modifie l'environnement d'une case
	 * 
	 * @param cord
	 *            : la case dont on modifie l'environnement
	 * @param env
	 *            : l'environement désirer
	 */
	public void setEnv(Coordonnee cord, EnumEnvironnement env) {

		if ((cord.getX() >= 0) && (cord.getX() < taille) && (cord.getY() >= 0)
				&& (cord.getY() < taille))
			switch (env) {
			case eau: {
				defFond[cord.getX()][cord.getY()] = 1;
				break;
			}
			case montagne: {
				defFond[cord.getX()][cord.getY()] = 2;
				break;
			}
			case plaine: {
				defFond[cord.getX()][cord.getY()] = 0;
				break;
			}
			}
	}

	/**
	 * Modifie l'environnement d'une zone
	 * 
	 * @param cord1
	 *            : début de la zone coin en haut a gauche
	 * @param cord2
	 *            : fin de la zone en bas a droite
	 * @param env
	 *            : l'environement désirer
	 */
	public void setEnv(Coordonnee cord1, Coordonnee cord2, EnumEnvironnement env) {
		for (int i = cord1.getX(); i <= cord2.getX(); i++)
			for (int j = cord1.getY(); j <= cord2.getY(); j++)
				setEnv(new Coordonnee(i, j), env);
	}
}
