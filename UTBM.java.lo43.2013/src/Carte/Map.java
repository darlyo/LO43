package Carte;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import Vivarium.Partie;
import Entite.Entite;
import Entite.Animaux.Chamois;
import Entite.Animaux.Lapin;
import Entite.Animaux.Loup;
import Entite.Animaux.Mouton;
import Entite.Animaux.Renard;
import Entite.Animaux.Sanglier;
import Entite.Vegetaux.Arbre;
import Entite.Vegetaux.Herbe;
import Enumeration.EnumEnvironnement;
import Enumeration.EnumModeDeVie;
import Enumeration.EnumSexe;
import Enumeration.EnumStade;
import GUI.InterfaceGraphique;
import GUI.Vue;

public class Map {
	private static Document document;
	private static Document documentSauv;
	//private static Element racineSauv;
	private static Element racine;
	private static int taille;
	protected static Case [][] grilleDeJeu;
	private Nourriture nourriture;
	private Environnement environnement;
	private static Coordonnee coordonnee;
	public static List<Entite> listEntites = new ArrayList<Entite>();;
	private int rayon;
	private static String fichier = new String ("src/Carte/Map.xml");
	private static int i = 0;
	
	/**
	 * Constructeurs
	 */
	public Map(int taille){
		System.out.println("----------------MAP--------------\n");
		this.taille = taille;
		grilleDeJeu = new Case[taille][taille];
		
		for(int j=0; j <taille; j++){
			for(int k = 0; k<taille; k++){
				//grilleDeJeu[j][k] = new Case();
				grilleDeJeu[j][k] = new Case(new Coordonnee(j, k));
				System.out.print("|_");
			}
			System.out.print("|");
			 System.out.println();
		}
	}
	
	
	/**
	 * Permet de parser le XML et de créer la map 
	 */
	public static List<Entite> lireXML(String fichier) throws Exception{
		System.out.println(">>>>>>>>>>>>>>>>>>>>>PARSAGE XML<<<<<<<<<<<<<<<<<<<<<<<<<<");
		/*On crée une instance de SAXBuilder*/
		SAXBuilder sxb = new SAXBuilder();
		/*On crée un nouveau document JDOM*/
		document = sxb.build(new File(fichier));
		/*On initialise un nouvel élément racine avec l'élément racine du document*/
		racine = document.getRootElement();
		
		/*Récupération de la taille de la map*/
		String tailleXML = racine.getChildText("taille");
		taille = Integer.parseInt(tailleXML);
		System.out.println("Taille de la map : " + taille);
		
		/*Récupération des éléments environnement du fichier XML*/
		List<Element> listEnvs = racine.getChildren("environnements");
		System.out.println("\n-------------ENVIRONNEMENTS------------- : ");
		
		for(int i = 0 ; i<listEnvs.size(); i++){
			
			Element e = (Element)listEnvs.get(i);
			List<Element> listEnv = e.getChildren("environnement");
			
			for(int h=0; h < listEnv.size() ; h++){
				Element env = (Element)listEnv.get(h);
				
				String nomEnv = env.getAttributeValue("name");
				System.out.println("Nom: " + nomEnv);
				 
				/*Récupération des coordonnées des environnements sur la grille*/
				int coordX_D = Integer.parseInt(env.getAttributeValue("coordX_debut"));
				int coordY_D = Integer.parseInt(env.getAttributeValue("coordY_debut"));
				int coordX_F = Integer.parseInt(env.getAttributeValue("coordX_fin"));
				int coordY_F = Integer.parseInt(env.getAttributeValue("coordY_fin"));
				
				/*Affichage des coordonnées des environnements*/
				System.out.println("Coord X debut: " + coordX_D + " ; Coord Y début: " + coordY_D);
				System.out.println("Coord X fin: " + coordX_F + " ; Coord Y fin: " + coordY_F);
				
				/*Affectation des cases de la grille aux différents environnements*/
				for(int j=coordX_D ;j<=coordX_F ; j++){
					for(int k=coordY_D ; k <=coordY_F; k++){
						//System.out.println("coor : " + j + " " + k);
						if(nomEnv == "eau"){
							grilleDeJeu[j][k] = new Case (new Coordonnee(j,k),EnumEnvironnement.eau, true);
						}else if (nomEnv == "plaine"){
							grilleDeJeu[j][k] = new Case (new Coordonnee(j,k),EnumEnvironnement.plaine, true);
						}else if(nomEnv == "montagne"){
							grilleDeJeu[j][k] = new Case (new Coordonnee(j,k),EnumEnvironnement.montagne, true);
						}
					}
				}	
				System.out.println();
			}
		}

		/*Récupération des éléments entités du fichier XML*/
		List<Entite> listEntites = new ArrayList<Entite>();
		
		Element entites = racine.getChild("entites");
		System.out.println("\n-------------ENTITES (ANIMAUX ET VEGETAUX)------------- : ");
		
		List<Element> listRenards = entites.getChildren("renards");
		for(int i = 0 ; i<listRenards.size(); i++){
			System.out.println("-----------------RENARDS-----------------");
			Element e = (Element)listRenards.get(i);
			List<Element> listRenard = e.getChildren("renard");
			
			if(listRenard.size() == 0){
				System.out.println("Pas de renards");
			}else{
				int portee = Integer.parseInt(e.getAttributeValue("portee"));
				int rayon = Integer.parseInt(e.getAttributeValue("rayon"));
				int valeurEnergetique = Integer.parseInt(e.getAttributeValue("valeurEnergetique"));
				int dureeVie = Integer.parseInt(e.getAttributeValue("dureeVie"));
				
				for(int h=0; h < listRenard.size() ; h++){
					System.out.println("Renard "+(h+1));
					Renard renard = new Renard();
					
					Element ren = (Element)listRenard.get(h);
					
					renard.setPortee(portee);
					renard.setRayon(rayon);
					renard.setValeurEnergetique(valeurEnergetique);
					renard.setDureeVie(dureeVie);
					String stade = ren.getAttributeValue("stade");
					if(stade == "jeune"){
						renard.setStade(EnumStade.jeune);
					}else if (stade == "adulte"){
						renard.setStade(EnumStade.adulte);
					}else if (stade == "vieux"){
						renard.setStade(EnumStade.vieux);
					}
					renard.setFaim(Integer.parseInt(ren.getAttributeValue("faim")));
					renard.setFatigue(Integer.parseInt(ren.getAttributeValue("fatigue")));
					
					if(e.getAttributeValue("modeDeVie") == "terrestre"){
						System.out.println("ici");
						renard.setModeDeVie(EnumModeDeVie.terrestre);
					}else if (e.getAttributeValue("modeDeVie") == "aquatique"){
						renard.setModeDeVie(EnumModeDeVie.aquatique);
					}else if (e.getAttributeValue("modeDeVie") == "amphibie"){
						renard.setModeDeVie(EnumModeDeVie.amphibie);
					}else{
						//Par défaut terrestre
						renard.setModeDeVie(EnumModeDeVie.terrestre);
					}
					
					renard.setAge(Integer.parseInt(ren.getAttributeValue("age")));
					if(ren.getAttributeValue("sexe") == "femelle"){
						renard.setSexe(EnumSexe.femelle);
					}else{
						renard.setSexe(EnumSexe.male);
					}
					int coordX = Integer.parseInt(ren.getAttributeValue("coordX"));
					int coordY = Integer.parseInt(ren.getAttributeValue("coordY"));
					renard.setCoordonee(new Coordonnee(coordX,coordY));
					
					
					//Affichage des éléments
					System.out.println("Faim : "+renard.getFaim());
					System.out.println("Fatigue :"+renard.getFatigue());
					System.out.println("Durée de Vie :"+renard.getDureeVie());
					System.out.println("Mode de Vie :"+renard.getModeDeVie());
					System.out.println("Portee : "+renard.getPortee());
					System.out.println("Rayon :"+renard.getRayon());
					System.out.println("Valeur Energetique : "+ renard.getValeurEnergetique());
					System.out.println("Stade :"+ renard.getStade());
					System.out.println("Age : "+ renard.getAge());
					System.out.println("Sexe : "+ renard.getSexe());
					System.out.println("Coordonnée :"+ renard.getCoordonnee().getX() + " , " +renard.getCoordonnee().getY());
				
					//grilleDeJeu[renard.getCoordonnee().getX()][renard.getCoordonnee().getY()].setVide(false);
					listEntites.add(renard);
					System.out.println();
				}
			}
		}
		
		//Récupération des données sur les lapins
		List<Element> listLapins = entites.getChildren("lapins");
		for(int i = 0 ; i<listLapins.size(); i++){
			System.out.println("------------LAPINS------------------");
			Element e = (Element)listLapins.get(i);
			List<Element> listLapin = e.getChildren("lapin");
			
			if(listLapin.size() == 0){
				System.out.println("Pas de lapins");
			}else{
				int portee = Integer.parseInt(e.getAttributeValue("portee"));
				int rayon = Integer.parseInt(e.getAttributeValue("rayon"));
				int valeurEnergetique = Integer.parseInt(e.getAttributeValue("valeurEnergetique"));
				int dureeVie = Integer.parseInt(e.getAttributeValue("dureeVie"));
				
				for(int h=0; h < listLapin.size() ; h++){
					System.out.println("Lapin "+(h+1));
					Lapin lapin = new Lapin();
					
					Element lap = (Element)listLapin.get(h);
					
					lapin.setPortee(portee);
					lapin.setRayon(rayon);
					lapin.setValeurEnergetique(valeurEnergetique);
					lapin.setDureeVie(dureeVie);
					if(lap.getAttributeValue("stade") == "jeune"){
						lapin.setStade(EnumStade.jeune);
					}else if (lap.getAttributeValue("stade") == "adulte"){
						lapin.setStade(EnumStade.adulte);
					}else if (lap.getAttributeValue("stade") == "vieux"){
						lapin.setStade(EnumStade.vieux);
					}
					lapin.setFaim(Integer.parseInt(lap.getAttributeValue("faim")));
					lapin.setFatigue(Integer.parseInt(lap.getAttributeValue("fatigue")));
					
					if(e.getAttributeValue("modeDeVie") == "terrestre"){
						System.out.println("ici");
						lapin.setModeDeVie(EnumModeDeVie.terrestre);
					}else if (e.getAttributeValue("modeDeVie") == "aquatique"){
						lapin.setModeDeVie(EnumModeDeVie.aquatique);
					}else if (e.getAttributeValue("modeDeVie") == "amphibie"){
						lapin.setModeDeVie(EnumModeDeVie.amphibie);
					}else{
						//Par défaut terrestre
						lapin.setModeDeVie(EnumModeDeVie.terrestre);
					}
					lapin.setAge(Integer.parseInt(lap.getAttributeValue("age")));
					if(lap.getAttributeValue("sexe") == "femelle"){
						lapin.setSexe(EnumSexe.femelle);
					}else{
						lapin.setSexe(EnumSexe.male);
					}
					lapin.setCoordonee(new Coordonnee(Integer.parseInt(lap.getAttributeValue("coordX")),Integer.parseInt(lap.getAttributeValue("coordY"))));
					
					//Affichage des éléments
					System.out.println("Faim : "+lapin.getFaim());
					System.out.println("Fatigue :"+lapin.getFatigue());
					System.out.println("Durée de Vie :"+lapin.getDureeVie());
					System.out.println("Mode de Vie :"+lapin.getModeDeVie());
					System.out.println("Portee : "+lapin.getPortee());
					System.out.println("Rayon :"+ lapin.getRayon());
					System.out.println("Valeur Energetique : "+ lapin.getValeurEnergetique());
					System.out.println("Stade :"+ lapin.getStade());
					System.out.println("Age : "+ lapin.getAge());
					System.out.println("Sexe : "+ lapin.getSexe());
					System.out.println("Coordonnée :"+ lapin.getCoordonnee().getX() + " , " + lapin.getCoordonnee().getY());
					
					//grilleDeJeu[lapin.getCoordonnee().getX()][lapin.getCoordonnee().getY()].setVide(false);
					listEntites.add(lapin);
					System.out.println();
				}
			}
		}
		//Récupération des données sur les chamois
		List<Element> listChamois = entites.getChildren("chamois");
		for(int i = 0 ; i<listChamois.size(); i++){
			System.out.println("------------CHAMOIS------------------");
			Element e = (Element)listChamois.get(i);
			List<Element> listChamoi = e.getChildren("chamoi");
			
			if(listChamoi.size() == 0){
				System.out.println("Pas de chamois");
			}else{
				int portee = Integer.parseInt(e.getAttributeValue("portee"));
				int rayon = Integer.parseInt(e.getAttributeValue("rayon"));
				int valeurEnergetique = Integer.parseInt(e.getAttributeValue("valeurEnergetique"));
				int dureeVie = Integer.parseInt(e.getAttributeValue("dureeVie"));
					
				for(int h=0; h < listChamoi.size() ; h++){
					System.out.println("Chamois "+(h+1));
					Chamois chamois = new Chamois();
						
					Element ch = (Element)listChamoi.get(h);
							
					chamois.setPortee(portee);
					chamois.setRayon(rayon);
					chamois.setValeurEnergetique(valeurEnergetique);
					chamois.setDureeVie(dureeVie);
					if(ch.getAttributeValue("stade") == "jeune"){
						chamois.setStade(EnumStade.jeune);
					}else if (ch.getAttributeValue("stade") == "adulte"){
						chamois.setStade(EnumStade.adulte);
					}else if (ch.getAttributeValue("stade") == "vieux"){
						chamois.setStade(EnumStade.vieux);
					}
					chamois.setFaim(Integer.parseInt(ch.getAttributeValue("faim")));
					chamois.setFatigue(Integer.parseInt(ch.getAttributeValue("fatigue")));
							
					if(e.getAttributeValue("modeDeVie") == "terrestre"){
						System.out.println("ici");
						chamois.setModeDeVie(EnumModeDeVie.terrestre);
					}else if (e.getAttributeValue("modeDeVie") == "aquatique"){
						chamois.setModeDeVie(EnumModeDeVie.aquatique);
					}else if (e.getAttributeValue("modeDeVie") == "amphibie"){
						chamois.setModeDeVie(EnumModeDeVie.amphibie);
					}else{
						//Par défaut terrestre
						chamois.setModeDeVie(EnumModeDeVie.terrestre);
					}
					chamois.setAge(Integer.parseInt(ch.getAttributeValue("age")));
					if(ch.getAttributeValue("sexe") == "femelle"){
						chamois.setSexe(EnumSexe.femelle);
					}else{
						chamois.setSexe(EnumSexe.male);
					}
					chamois.setCoordonee(new Coordonnee(Integer.parseInt(ch.getAttributeValue("coordX")),Integer.parseInt(ch.getAttributeValue("coordY"))));
					
					//Affichage des éléments
					System.out.println("Faim : "+chamois.getFaim());
					System.out.println("Fatigue :"+chamois.getFatigue());
					System.out.println("Durée de Vie :"+chamois.getDureeVie());
					System.out.println("Mode de Vie :"+chamois.getModeDeVie());
					System.out.println("Portee : "+chamois.getPortee());
					System.out.println("Rayon :"+ chamois.getRayon());
					System.out.println("Valeur Energetique : "+ chamois.getValeurEnergetique());
					System.out.println("Stade :"+ chamois.getStade());
					System.out.println("Age : "+ chamois.getAge());
					System.out.println("Sexe : "+ chamois.getSexe());
					System.out.println("Coordonnée :"+ chamois.getCoordonnee().getX() + " , " + chamois.getCoordonnee().getY());
					
					//grilleDeJeu[chamois.getCoordonnee().getX()][chamois.getCoordonnee().getY()].setVide(false);
					listEntites.add(chamois);
					System.out.println();
				}
			}
		}
		//Récupération des données sur les loups
		List<Element> listLoups = entites.getChildren("loups");
		for(int i = 0 ; i<listLoups.size(); i++){
			System.out.println("------------LOUPS------------------");
			Element e = (Element)listLoups.get(i);
			List<Element> listLoup = e.getChildren("loup");
			
			if(listLoup.size() == 0){
				System.out.println("Pas de sanglier");
			}else{
				int portee = Integer.parseInt(e.getAttributeValue("portee"));
				int rayon = Integer.parseInt(e.getAttributeValue("rayon"));
				int valeurEnergetique = Integer.parseInt(e.getAttributeValue("valeurEnergetique"));
				int dureeVie = Integer.parseInt(e.getAttributeValue("dureeVie"));
						
				for(int h=0; h < listLoup.size() ; h++){
					System.out.println("Loup "+(h+1));
					Loup animal = new Loup();
						
					Element ani = (Element)listLoup.get(h);
								
					animal.setPortee(portee);
					animal.setRayon(rayon);
					animal.setValeurEnergetique(valeurEnergetique);
					animal.setDureeVie(dureeVie);
					if(ani.getAttributeValue("stade") == "jeune"){
						animal.setStade(EnumStade.jeune);
					}else if (ani.getAttributeValue("stade") == "adulte"){
						animal.setStade(EnumStade.adulte);
					}else if (ani.getAttributeValue("stade") == "vieux"){
						animal.setStade(EnumStade.vieux);
					}
					animal.setFaim(Integer.parseInt(ani.getAttributeValue("faim")));
					animal.setFatigue(Integer.parseInt(ani.getAttributeValue("fatigue")));
								
					if(e.getAttributeValue("modeDeVie") == "terrestre"){
						System.out.println("ici");
						animal.setModeDeVie(EnumModeDeVie.terrestre);
					}else if (e.getAttributeValue("modeDeVie") == "aquatique"){
						animal.setModeDeVie(EnumModeDeVie.aquatique);
					}else if (e.getAttributeValue("modeDeVie") == "amphibie"){
						animal.setModeDeVie(EnumModeDeVie.amphibie);
					}else{
						//Par défaut terrestre
						animal.setModeDeVie(EnumModeDeVie.terrestre);
					}
					animal.setAge(Integer.parseInt(ani.getAttributeValue("age")));
					if(ani.getAttributeValue("sexe") == "femelle"){
						animal.setSexe(EnumSexe.femelle);
					}else{
						animal.setSexe(EnumSexe.male);
					}
					animal.setCoordonee(new Coordonnee(Integer.parseInt(ani.getAttributeValue("coordX")),Integer.parseInt(ani.getAttributeValue("coordY"))));
						
					//Affichage des éléments
					System.out.println("Faim : "+animal.getFaim());
					System.out.println("Fatigue :"+animal.getFatigue());
					System.out.println("Durée de Vie :"+animal.getDureeVie());
					System.out.println("Mode de Vie :"+animal.getModeDeVie());
					System.out.println("Portee : "+animal.getPortee());
					System.out.println("Rayon :"+ animal.getRayon());
					System.out.println("Valeur Energetique : "+ animal.getValeurEnergetique());
					System.out.println("Stade :"+ animal.getStade());
					System.out.println("Age : "+ animal.getAge());
					System.out.println("Sexe : "+ animal.getSexe());
					System.out.println("Coordonnée :"+ animal.getCoordonnee().getX() + " , " + animal.getCoordonnee().getY());
					
					//grilleDeJeu[animal.getCoordonnee().getX()][animal.getCoordonnee().getY()].setVide(false);
					listEntites.add(animal);
					System.out.println();
				}
			}
		}		
		
		//Récupération des données sur les moutons
		List<Element> listMoutons = entites.getChildren("moutons");
		for(int i = 0 ; i<listMoutons.size(); i++){
			System.out.println("------------MOUTONS------------------");
			Element e = (Element)listMoutons.get(i);
			List<Element> listMouton = e.getChildren("mouton");
			
			if(listMouton.size() == 0){
				System.out.println("Pas de sanglier");
			}else{
				int portee = Integer.parseInt(e.getAttributeValue("portee"));
				int rayon = Integer.parseInt(e.getAttributeValue("rayon"));
				int valeurEnergetique = Integer.parseInt(e.getAttributeValue("valeurEnergetique"));
				int dureeVie = Integer.parseInt(e.getAttributeValue("dureeVie"));
						
				for(int h=0; h < listMouton.size() ; h++){
					System.out.println("Mouton "+(h+1));
					Mouton animal = new Mouton();
						
					Element ani = (Element)listMouton.get(h);
								
					animal.setPortee(portee);
					animal.setRayon(rayon);
					animal.setValeurEnergetique(valeurEnergetique);
					animal.setDureeVie(dureeVie);
					if(ani.getAttributeValue("stade") == "jeune"){
						animal.setStade(EnumStade.jeune);
					}else if (ani.getAttributeValue("stade") == "adulte"){
						animal.setStade(EnumStade.adulte);
					}else if (ani.getAttributeValue("stade") == "vieux"){
						animal.setStade(EnumStade.vieux);
					}
					animal.setFaim(Integer.parseInt(ani.getAttributeValue("faim")));
					animal.setFatigue(Integer.parseInt(ani.getAttributeValue("fatigue")));
								
					if(e.getAttributeValue("modeDeVie") == "terrestre"){
						System.out.println("ici");
						animal.setModeDeVie(EnumModeDeVie.terrestre);
					}else if (e.getAttributeValue("modeDeVie") == "aquatique"){
						animal.setModeDeVie(EnumModeDeVie.aquatique);
					}else if (e.getAttributeValue("modeDeVie") == "amphibie"){
						animal.setModeDeVie(EnumModeDeVie.amphibie);
					}else{
						//Par défaut terrestre
						animal.setModeDeVie(EnumModeDeVie.terrestre);
					}
					animal.setAge(Integer.parseInt(ani.getAttributeValue("age")));
					if(ani.getAttributeValue("sexe") == "femelle"){
						animal.setSexe(EnumSexe.femelle);
					}else{
						animal.setSexe(EnumSexe.male);
					}
					animal.setCoordonee(new Coordonnee(Integer.parseInt(ani.getAttributeValue("coordX")),Integer.parseInt(ani.getAttributeValue("coordY"))));
						
					//Affichage des éléments
					System.out.println("Faim : "+animal.getFaim());
					System.out.println("Fatigue :"+animal.getFatigue());
					System.out.println("Durée de Vie :"+animal.getDureeVie());
					System.out.println("Mode de Vie :"+animal.getModeDeVie());
					System.out.println("Portee : "+animal.getPortee());
					System.out.println("Rayon :"+ animal.getRayon());
					System.out.println("Valeur Energetique : "+ animal.getValeurEnergetique());
					System.out.println("Stade :"+ animal.getStade());
					System.out.println("Age : "+ animal.getAge());
					System.out.println("Sexe : "+ animal.getSexe());
					System.out.println("Coordonnée :"+ animal.getCoordonnee().getX() + " , " + animal.getCoordonnee().getY());
					
					//grilleDeJeu[animal.getCoordonnee().getX()][animal.getCoordonnee().getY()].setVide(false);
					listEntites.add(animal);
					System.out.println();
				}
			}
		}		
		
		//Récupération des données sur les sangliers
		List<Element> listSangliers = entites.getChildren("sangliers");
		for(int i = 0 ; i<listSangliers.size(); i++){
			System.out.println("------------SANGLIERS------------------");
			Element e = (Element)listSangliers.get(i);
			List<Element> listSanglier = e.getChildren("sanglier");
			
			if(listSanglier.size() == 0){
				System.out.println("Pas de sanglier");
			}else{
				int portee = Integer.parseInt(e.getAttributeValue("portee"));
				int rayon = Integer.parseInt(e.getAttributeValue("rayon"));
				int valeurEnergetique = Integer.parseInt(e.getAttributeValue("valeurEnergetique"));
				int dureeVie = Integer.parseInt(e.getAttributeValue("dureeVie"));
						
				for(int h=0; h < listSanglier.size() ; h++){
					System.out.println("Sanglier "+(h+1));
					Sanglier animal = new Sanglier();
						
					Element ani = (Element)listSanglier.get(h);
								
					animal.setPortee(portee);
					animal.setRayon(rayon);
					animal.setValeurEnergetique(valeurEnergetique);
					animal.setDureeVie(dureeVie);
					if(ani.getAttributeValue("stade") == "jeune"){
						animal.setStade(EnumStade.jeune);
					}else if (ani.getAttributeValue("stade") == "adulte"){
						animal.setStade(EnumStade.adulte);
					}else if (ani.getAttributeValue("stade") == "vieux"){
						animal.setStade(EnumStade.vieux);
					}
					animal.setFaim(Integer.parseInt(ani.getAttributeValue("faim")));
					animal.setFatigue(Integer.parseInt(ani.getAttributeValue("fatigue")));
								
					if(e.getAttributeValue("modeDeVie") == "terrestre"){
						System.out.println("ici");
						animal.setModeDeVie(EnumModeDeVie.terrestre);
					}else if (e.getAttributeValue("modeDeVie") == "aquatique"){
						animal.setModeDeVie(EnumModeDeVie.aquatique);
					}else if (e.getAttributeValue("modeDeVie") == "amphibie"){
						animal.setModeDeVie(EnumModeDeVie.amphibie);
					}else{
						//Par défaut terrestre
						animal.setModeDeVie(EnumModeDeVie.terrestre);
					}
					animal.setAge(Integer.parseInt(ani.getAttributeValue("age")));
					if(ani.getAttributeValue("sexe") == "femelle"){
						animal.setSexe(EnumSexe.femelle);
					}else{
						animal.setSexe(EnumSexe.male);
					}
					animal.setCoordonee(new Coordonnee(Integer.parseInt(ani.getAttributeValue("coordX")),Integer.parseInt(ani.getAttributeValue("coordY"))));
						
					//Affichage des éléments
					System.out.println("Faim : "+animal.getFaim());
					System.out.println("Fatigue :"+animal.getFatigue());
					System.out.println("Durée de Vie :"+animal.getDureeVie());
					System.out.println("Mode de Vie :"+animal.getModeDeVie());
					System.out.println("Portee : "+animal.getPortee());
					System.out.println("Rayon :"+ animal.getRayon());
					System.out.println("Valeur Energetique : "+ animal.getValeurEnergetique());
					System.out.println("Stade :"+ animal.getStade());
					System.out.println("Age : "+ animal.getAge());
					System.out.println("Sexe : "+ animal.getSexe());
					System.out.println("Coordonnée :"+ animal.getCoordonnee().getX() + " , " + animal.getCoordonnee().getY());
					
					//grilleDeJeu[animal.getCoordonnee().getX()][animal.getCoordonnee().getY()].setVide(false);
					listEntites.add(animal);
					System.out.println();
				}
			}
		}
		
		//Récupération des entités Arbres
		List<Element> listArbres = entites.getChildren("arbres");
		for(int i = 0 ; i<listArbres.size(); i++){
			System.out.println("------------ARBRES------------------");
			Element e = (Element)listArbres.get(i);
			List<Element> listArbre = e.getChildren("arbre");
			
			if(listArbre.size() == 0){
				System.out.println("Pas d'arbres");
			}else{
				int cycle = Integer.parseInt(e.getAttributeValue("cycle"));
				int quantiteMax = Integer.parseInt(e.getAttributeValue("quantiteMax"));
				int valeurEnergetique = Integer.parseInt(e.getAttributeValue("valeurEnergetique"));
	
				for(int h=0; h < listArbre.size() ; h++){
					System.out.println("Arbre "+(h+1));
					Arbre vegetal = new Arbre();
						
					Element veg = (Element)listArbre.get(h);
								
					vegetal.setCycle(cycle);
					vegetal.setQuantiteMax(quantiteMax);
					vegetal.setValeurEnergetique(valeurEnergetique);
				
					vegetal.setAge(Integer.parseInt(veg.getAttributeValue("age")));
					vegetal.setCoordonee(new Coordonnee(Integer.parseInt(veg.getAttributeValue("coordX")),Integer.parseInt(veg.getAttributeValue("coordY"))));
						
					//Affichage des éléments
					System.out.println("Cycle : "+vegetal.getCycle());
					System.out.println("Quantité max :"+ vegetal.getQuantiteMax());
					System.out.println("Valeur Energetique : "+ vegetal.getValeurEnergetique());
					System.out.println("Quantite Now :"+ vegetal.getQuantiteNow());
					System.out.println("Age : "+ vegetal.getAge());
					System.out.println("Coordonnée :"+ vegetal.getCoordonnee().getX() + " , " + vegetal.getCoordonnee().getY());
					
					//grilleDeJeu[vegetal.getCoordonnee().getX()][vegetal.getCoordonnee().getY()].setVide(false);
					listEntites.add(vegetal);
					System.out.println();
				}
			}
		}
		
		//Récupération des entités Herbes
		List<Element> listHerbes = entites.getChildren("herbes");
		for(int i = 0 ; i<listHerbes.size(); i++){
			System.out.println("------------HERBES------------------");
			Element e = (Element)listHerbes.get(i);
			List<Element> listHerbe = e.getChildren("herbe");
			
			if(listHerbe.size() == 0){
				System.out.println("Pas d'herbes");
			}else{
				int cycle = Integer.parseInt(e.getAttributeValue("cycle"));
				int quantiteMax = Integer.parseInt(e.getAttributeValue("quantiteMax"));
				int valeurEnergetique = Integer.parseInt(e.getAttributeValue("valeurEnergetique"));
	
				for(int h=0; h < listHerbe.size() ; h++){
					System.out.println("Herbe "+(h+1));
					Herbe vegetal = new Herbe();
						
					Element veg = (Element)listHerbe.get(h);
								
					vegetal.setCycle(cycle);
					vegetal.setQuantiteMax(quantiteMax);
					vegetal.setValeurEnergetique(valeurEnergetique);
				
					vegetal.setAge(Integer.parseInt(veg.getAttributeValue("age")));
					vegetal.setCoordonee(new Coordonnee(Integer.parseInt(veg.getAttributeValue("coordX")),Integer.parseInt(veg.getAttributeValue("coordY"))));
						
					//Affichage des éléments
					System.out.println("Cycle : "+vegetal.getCycle());
					System.out.println("Quantité max :"+ vegetal.getQuantiteMax());
					System.out.println("Valeur Energetique : "+ vegetal.getValeurEnergetique());
					System.out.println("Quantite Now :"+ vegetal.getQuantiteNow());
					System.out.println("Age : "+ vegetal.getAge());
					System.out.println("Coordonnée :"+ vegetal.getCoordonnee().getX() + " , " + vegetal.getCoordonnee().getY());
					
					//grilleDeJeu[vegetal.getCoordonnee().getX()][vegetal.getCoordonnee().getY()].setVide(false);
					listEntites.add(vegetal);
					System.out.println();
				}
			}
		}			

		System.out.println("Nombre d'entités : "+listEntites.size());
		return listEntites;
	}

	
	/**
	 * Permet de sauvegarder une partie en écrivant dans un XML
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	/*public static void ecrireXML() throws IOException, JDOMException {
		System.out.println("\n\n>>>>>>>>>>>>>>>>>>>>>>>>>>ECRIRE XML<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
		
		Element racineSauv = new Element ("map");
		Document documentSauv = new Document (racineSauv);
		
		Element taille = new Element ("taille");
		racineSauv.addContent(taille);
		
		Element environnements = new Element ("environnements");
		
		Element environnement = new Element ("environnement");
		environnement.setAttribute(new Attribute ("name","plaine"));
		environnement.setAttribute(new Attribute ("coordX_debut","a trouver"));
		environnement.setAttribute(new Attribute ("coordY_debut","a trouver"));
		environnement.setAttribute(new Attribute ("coordX_fin","a trouver"));
		environnement.setAttribute(new Attribute ("coordY_fin", "a trouver"));
		
		Element environnement2 = new Element ("environnement");
		environnement2.setAttribute(new Attribute ("name","eau"));
		environnement2.setAttribute(new Attribute ("coordX_debut","a trouver"));
		environnement2.setAttribute(new Attribute ("coordY_debut","a trouver"));
		environnement2.setAttribute(new Attribute ("coordX_fin","a trouver"));
		environnement2.setAttribute(new Attribute ("coordY_fin", "a trouver"));
		
		Element environnement3 = new Element ("environnement");
		environnement3.setAttribute(new Attribute ("name","montagne"));
		environnement3.setAttribute(new Attribute ("coordX_debut","a trouver"));
		environnement3.setAttribute(new Attribute ("coordY_debut","a trouver"));
		environnement3.setAttribute(new Attribute ("coordX_fin","a trouver"));
		environnement3.setAttribute(new Attribute ("coordY_fin", "a trouver"));
		
		environnements.addContent(environnement);
		environnements.addContent(environnement2);
		environnements.addContent(environnement3);
	       
		affiche();
		enregistre("save.xml");
	}*/

