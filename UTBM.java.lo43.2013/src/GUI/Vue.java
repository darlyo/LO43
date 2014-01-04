package GUI;

import java.util.List;

import Carte.Map;
import Entite.Entite;
import Vivarium.Partie;

public interface Vue {

	/**
	 * Déssine l'interface graphique de l'appliacation - les menus -
	 * l'emplacementde la map
	 * 
	 * La fenetre vas être fixe, c'est la map qui s'addaptera a notre fenetre
	 * soit par agrandissement soit par réduction
	 * 
	 * Les menus ont une structure fixe mais un contenu dynamique définit par
	 * les classes et les enumerations
	 */
	public abstract void fenetre(Partie partie);

	public abstract void dessineMap(Map map);
	
	/**
	 * dessine les entitées
	 */
	public abstract void dessineEntite(List<Entite> listEntite );

}
