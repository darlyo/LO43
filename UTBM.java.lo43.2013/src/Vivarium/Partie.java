package Vivarium;

import Carte.Map;

public class Partie {

	private int tempsDeJeux;

	private int score;
	private Map map;

	private boolean start;
	
	public Partie(Map map) {
		this.map = map;
		this.start = false;
	}
	
	public void run()
	{
		this.start =true;
		while(start)
		{
			//on applique la methode live au entité
		}
	}
	
	public void stop()
	{
		this.start = false;
	}
	
	public void supprEntite() {

	}

	public void ajouteEntite() {

	}

}
