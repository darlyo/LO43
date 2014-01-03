package Enumeration;


import java.awt.Color;

import GUI.Couleur;

public enum EnumEntite {

	Chamois (Couleur.yellow), 
	Lapin (Couleur.cyan), 
	Loup (Couleur.red), 
	Mouton (Couleur.white), 
	Renard (Couleur.orange), 
	Sanglier (Couleur.GRAY), 
	Arbre (Couleur.marron), 
	Herbe (Couleur.violet);

	private Color color;
	
	EnumEntite(Color color)
	{
		this.color = (Color) color;
	}
	
	public Color getColor()
	{
		return color;
	}
}

