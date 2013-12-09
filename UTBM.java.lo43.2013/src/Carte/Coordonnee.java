package Carte;

public class Coordonnee {

	private int x;

	private int y;
	
	public void setCoordonnee(int x,int y,int z){
		this.x = x;
		this.y = y;
	}
	
	public Coordonnee getCoordonnee(){
		return this;
	}
	
	//Distance entre deux point (x^2+y^2)^(1/2)
	public double distance(Coordonnee nouveauPosition){
		return Math.sqrt(
							Math.pow((double)(nouveauPosition.x-this.x),2)+
							Math.pow((double)(nouveauPosition.y-this.y),2)
						);
	}

}
