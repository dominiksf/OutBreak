package Model;

import Controller.GameBoard;

public class Person {
	/**
	 * All positions are relative (between 0 and 1)
	 */
	private boolean infected;
	private double xPosition;
	private double yPosition;
	private double stepLength = 0.01;
	private final static double rad = 0.015625;
	private GameBoard gameBoard;
	/**
	 * The unit of direction is degree
	 * 0 degree = 360 degree = towards the top
	 * 90 degree = towards the right
	 * 180 degree = towards the bottom
	 * 270 degree = towards the left
	 */
	private double direction = 0;

	/**
	 * Constructors
	 */
	public Person(boolean infected, double xPosition, double yPosition, double direction, double stepLength, GameBoard gameBoard) {
		new Person (infected, xPosition, yPosition, direction, gameBoard);
		this.stepLength = stepLength;
	}
	public Person(boolean infected, double xPosition, double yPosition, double direction,  GameBoard gameBoard) {
		this.infected = infected;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.direction = direction % 360;
		this.gameBoard = gameBoard;
		new Thread(() -> {
			while (true) {
				makeStep();
				Collidable.dectectSideCollision(this);
				Collidable.dectectRectCollision(gameBoard.paddle,this);
				try {
					Thread.sleep(1000 / 60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * Getters
	 */
	public boolean isInfected() {
		return infected;
	}
	public double getxPosition() {
		return xPosition;
	}
	public double getyPosition() {
		return yPosition;
	}
	public double[] getPosition(){
		return new double[]{xPosition,yPosition};
	}
	public double getStepLength() {
		return stepLength;
	}
	public void setStepLength(double stepLength) {
		this.stepLength = stepLength;
	}
	public double getDirection() {
		return direction;
	}

	public static double getR() {
		return rad;
	}

	/**
	 * Setters
	 */
	public void setInfected(boolean infected) {
		this.infected = infected;
		if (infected) gameBoard.audioPlayer.makeInfectionSound();
	}
	/*
	public void setDirection(double direction) {
		this.direction = direction;
	}
	 */

	/**
	 * Lets the person move one step forward
	 */
	public void makeStep() {
		xPosition += stepLength * Math.sin(direction / 360 * 2 * Math.PI);
		yPosition += stepLength * Math.cos(direction / 360 * 2 * Math.PI);
	}

	/**
	 * Turns the direction of the Person depending on its current direction when it hits a surface
	 * surfaceDirection is the direction of the surface that the person collides with
	 *                         Unit: degree
	 * 							collision with top wall -> direction = 0
	 * 							collision with right wall -> direction = 90
	 *                  		collision with bottom wall -> direction = 180
	 *                  		collision with left wall -> direction = 270
	 */
	public void collide(double surfaceDirection) {
		gameBoard.audioPlayer.makeCollisionSound();
		direction = mod((direction + 2 * (direction - surfaceDirection)),360);
	}

	public static double mod(double subject,double modBy){
		while (subject < 0){
			subject += modBy;
		}
		return subject%modBy;
	}

}


