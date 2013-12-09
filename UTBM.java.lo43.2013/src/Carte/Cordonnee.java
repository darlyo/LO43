package Carte;

public class Cordonnee {

	private int x;

	private int y;

	private int z;
	
	public void setCordonnee(int x,int y,int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Cordonnee getCordonnee(){
		return this;
	}
	
	//Distance entre deux point (x^2+y^2+z^2)^(1/2)
	public double distance(Cordonnee nouveauPosition){
		return Math.sqrt(
							Math.pow((double)(nouveauPosition.x-this.x),2)+
							Math.pow((double)(nouveauPosition.y-this.y),2)+
							Math.pow((double)(nouveauPosition.z-this.z),2)
						);
	}

}
