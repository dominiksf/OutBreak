package Model;

import View.GameBoardUI;

public class Paddle implements Collidable {

	/**
	 * All Paddle parameters are relative (between 0 and 1)
	 */
	private double length = 0.2;
	private double height = 0.01;
	private static final double DEFAULT_LENGTH = 0.2;
	private static final double DEFAULT_HEIGHT = 0.01;
	private static final double PADDLE_STEP_LENGTH = 0.05;

	/**
	 * XPosition is the position of the paddle's middle
	 */
	private double xPosition = 0.5;

	/**
	 * Getters
	 */
	public double getLength() {
		return length;
	}

	public double getHeight() {
		return height;
	}

	public double getXPosition() {
		return xPosition;
	}

	/**
	 * Constructor
	 * 
	 * @param xPosition initial Paddle position in relative declaration (between 0
	 *                  and 1)
	 * @param length    relative paddle length (between 0 and 1, where 1 matches the
	 *                  width of the gameboard)
	 * @param height    relative paddle height (between 0 and 1, where 1 matches the
	 *                  height of the gameboard)
	 */
	public Paddle(double xPosition, double length, double height) {
		this.xPosition = xPosition;
		// this.length = length;
		// this.height = height;
	}

	/**
	 * Constructor with default values
	 * 
	 * @param xPosition initial Paddle position in relative declaration (between 0
	 *                  and 1)
	 */
	public Paddle(double xPosition) {
		new Paddle(xPosition, DEFAULT_LENGTH, DEFAULT_HEIGHT);
	}

	public void moveLeft() {
		setPosition(xPosition - PADDLE_STEP_LENGTH);
	}

	public void moveRight() {
		setPosition(xPosition + PADDLE_STEP_LENGTH);
	}

	/**
	 * Sets the paddle's position and makes sure that the paddle cannot be placed
	 * outside the boundaries
	 * 
	 * @param x
	 */
	private void setPosition(double x) {
		if (x > 1 - length / 2)
			xPosition = 1 - length / 2;
		else if (x < length / 2)
			xPosition = length / 2;
		else
			xPosition = x;
	}

	// Für Maus-Steuerung
	public void move(double xPosition) {
		this.xPosition = xPosition;
	}

	@Override
	public double[] getDimensions() {
		return new double[] { length, height };
	}

	@Override
	public double[] getPosition() {
		return new double[] { xPosition - 0.1, (double)(GameBoardUI.getHEIGHT() - 100) / GameBoardUI.getHEIGHT() };
	}

	@Override
	public void collide(Person person, int surfaceDirection) {
		person.collide(surfaceDirection);
		/*
		 * 
		 * // hier: Unterscheidung zwischen Aufprall der Person in der Mitte des Paddles
		 * oder nicht
		 * 
		 * // Mitte des Feldes richtig?? if(person.getPosition()[0] >
		 * this.getPosition()[0] + (this.length / 3) || person.getPosition()[0] <
		 * this.getPosition()[0] + (this.length / 3) * 2) {
		 * 
		 * // bisher kein Setter vorhanden // person.setDirection(0);
		 * 
		 * } else { person.collide(surfaceDirection); }
		 * 
		 * // alte Funktionalität person.collide(surfaceDirection);
		 * 
		 */
	}
}
