package Model;

import java.awt.*;

public abstract class Block implements Collidable{

	// StrategyPattern
	// Je nach dem, auf welche Blockart die Person trifft, wird eine unterschiedliche Strategie ausgeführt
	// Strategie bei Hospital: Person aufnehmen und nach bestimmter Zeit wieder freilassen
	// Strategie bei House: Bewohner infizieren ?

	public double[] position;
	public static final double[] dimensions = new double[]{0.1, 0.05};

    public abstract Color getColor();

	@Override
	public double[] getDimensions() {
		return dimensions;
	}

	@Override
	public double[] getPosition() {
		return position;
	}

	public Block(double[] position) {
		this.position = position;
	}
}
