package GUI;
public interface Vue {

	public abstract void menu();

	public abstract void dessineMap();

	public abstract void fenetre();
	
	/**
	 *  dessine les entit�es
	 */
	
	public abstract void dessineEntite();
  

  /**
   * affiche un message 
   * @param msg : intitul� du message
   */

  void message(String msg);


}
