package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import Carte.Map;
import Entite.Entite;
import Enumeration.EnumEntite;
import Enumeration.EnumEnvironnement;
import Vivarium.Main;
import Vivarium.Partie;
import Vivarium.Test.TestGraphique;

public class InterfaceGraphique implements Vue, Controle {

	private JFrame fenetre;
	private JPanel panelMenu, panelMap;
	private MyJMap panelCarte;

	// attribut propre au panelMenu
	private CardLayout cardMenu;
	private JPanel content, menuConfigue, menuGestion;
	private String[] listContent = { "Configue", "Gestion" };

	// element du panelMenu modifiable
	private JLabel JNbTour, JScore;
	private int NbTour = 0;
	private int Score = 0;
	private boolean etat = false;
	
	private static Partie controleur;

	public InterfaceGraphique(Partie partie) {
		super();
		controleur=partie;
	}

	public InterfaceGraphique() {
		super();
	}

	@Override
	public void fenetre(Partie partie) {

		Partie controlleur = partie;
		this.fenetre = new JFrame();
		this.fenetre.setTitle(Main.NAME);
		this.fenetre.setSize(800, 600);
		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetre.setResizable(false);

		// construction du panelMenu
		panelMenu = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buildMenu();

		// construction de la zone de panelMap
		// mais on ne charge pas encore de panelMap
		panelMap = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buildMap();

		fenetre.add(panelMenu);
		fenetre.add(panelMap);

		this.fenetre.setVisible(true);
	}

	/**
	 * Construit la zone ou sera afficher la map et definit sa taille
	 */
	private void buildMap() {
		panelMap.setSize(600, 600);
		panelMap.setOpaque(false);
		panelMap.setLocation(200, 0);

		// panelMap.setBackground(Color.green);
	}

