package GUI;
public interface Vue {

	public abstract void menu();

	public abstract void dessineMap();

	public abstract void fenetre();
	
	/**
	 *  dessine les entitées
	 */
	
	public abstract void dessineEntite();
  

  /**
   * affiche un message 
   * @param msg : intitulé du message
   */

  void message(String msg);


}
