package GUI;

import java.util.List;

import Carte.Map;
import Entite.Entite;
import Vivarium.Partie;

public interface Vue {

	/**
	 * D�ssine l'interface graphique de l'appliacation - les menus -
	 * l'emplacementde la map
	 * 
	 * La fenetre vas �tre fixe, c'est la map qui s'addaptera a notre fenetre
	 * soit par agrandissement soit par r�duction
	 * 
	 * Les menus ont une structure fixe mais un contenu dynamique d�finit par
	 * les classes et les enumerations
	 */
	public abstract void fenetre(Partie partie);

	public abstract void dessineMap(Map map);
	
	/**
	 * dessine les entit�es
	 */
	public abstract void dessineEntite(List<Entite> listEntite );

}
