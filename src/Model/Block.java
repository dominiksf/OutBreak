package Model;

import java.util.Vector;

public abstract class Block implements Collidable{

	// mögliches Pattern => StrategyPattern
	// Je nach dem, auf welche Blockart die Person trifft, wird unterschiedliche Strategie ausgeführt
	// Strategie bei Hospital: Person aufnehmen und nach bestimmter Zeit wieder freilassen
	// Strategie bei House: Bewohner infizieren ?

	public Vector position;
	public int inhabitants;

	
	public void addPerson() {
		
	}

	@Override
	public double[] getDimensions() {
		return new double[0];
	}

	@Override
	public double[] getPosition() {
		return new double[0];
	}

	@Override
	public void collide(Person person, int surfaceDirection) {

	}
}