	/**
	 * Permet d'afficher le XML sauvegardé
	 */
	static void affiche()
	{
	   try
	   {
	      //On utilise ici un affichage classique avec getPrettyFormat()
	      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	      sortie.output(documentSauv, System.out);
	   }
	   catch (java.io.IOException e){}
	}

	
	/**
	 * Permet de sauvegarder la map dans un fichier XML
	 * @param fichier
	 */
	static void enregistre(String fichier)
	{
	   try
	   {
	      //On utilise ici un affichage classique avec getPrettyFormat()
	      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	      //effectuer la sérialisation
	      sortie.output(documentSauv, new FileOutputStream(fichier));
	   }
	   catch (java.io.IOException e){}
	}
	
	
	/**
	 * i == 0 permet de savoir si on a déjà récupéré les entités du fichier XML
	 * @return la liste des entités en temps réel
	 */
	public static List<Entite> listEntiteTempsReel(){
		if(i==0){
			try {
				listEntites = lireXML(fichier);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i=1;
		}
		return listEntites;
	}
	
	
	/**
	 * Ajouter une entité à la liste d'entité de la map
	 * @param entite
	 */
	public static void ajoutEntite(Entite entite){
		listEntites.add(entite);
		//grilleDeJeu[entite.getCoordonnee().getX()][entite.getCoordonnee().getY()].setVide(false);
	}
	
	
	/**
	 * Supprime une entité de la liste d'entité de la map
	 * @param entite
	 */
	public static void supprimeEntite(Entite entite){
		listEntites.remove(entite);
		//grilleDeJeu[entite.getCoordonnee().getX()][entite.getCoordonnee().getY()].setVide(true);
	}
	
	
	/**
	 * Main principale pour tester la map et l'interface graphique
	 */
	public static void main(String[] args) throws IOException {		
		Entite lapin = new Lapin ();
		lapin.setCoordonee(new Coordonnee(1,0));
		
		//Chargement de la map
		listEntites = listEntiteTempsReel();
		
		//Ajout d'une entité 
		ajoutEntite(lapin);
		
		//supprimer une entité
		//supprimeEntite(lapin);
		
		//Teste de la fonction perception
		List<Entite> listePerception = new ArrayList<Entite>();
		listePerception = perception(lapin.getCoordonnee(),1);
		
		taille = Integer.parseInt(racine.getChildText("taille"));
		Map map = new Map(taille);
		
		Vue vue = new InterfaceGraphique();
		vue.fenetre(new Partie(map));
		vue.dessineMap(map);
		vue.dessineEntite(listEntites);
		
		while(true)
		{
			try {
				new Thread();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			vue.dessineMap(map);
			vue.dessineEntite(listEntites);
			Iterator<Entite> it =listEntites.iterator();
			while(it.hasNext())
			{
				Entite ent = it.next();
				Coordonnee cord = ent.getCoordonnee();
				cord.setCoordonnee((cord.getX()+1)%5, (cord.getY()+1)%5);
				//ent.live(map);
			}
		}
	}

	//TODO à faire ! 
	public static List<Entite> perception(Coordonnee co, int rayon) {
		List<Entite> listeEntitePerception = new ArrayList<Entite>();
		
		for(int i = co.getX()-rayon; i <=co.getX()+rayon ; i++){
			for(int j = co.getY() - rayon ; j <= co.getY()+rayon ; j++){
				for(int h=0; h < listEntites.size() ; h++){
					if(co.getX() == i && co.getY() == j){
						//On ne prend pas en compte l'entité qui cherche au alentour
					}else{
						if(listEntites.get(h).getCoordonnee().getX() == i && listEntites.get(h).getCoordonnee().getY() == j){
						listeEntitePerception.add(listEntites.get(h));
					}
					}
					
				}
			}
		}
		System.out.println("\nListe d'entité avec la méthode perception : " + listeEntitePerception);
		return listeEntitePerception;
	}

	
	/**
	 * Getters et setters
	 */
	public int getTaille() {
		return taille;
	}
	public Case[][] getGrilleDeJeu() {
		return grilleDeJeu;
	}
	public Nourriture getNourriture() {
		return nourriture;
	}
	public void setNourriture(Nourriture nourriture) {
		this.nourriture = nourriture;
	}
	public int getRayon() {
		return rayon;
	}
	public void setRayon(int rayon) {
		this.rayon = rayon;
	}
	public static Coordonnee getCoordonnee() {
		return coordonnee;
	}
	public static void setCoordonnee(Coordonnee coordonnee) {
		Map.coordonnee = coordonnee;
	}
	public Environnement getEnvironnement() {
		return environnement;
	}
	public void setEnvironnement(Coordonnee cord, EnumEnvironnement env) {
		this.grilleDeJeu[cord.getX()][cord.getY()].setEnvironnement(env);
		//this.environnement = environnement;
	}
}

