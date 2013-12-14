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

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import Entite.Entite;
import Entite.Animaux.Animal;
import Enumeration.EnumEntite;
import Enumeration.EnumEnvironnement;
import Vivarium.Main;

public class InterfaceGraphique implements Vue, Controle {

	private JFrame fenetre;
	private JPanel menu, map;
	
	//attribut propre au menu
	private CardLayout cardMenu;
	private JPanel content, menuConfigue, menuGestion;
	private String[] listContent = {"Configue", "Gestion"};
	

	@Override
	public void fenetre() {

		this.fenetre = new JFrame();
		this.fenetre.setTitle(Main.NAME);
		this.fenetre.setSize(800, 600);
		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fenetre.setResizable(false);

		//construction du menu
		menu = new JPanel();
		buildMenu();
		
		//construction de la zone de map
		//mais on ne charge pas encore de map
		map = new JPanel();
		buildMap();
		
		fenetre.add(menu);
		fenetre.add(map);
		
		this.fenetre.setVisible(true);
	}

	private void buildMap() {
		map.setSize(600, 600);
		map.setBackground(Color.green);
	}

	private void buildMenu() {
		menu.setSize(200,600);
		menu.setBackground(Color.cyan);
		
		//création d'un titre sur une seul ligne de taille 18 et de police sherif
		JLabel title = new JLabel();
		Font font = new Font(Font.SERIF, Font.PLAIN, 18);
		title.setFont(font);
		title.setText("Gestion du "+Main.NAME);
		
		cardMenu = new CardLayout();
		buildMenuConfigue();
		buildMenuGestion();
		content = new JPanel();
		content.setLayout(cardMenu);
		content.add(menuConfigue, listContent[0]);
		content.add(menuGestion, listContent[1]);
		
		menu.setLayout(new BorderLayout());
		menu.add(title, BorderLayout.NORTH);
		menu.add(content, BorderLayout.CENTER);
		
	}

	private void buildMenuGestion() {
		menuGestion = new JPanel();
		menuGestion.setBackground(Color.yellow);
		
	}

	private void buildMenuConfigue() {
		menuConfigue = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 20));
		menuConfigue.setBackground(Color.red);	
		
		//definit des boutons radio
		final JRadioButton r1 = new JRadioButton("Modifier l'environnement");
		final JRadioButton r2 = new JRadioButton("Ajouter une entité");
		final JRadioButton r3 = new JRadioButton("Supprimer une entité");
		
		ButtonGroup btgr = new ButtonGroup();
		btgr.add(r1);
		btgr.add(r2);
		btgr.add(r3);
		
		//definition des listes déroulantes
		final JComboBox<String> ListEnv = new JComboBox<String>();
		EnumEnvironnement[] env = EnumEnvironnement.values();
		for(int i =0; i < env.length; i++)
		{
			ListEnv.addItem(env[i].name());
		}
		ListEnv.disable();
		
		final JComboBox<String> ListEntite = new JComboBox<String>();
		ListEntite.setPreferredSize(new Dimension(100,30));
		EnumEntite[] entite = EnumEntite.values();
		for(int i =0; i < entite.length; i++)
		{
			ListEntite.addItem(entite[i].name());
		}
		ListEntite.disable();
		
		//définition des bouttons
		final JButton BtStart = new JButton("Start");
		final JButton BtCharge = new JButton("Charger une map");
		
		//défintion des action sur les bouttons
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == r1)
				{
					ListEnv.setEnabled(true);
					ListEntite.setEnabled(false);
				}
				else if(e.getSource() == r2)
				{
					ListEnv.setEnabled(false);
					ListEntite.setEnabled(true);
				}
				else if(e.getSource() == r3)
				{
					ListEnv.setEnabled(false);
					ListEntite.setEnabled(false);
				}
				else if(e.getSource() == BtStart)
				{
					cardMenu.next(content);
				}
				else if(e.getSource() == BtCharge)
				{
					;
				}
			}
		};
		
		r1.addActionListener(listener);
		r2.addActionListener(listener);
		r3.addActionListener(listener);
		BtStart.addActionListener(listener);
		BtCharge.addActionListener(listener);
		
		//ajout des éléments au menu
		menuConfigue.add(r1);
		menuConfigue.add(ListEnv);
		menuConfigue.add(r2);
		menuConfigue.add(ListEntite);
		menuConfigue.add(r3);
		menuConfigue.add(BtCharge);
		menuConfigue.add(BtStart);
	}

	@Override
	public void menu() {
		// TODO Auto-generated method stub

	}

	/**
	 * @see Vue#dessineMap()
	 * 
	 * 
	 */
	public void dessineMap() {

	}

	/**
	 * @see Controle#getChoix()
	 * 
	 * 
	 */
	public void getChoix() {

	}

	@Override
	public void dessineEntite() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void message(String msg) {
		// TODO Auto-generated method stub
		
	}

}
