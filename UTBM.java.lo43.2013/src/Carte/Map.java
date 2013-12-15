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

import Entite.Entite;
import Entite.Animaux.Renard;
import Enumeration.EnumEnvironnement;

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
		grilleDeJeu = new Case[taille][taille];
		for(int j=0; j <taille; j++){
			for(int k = 0; k<taille; k++){
				grilleDeJeu[j][k] = new Case();
				System.out.print("|_");
			}
			System.out.print("|");
			 System.out.println();
		}
	}
	
	/**
	 * Permet de parser le XML et de créer la map 
	 */
	public static void lireXML(String fichier) throws Exception{
		
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

		
		//TODO Liste des Entités du fichier XML
		List<Element> listEntitesRecupere = racine.getChildren("entites");
		
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
		}
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
		
		try {
			lireXML(fichier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		taille = Integer.parseInt(racine.getChildText("taille"));
		Map map = new Map(taille);
		
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

