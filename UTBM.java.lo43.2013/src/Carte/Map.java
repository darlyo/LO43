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
import Entite.Animaux.Renard;
import Enumeration.EnumEnvironnement;
import Enumeration.EnumModeDeVie;
import Enumeration.EnumSexe;
import Enumeration.EnumStade;

public class Map {
	private static Document document;
	private static Element racine;
	private static int taille;
	protected static Case [][] grilleDeJeu;
	private Nourriture nourriture;
	private Environnement environnement;
	private static Coordonnee coordonnee;
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
	 * Permet de parser le XML et de cr�er la map 
	 */
	public static void lireXML(String fichier) throws Exception{
		
		/*On cr�e une instance de SAXBuilder*/
		SAXBuilder sxb = new SAXBuilder();
		/*On cr�e un nouveau document JDOM*/
		document = sxb.build(new File(fichier));
		/*On initialise un nouvel �l�ment racine avec l'�l�ment racine du document*/
		racine = document.getRootElement();
		
		/*R�cup�ration de la taille de la map*/
		String tailleXML = racine.getChildText("taille");
		taille = Integer.parseInt(tailleXML);
		System.out.println("Taille de la map : " + taille);
		
		/*R�cup�ration des �l�ments environnement du fichier XML*/
		List<Element> listEnvs = racine.getChildren("environnements");
		System.out.println("\n-------------ENVIRONNEMENTS------------- : ");
		
		for(int i = 0 ; i<listEnvs.size(); i++){
			
			Element e = (Element)listEnvs.get(i);
			List listEnv = e.getChildren("environnement");
			
			for(int h=0; h < listEnv.size() ; h++){
				Element env = (Element)listEnv.get(h);
				
				String nomEnv = env.getAttributeValue("name");
				System.out.println("Nom: " + nomEnv);
				 
				/*R�cup�ration des coordonn�es des environnements sur la grille*/
				int coordX_D = Integer.parseInt(env.getAttributeValue("coordX_debut"));
				int coordY_D = Integer.parseInt(env.getAttributeValue("coordY_debut"));
				int coordX_F = Integer.parseInt(env.getAttributeValue("coordX_fin"));
				int coordY_F = Integer.parseInt(env.getAttributeValue("coordY_fin"));
				
				/*Affichage des coordonn�es des environnements*/
				System.out.println("Coord X debut: " + coordX_D + " ; Coord Y d�but: " + coordY_D);
				System.out.println("Coord X fin: " + coordX_F + " ; Coord Y fin: " + coordY_F);
				
				/*Affectation des cases de la grille aux diff�rents environnements*/
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

		/*R�cup�ration des �l�ments entit�s du fichier XML*/
		List<Entite> listEntites = new ArrayList<Entite>();
		
		Element entites = racine.getChild("entites");
		System.out.println("\n-------------ENTITES (ANIMAUX ET VEGETAUX)------------- : ");
		
		List<Element> listRenards = entites.getChildren("renards");
		for(int i = 0 ; i<listRenards.size(); i++){
			System.out.println("RENARDS :");
			Element e = (Element)listRenards.get(i);
			List listRenard = e.getChildren("renard");
			
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
					//Par d�faut terrestre
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
				
				
				//Affichage des �l�ments de renard
				System.out.println("Faim : "+renard.getFaim());
				System.out.println("Fatigue :"+renard.getFatigue());
				System.out.println("Dur�e de Vie :"+renard.getDureeVie());
				System.out.println("Mode de Vie :"+renard.getModeDeVie());
				System.out.println("Portee : "+renard.getPortee());
				System.out.println("Rayon :"+renard.getRayon());
				System.out.println("Valeur Energetique : "+ renard.getValeurEnergetique());
				System.out.println("Stade :"+ renard.getStade());
				System.out.println("Age : "+ renard.getAge());
				System.out.println("Sexe : "+ renard.getSexe());
				System.out.println("Coordonn�e :"+ renard.getCoordonee());
				
				
				 
				listEntites.add(renard);
				System.out.println();
			}
			
		}
		
		//TODO Liste des Entit�s du fichier XML
		/*List<Element> listEntitesRecupere = racine.getChildren("entites");
		
		System.out.println("---------------ENTITES---------------");
		
		for(int i=0; i<listEntitesRecupere.size(); i++){
			Element courant = listEntitesRecupere.get(i);
			List listR = courant.getChildren("renard");
			for(int j=0; j<listR.size(); j++){
				Element ren = (Element)listR.get(j);
				
				System.out.print("Renard " + ren.getAttributeValue("id")+ " ");
				System.out.print(" (" + ren.getAttributeValue("coordX")+ " ; ");
				System.out.print(ren.getAttributeValue("coordY")+ ") ");
				System.out.println("Faim : " + ren.getChildText("faim"));
			}
			System.out.println();
		}*/
	}

	/**
	 * Permet de sauvegarder une partie en �crivant dans un XML
	 */
	public void ecrireXML() {
	}
	
	static void afficher(Document document) throws Exception{
	         XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	         sortie.output(document, System.out);
	   }
	
	public static void main(String[] args) throws IOException {
		String fichier = new String ("src/Carte/Map.xml");
		
		try {
			lireXML(fichier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		taille = Integer.parseInt(racine.getChildText("taille"));
		Map map = new Map(taille);
		
	}

	//TODO � faire ! 
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

