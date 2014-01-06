package Vivarium;

import Carte.Map;

public class Main {

	public static final String NAME = "Small World";

	public static void main(String[] args) {

		Map map = new Map();
		Partie partie = new Partie(map);
		partie.run();
	}

}
