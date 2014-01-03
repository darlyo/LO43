package Carte;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

import Entite.Entite;
import Entite.Animaux.Chamois;
import Entite.Animaux.Lapin;
import Entite.Animaux.Loup;
import Entite.Animaux.Mouton;
import Entite.Animaux.Renard;
import Entite.Animaux.Sanglier;
import Enumeration.EnumEnvironnement;
import Enumeration.EnumModeDeVie;
import Enumeration.EnumSexe;
import Enumeration.EnumStade;
import GUI.InterfaceGraphique;
import GUI.Vue;

public class Map {
	private static Document document;
	private static Element racine;
	private static int taille;
	protected static Case [][] grilleDeJeu;
	private Nourriture nourriture;
	private Environnement environnement;
	private static Coordonnee coordonnee;
	public static List<Entite> listEntites;
	private int rayon;
	
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
			List listEnv = e.getChildren("environnement");
			
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
			List listRenard = e.getChildren("renard");
			
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
					System.out.println("Coordonnée :"+ renard.getCoordonnee());
				
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
			List listLapin = e.getChildren("lapin");
			
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
					System.out.println("Coordonnée :"+ lapin.getCoordonnee());
					
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
			List listChamoi = e.getChildren("chamoi");
			
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
					System.out.println("Coordonnée :"+ chamois.getCoordonnee());
					
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
			List listLoup = e.getChildren("loup");
			
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
					System.out.println("Coordonnée :"+ animal.getCoordonnee());
							
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
			List listMouton = e.getChildren("mouton");
			
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
					System.out.println("Coordonnée :"+ animal.getCoordonnee());
							
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
			List listSanglier = e.getChildren("sanglier");
			
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
					System.out.println("Coordonnée :"+ animal.getCoordonnee());
							
					listEntites.add(animal);
					System.out.println();
				}
			}
		}
				
		
		System.out.println(listEntites.size());
		return listEntites;
	}

	/**
	 * Permet de sauvegarder une partie en écrivant dans un XML
	 */
	public void ecrireXML() {
	}
	
	static void afficher(Document document) throws Exception{
	         XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	         sortie.output(document, System.out);
	   }
	
	public static void main(String[] args) throws IOException {
		String fichier = new String ("src/Carte/Map.xml");
		Vue vue = new InterfaceGraphique();
		vue.fenetre();
		listEntites= new ArrayList<Entite>();
		
		try {
			listEntites = lireXML(fichier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		taille = Integer.parseInt(racine.getChildText("taille"));
		Map map = new Map(taille);
		
		/*//A mettre dans dessineMap je pense ;)
		for(int j=0 ;j<=taille ; j++){
			for(int k=0 ; k <=taille; k++){
				if(map.getGrilleDeJeu()[j][k].getEnvironnement() == EnumEnvironnement.plaine){
					//color en vert
				}else if(map.getGrilleDeJeu()[j][k].getEnvironnement() == EnumEnvironnement.eau){
					//color en bleu
				}else if(map.getGrilleDeJeu()[j][k].getEnvironnement() == EnumEnvironnement.montagne){
					//color en gris
				}
			}
		}	*/
		vue.dessineMap(map);
		vue.dessineEntite(listEntites);
		
		while(true)
		{
			vue.dessineMap(map);
			vue.dessineEntite(listEntites);
		}
		
	}

	//TODO à faire ! 
	public List<Entite> perception(Coordonnee co, int rayon) {
		List<Entite> listeEntite = new ArrayList<Entite>();
		
		return listeEntite;
	}

	
	/**
	 * Getters et setters
	 * 
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
	public void setEnvironnement(Environnement environnement) {
		this.environnement = environnement;
	}
}

