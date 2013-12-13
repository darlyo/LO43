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
	 * Permet de lire le XML et de cr�er la map 
	 */
	public void lireXML() {
		/**
		 * Ouvrir un fichier XML avec DOM et v�rifier les exceptions
		 */
		try{
			// cr�ation d'une fabrique de documents
			DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
			
			// cr�ation d'un constructeur de documents
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
			System.out.println("lors de l'appel � fabrique.newDocumentBuilder();");
		}catch(SAXException se){
			System.out.println("Erreur lors du parsing du document");
			System.out.println("lors de l'appel � construteur.parse(xml)");
		}catch(IOException ioe){
			System.out.println("Erreur d'entr�e/sortie");
			System.out.println("lors de l'appel � construteur.parse(xml)");
		}
	}

	
	/**
	 * Permet de sauvegarder une partie en �crivant dans un XML
	 */
	public void ecrireXML() {

	}

	public void perception(Coordonnee co, int rayon) {

	}
	public void main(String args[]){
		lireXML();
	}

}
