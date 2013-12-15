package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

public class InterfaceGraphique implements Vue {

	private JFrame fenetre;
	private JPanel panelMenu, panelMap, panelCarte;

	// attribut propre au panelMenu
	private CardLayout cardMenu;
	private JPanel content, menuConfigue, menuGestion;
	private String[] listContent = { "Configue", "Gestion" };

	// element du panelMenu modifiable
	private JLabel JNbTour, JScore;
	private int NbTour = 0;
	private int Score = 0;
	private boolean etat = false;
	
	//Couleur
	private Color grisClair = new Color( Integer.parseInt( "d2d2d2", 16 ) );
	private Color grisClaire2 = new Color( Integer.parseInt( "eeeeee", 16 ) );
	private int taille;
	
	@Override
	public void fenetre() {

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
		panelMap = new JPanel();
		buildMap();

		fenetre.add(panelMenu);
		fenetre.add(panelMap);

		this.fenetre.setVisible(true);
	}

	private void buildMap() {
		panelMap.setSize(600, 600);
		panelMap.setBackground(Color.green);
	}

	private void buildMenu() {
		panelMenu.setSize(200, 600);
		panelMenu.setBackground(grisClaire2);
		panelMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));
		
		// cr�ation d'un titre sur une seul ligne de taille 18 et de police
		// sherif
		JLabel title = new JLabel();
		title.setFont( new Font(Font.SERIF, Font.PLAIN, 18));
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

		final JRadioButton r1 = new JRadioButton("Ajouter une entit�");
		final JRadioButton r2 = new JRadioButton("Supprimer une entit�");

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

		final JButton btSave = new JButton("Enregistrer la panelMap");
		final JButton btPauseStart = new JButton("Pause");
		// d�fintion des action sur les bouttons
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
						btPauseStart.setText("Start");
						etat = false;
					} else {
						btPauseStart.setText("Pause");
						etat = true;
					}
				}
			}
		};

		r1.addActionListener(listener);
		r2.addActionListener(listener);

		menuGestion.add(PTour);
		menuGestion.add(PScore);
		menuGestion.add(r1);
		menuGestion.add(ListEntite);
		menuGestion.add(r2);
		menuGestion.add(btSave);
		menuGestion.add(btPauseStart);
	}

	private void buildMenuConfigue() {
		menuConfigue = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 20));

		// definit des boutons radio
		final JRadioButton r1 = new JRadioButton("Modifier l'environnement");
		final JRadioButton r2 = new JRadioButton("Ajouter une entit�");
		final JRadioButton r3 = new JRadioButton("Supprimer une entit�");

		ButtonGroup btgr = new ButtonGroup();
		btgr.add(r1);
		btgr.add(r2);
		btgr.add(r3);

		// definition des listes d�roulantes
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

		// d�finition des bouttons
		final JButton BtStart = new JButton("Start");
		final JButton BtCharge = new JButton("Charger une panelMap");

		// d�fintion des action sur les bouttons
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
					cardMenu.next(content);
					etat = true;
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

		// ajout des �l�ments au panelMenu
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dessineMap(Map map) {
		panelMap.removeAll();
		
		taille = 10;//map.getSize();
		panelCarte = new JPanel(new GridLayout(taille, taille));
		
	}

}
