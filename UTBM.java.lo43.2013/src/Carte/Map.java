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
	protected static Case [][] grille;
	private Nourriture nourriture;
	private Environnement environnement;
	private static Coordonnee coordonnee;
	private int rayon;
	
	/**
	 * Constructeurs
	 */
	public Map(int taille){
		grille = new Case[taille][taille];
		for(int j=0; j <taille; j++){
			for(int k = 0; k<taille; k++){
				grille[j][k] = new Case();
				System.out.print(" | ");
			}
			System.out.print(" | ");
			 System.out.println();
		}
	}
	
	/**
	 * Permet de lire le XML et de créer la map 
	 * 
	 */
	public static void lireXML(String fichier) throws Exception{
		
		SAXBuilder sxb = new SAXBuilder();
		document = sxb.build(new File(fichier));
		racine = document.getRootElement();
		
		String tailleXML = racine.getChildText("taille");
		taille = Integer.parseInt(tailleXML);
		System.out.println("Taille de la map : " + taille);
		
		//TODO faire avec les cases ! faire correspondre la plaine par exemple sur une case ! 
		List<Element> listEnv = racine.getChildren("environnements");
		for(int i = 0 ; i<listEnv.size(); i++){
			Element e = (Element)listEnv.get(i);
			
			String nomEnv = e.getChild("environnement").getAttributeValue("name");
			System.out.println("Nom: " + nomEnv);
			 
			/*Récupération des coordonnées des environnements sur la grille*/
			int coordX_D = Integer.parseInt(e.getChild("environnement").getAttributeValue("coordX_debut"));
			int coordY_D = Integer.parseInt(e.getChild("environnement").getAttributeValue("coordY_debut"));
			int coordX_F = Integer.parseInt(e.getChild("environnement").getAttributeValue("coordX_fin"));
			int coordY_F = Integer.parseInt(e.getChild("environnement").getAttributeValue("coordY_fin"));
			
			/*Affichage des coordonnées des environnements*/
			System.out.println("Coordonnée X debut: " + coordX_D);
			System.out.println("Coordonnée Y début: " + coordY_D);
			System.out.println("Coordonnée X fin: " + coordX_F);
			System.out.println("Coordonnée Y fin: " + coordY_F);
			
			/* */
			for(int j=coordX_D ;j<=coordX_F ; j++){
				for(int k=coordY_D ; k <=coordY_F; k++){
					System.out.println("coor : " + j + " " + k);
					if(nomEnv == "eau"){
						grille[j][k] = new Case (new Coordonnee(j,k),EnumEnvironnement.eau, true);
					}else if (nomEnv == "plaine"){
						grille[j][k] = new Case (new Coordonnee(j,k),EnumEnvironnement.plaine, true);
					}else if(nomEnv == "montagne"){
						grille[j][k] = new Case (new Coordonnee(j,k),EnumEnvironnement.montagne, true);
					}
				}
			}
		}
		
		
		
		
		
	
		
		List<Entite> listeEntites = new ArrayList<Entite>();
		
		/*On crée une List contenant tous les noeuds "renard" de l'Element racine */
		List listeRenards = racine.getChildren("renard");
		Iterator<Element> j = listeRenards.iterator();
		while(j.hasNext()){
			Element courant = j.next();
			Renard r = new Renard();
			listeEntites.add(r);
			int id = courant.getAttribute("id").getIntValue();
			/*r.setId(id);
			
			r.setX(courant.getAttribute("coordX").getIntValue());
			r.setY(courant.getAttribute("coordY").getIntValue());
			
			r.setFaim(courant.getChild("faim").getText());
			*/

		}
	
		//Affichage
		System.out.println("Liste des entités : ");
		for(Entite elem: listeEntites){
			System.out.println(elem);
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
	public Case[][] getGrille() {
		return grille;
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

