package Animaux.interfaces;

import Vegetaux.Vegetal;
import Animaux.Animal;

public interface Omnivorable {

	public abstract void manger(Animal animal);

	public abstract void manger(Vegetal vegetal);

}
