package Carte;
import javax.xml.parsers.*;

public class Map {
	//TODO Dom n'est pas reconnue, Il s'agit peut etre plutot de Doc
	private Dom doc;  
	private int taille;
	private int [][] grilleDeJeu;
	private Nourriture nourriture;

	/**
	 * Constructeurs
	 */
	public Map(){
		
	}
	
	/**
	 * Permet de lire le XML et de créer la map 
	 */
	public void lireXML() {
		/**
		 * Ouvrir un fichier XML avec DOM et vérifier les exceptions
		 */
		try{
			// création d'une fabrique de documents
			DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
			
			// création d'un constructeur de documents
			DocumentBuilder constructeur = fabrique.newDocumentBuilder();
			
			// lecture du contenu d'un fichier XML avec DOM
			File xml = new File("BBDcarte.xml");
			Document document = constructeur.parse(xml);
			
			//traitement du document
			Element racine = doc.getDocumentElement();
			system.out.println(racine);
			
			Element taille = racine.getElementsByTagName("taille");
			Element type = racine.getElementsByTagName("type");
			Element plaine = type.getElementsByTagName("plaine");
			
			
		}catch(ParserConfigurationException pce){
			System.out.println("Erreur de configuration du parseur DOM");
			System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
		}catch(SAXException se){
			System.out.println("Erreur lors du parsing du document");
			System.out.println("lors de l'appel à construteur.parse(xml)");
		}catch(IOException ioe){
			System.out.println("Erreur d'entrée/sortie");
			System.out.println("lors de l'appel à construteur.parse(xml)");
		}
	}

	
	/**
	 * Permet de sauvegarder une partie en écrivant dans un XML
	 */
	public void ecrireXML() {

	}

	public void perception(Coordonnee co, int rayon) {

	}
	public void main(String args[]){
		lireXML();
	}

}