	/**
	 * Construit la zone de menu Avec le titre et les 2 conteneur pour la
	 * gestion et la configuration
	 */
	private void buildMenu() {
		panelMenu.setSize(200, 600);
		panelMenu.setBackground(Couleur.grisClaire2);
		panelMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2,
				Color.black));

		// création d'un titre sur une seul ligne de taille 18 et de police
		// sherif
		JLabel title = new JLabel();
		title.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		title.setText("Gestion du " + Main.NAME);
		cardMenu = new CardLayout();
		buildMenuConfigue();
		buildMenuGestion();
		content = new JPanel();
		content.setLayout(cardMenu);
		content.add(menuConfigue, listContent[0]);
		content.add(menuGestion, listContent[1]);

		panelMenu.setLayout(new BorderLayout());
		panelMenu.add(title, BorderLayout.NORTH);
		panelMenu.add(content, BorderLayout.CENTER);

	}

	/**
	 * Construi le menu propre a la gestion de la partie C'est a dire visible
	 * une fois la partie demarrer
	 */
	private void buildMenuGestion() {
		menuGestion = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 20));

		// affichage du nombre de tour de la partie
		JPanel PTour = new JPanel();
		JLabel libTour = new JLabel("Nombre de Tours : ");
		JNbTour = new JLabel("" + NbTour);

		PTour.add(libTour);
		PTour.add(JNbTour);

		// affichage du score de la partie en cour
		JPanel PScore = new JPanel();
		JLabel libScore = new JLabel("Score : ");
		JScore = new JLabel("" + Score);

		PScore.add(libScore);
		PScore.add(JScore);

		final JRadioButton r1 = new JRadioButton("Ajouter une entité");
		final JRadioButton r2 = new JRadioButton("Supprimer une entité");

		ButtonGroup btgr = new ButtonGroup();
		btgr.add(r1);
		btgr.add(r2);

		final JComboBox<String> ListEntite = new JComboBox<String>();
		ListEntite.setPreferredSize(new Dimension(100, 30));
		EnumEntite[] entite = EnumEntite.values();
		for (int i = 0; i < entite.length; i++) {
			ListEntite.addItem(entite[i].name());
		}
		ListEntite.setEnabled(false);

		final JButton btSave = new JButton("Enregistrer la carte");
		final JButton btPauseStart = new JButton("Pause");
		
		// défintion des action sur les bouttons
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == r1) {
					ListEntite.setEnabled(true);
				} else if (e.getSource() == r2) {
					ListEntite.setEnabled(false);
				} else if (e.getSource() == btSave) {

				} else if (e.getSource() == btPauseStart) {
					if (etat) {
						System.out.println("passage1");

						btPauseStart.setText("Start");
						etat = false;
						controleur.setPlay(etat);

					} else {
						System.out.println("passage2");

						btPauseStart.setText("Pause");
						etat = true;
						controleur.setPlay(etat);
						System.out.println("passage3");
					}
				}
			}
		};

		r1.addActionListener(listener);
		r2.addActionListener(listener);
		btPauseStart.addActionListener(listener);
		btSave.addActionListener(listener);
		

		menuGestion.add(PTour);
		menuGestion.add(PScore);
		menuGestion.add(r1);
		menuGestion.add(ListEntite);
		menuGestion.add(r2);
		menuGestion.add(btSave);
		menuGestion.add(btPauseStart);
	}

	/**
	 * Définit la menu de configuration de la map disponible avant le lancer de
	 * la map
	 */
	private void buildMenuConfigue() {
		menuConfigue = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 20));

		// definit des boutons radio
		final JRadioButton r1 = new JRadioButton("Modifier l'environnement");
		final JRadioButton r2 = new JRadioButton("Ajouter une entité");
		final JRadioButton r3 = new JRadioButton("Supprimer une entité");

		ButtonGroup btgr = new ButtonGroup();
		btgr.add(r1);
		btgr.add(r2);
		btgr.add(r3);

		// definition des listes déroulantes
		final JComboBox<String> ListEnv = new JComboBox<String>();
		EnumEnvironnement[] env = EnumEnvironnement.values();
		for (int i = 0; i < env.length; i++) {
			ListEnv.addItem(env[i].name());
		}
		ListEnv.setEnabled(false);

		final JComboBox<String> ListEntite = new JComboBox<String>();
		ListEntite.setPreferredSize(new Dimension(100, 30));
		EnumEntite[] entite = EnumEntite.values();
		for (int i = 0; i < entite.length; i++) {
			ListEntite.addItem(entite[i].name());
		}
		ListEntite.setEnabled(false);

		// définition des bouttons
		final JButton BtStart = new JButton("Start");
		final JButton BtCharge = new JButton("Charger une carte");

		// défintion des action sur les bouttons
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == r1) {
					ListEnv.setEnabled(true);
					ListEntite.setEnabled(false);
				} else if (e.getSource() == r2) {
					ListEnv.setEnabled(false);
					ListEntite.setEnabled(true);
				} else if (e.getSource() == r3) {
					ListEnv.setEnabled(false);
					ListEntite.setEnabled(false);
				} else if (e.getSource() == BtStart) {
					etat = true;
					controleur.setPlay(etat);
					System.out.println("ok");
					cardMenu.next(content);

				} else if (e.getSource() == BtCharge) {
					;
				}
			}
		};

		r1.addActionListener(listener);
		r2.addActionListener(listener);
		r3.addActionListener(listener);
		BtStart.addActionListener(listener);
		BtCharge.addActionListener(listener);

		// ajout des éléments au panelMenu
		menuConfigue.add(r1);
		menuConfigue.add(ListEnv);
		menuConfigue.add(r2);
		menuConfigue.add(ListEntite);
		menuConfigue.add(r3);
		menuConfigue.add(BtCharge);
		menuConfigue.add(BtStart);
	}

	@Override
	public void dessineEntite(java.util.List<Entite> listEntite) {
		if (listEntite.isEmpty())
			return;
		panelCarte.add(listEntite);
	}

	@Override
	public void dessineMap(Map map) {
		panelMap.removeAll();
		
		panelCarte = new MyJMap(map, panelMap.getHeight(), panelMap.getWidth());
		
		panelMap.add(panelCarte);
		fenetre.repaint();
		panelMap.repaint();

	}

	@Override
	public void getChoix() {
		
	}

	@Override
	public void setNbTour(int tour) {
		NbTour = tour;
		JNbTour.setText(""+NbTour);
	}

}
