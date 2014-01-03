package Carte;

public class Coordonnee {

	private int x;
	private int y;

public Coordonnee() {
	this.x = 0;
	this.y = 0;
}
	public Coordonnee(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Coordonnee(Coordonnee coordonnee){
		this.x = coordonnee.getX();
		this.y = coordonnee.getY();
	}
	
	/**
	 * Getters et setters
	 */
	public void setCoordonnee(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordonnee getCoordonnee() {
		return this;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return this.x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return this.y;
	}

	/**
	 * Distance entre deux point (x^2+y^2)^(1/2)
	 */
	public double distance(Coordonnee nouveauPosition){
		return Math.sqrt(
							Math.pow((double)(nouveauPosition.x-this.x),2)+
							Math.pow((double)(nouveauPosition.y-this.y),2)
						);
	}

}
