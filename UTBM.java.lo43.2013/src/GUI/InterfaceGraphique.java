package GUI;

import javax.swing.JFrame;

import Vivarium.Main;

public class InterfaceGraphique implements Vue, Controle {

	private JFrame fenetre;

	
	@Override
	public void fenetre() {

	this.fenetre = new JFrame();
    this.fenetre.setTitle(Main.NAME);
    this.fenetre.setSize(600, 600);
    this.fenetre.setLocationRelativeTo(null);
    this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.fenetre.setResizable(false);

   }

	 
	@Override
	public void menu() {
    // TODO Auto-generated method stub
 
  }


	/**
	 * @see Vue#dessineMap()
	 * 
	 *  
	 */
	public void dessineMap() {

	}


	/**
	 * @see Controle#getChoix()
	 * 
	 *  
	 */
	public void getChoix() {

	}

}
