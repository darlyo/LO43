package Entite.Animaux.interfaces;

import Entite.Animaux.Animal;
import Entite.Vegetaux.Vegetal;

public interface Omnivorable {

	public abstract void manger(Animal animal);

	public abstract void manger(Vegetal vegetal);

}
