package GUI;

public interface Vue {

	public abstract void menu();

	public abstract void dessineMap();

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
	public abstract void fenetre();

	/**
	 * dessine les entit�es
	 */
	public abstract void dessineEntite();

	/**
	 * affiche un message
	 * 
	 * @param msg
	 *            : intitul� du message
	 */
	void message(String msg);

}
