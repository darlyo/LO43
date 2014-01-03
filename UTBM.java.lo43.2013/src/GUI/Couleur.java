package GUI;

import java.awt.Color;

@SuppressWarnings("serial")
public class Couleur extends Color{

	public final static Color marron = new Color(128,0,0);
	public final static Color violet = new Color(128,0,128);
	public final static Color grisClair = new Color(Integer.parseInt("d2d2d2", 16));
	public final static Color grisClaire2 = new Color(Integer.parseInt("eeeeee", 16));
	
	
	public Couleur(int rgb) {
		super(rgb);
	}

}
