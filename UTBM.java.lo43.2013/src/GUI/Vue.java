package GUI;

public interface Vue {

	public abstract void menu();

	public abstract void dessineMap();

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
	public abstract void fenetre();

	/**
	 * dessine les entitées
	 */
	public abstract void dessineEntite();

	/**
	 * affiche un message
	 * 
	 * @param msg
	 *            : intitulé du message
	 */
	void message(String msg);

}
