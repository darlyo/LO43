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

public class Map {
	private static Document document;
	private static Element racine;
	private static int taille;
	private Case [][] grilleDeJeu;
	private Nourriture nourriture;
	private Environnement environnement;
	private static Coordonnee coordonnee;
	private int rayon;
	
	/**
	 * Constructeurs
	 */
	public Map(){
		
	}
	
	/**
	 * Permet de lire le XML et de créer la map 
	 * 
	 */
	public static void lireXML(String fichier) throws Exception{
		
		
		SAXBuilder sxb = new SAXBuilder();
		document = sxb.build(new File(fichier));
		racine = document.getRootElement();
		
		String tailleXML = racine.getChild("taille").getText();
		//TODO transformer tailleXML en int et le mettre dans taille
		
		
		//Récupérer une liste d'environnements sous l'Element racine
		List listeEnvironnementsLus = racine.getChildren("environnement");
		List<Environnement> listeEnvironnementsCrees = new ArrayList<Environnement>();
		Iterator<Element> i = listeEnvironnementsLus.iterator();
		/*while(i.hasNext()){
			Element e = i.next();
			Environnement env = new Environnement();
			listeEnvironnementsCrees.add(env);
			
			//TODO faire avec les cases ! faire correspondre la plaine par exemple sur une case ! 
			env.setName(e.getAttribute("name").getName());
			
			env.setCoordXDebut(e.getAttribute("coordX_debut").getIntValue());
			env.setCoordXFin(e.getAttribute("coordX_fin").getIntValue());
			env.setCoordYDebut(e.getAttribute("coordY_debut").getIntValue());
			env.setCoordYFin(e.getAttribute("coordY_fin").getIntValue());
		}*/
		
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
			//TODO à trnasformer en int
			
			
			
		}
	
		//Affichage
		System.out.println("Liste des entités : ");
		for(Entite elem: listeEntites){
			System.out.println(elem);
		}
		System.out.println("Liste des entités : ");
		for(Environnement elem: listeEnvironnementsCrees){
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
			
		try {
			lireXML("src/Carte/Map.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<Entite> perception(Coordonnee co, int rayon) {
		List<Entite> listeEntite = new ArrayList<Entite>();
		
		return listeEntite;
	}

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

}

