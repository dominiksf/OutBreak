package View;

import java.awt.*;
import java.util.List;

import Controller.AudioPlayer;
import Model.Block;
import Model.Collidable;
import Controller.GameBoard;
import Model.Paddle;
import Model.Person;

import javax.swing.*;

public class GameBoardUI extends JPanel {

	/**
	 *  UI Colors
	 */
	private static final Color BACKGROUND_COLOR = Color.WHITE;
	private static final Color PERSON_COLOR_HEALTHY = Color.GREEN;
	private static final Color PERSON_COLOR_INFECTED = Color.RED;
	private static final Color PADDLE_COLOR = Color.BLACK;
	/**
	 * UI sizes (in pixels)
	 */
	private static final double PERSON_SIZE = 0.015625;
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	/**
	 * Attributes
	 */
	private GameBoard gameBoard;
	private Paddle paddle;
	private List<Person> people;
	private List<Block> blocks;
	private JFrame jFrame;

	/**
	 * Constructor
	 */
	public GameBoardUI(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
		gameBoard.generatePaddle();
		paddle = new Paddle(0.5);
		people = gameBoard.people;
		blocks = gameBoard.blocks;
		setBackground(BACKGROUND_COLOR);
		setSize(new Dimension(WIDTH,HEIGHT));
		jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Programm endet, wenn Fenster geschlossen wird
		jFrame.setSize(WIDTH, HEIGHT);
		jFrame.setLocationRelativeTo(null);
		jFrame.setTitle("OUTBREAK - THE GAME"); //Name des Fensters
		jFrame.add(this);
		jFrame.setVisible(true);
		gameBoard.getKeyboard().setPaddle(paddle);
		gameBoard.setPaddle(paddle);
		jFrame.addKeyListener(gameBoard.getKeyboard());
		Thread repainter = new Thread(() -> {
			while (true) {
				repaint();
				try {
					Thread.sleep(1000 / 60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		repainter.start();
	}

	/**
	 * Draws everything (people, blocks and the paddle) on the GameBoard.
	 * Does not update automatically, so it has to be called again in the desired update interval.
	 */
	public void paintComponent(Graphics g){ //geerbte Methode aus JPanel
		super.paintComponent(g); //ruft geerbte Methode auf
		g.setColor(Color.BLACK);
		Rectangle rectangle = new Rectangle((int)(WIDTH*paddle.getXPosition()),HEIGHT-(int)(HEIGHT*paddle.getHeight()*5),(int)(WIDTH*paddle.getLength()),(int)(HEIGHT*paddle.getHeight()));
		double d = WIDTH*paddle.getXPosition();
		int j = (int)(WIDTH*paddle.getXPosition());
		double x = paddle.getXPosition();
		g.fillRect((int)(WIDTH*paddle.getXPosition() - paddle.getLength()*WIDTH*0.5),HEIGHT-100,(int)(WIDTH*paddle.getLength()),(int)(HEIGHT*paddle.getHeight()));
		synchronized (people) {
			for (Person person : people) {
				if (person.isInfected()) {
					g.setColor(PERSON_COLOR_INFECTED);
				} else {
					g.setColor(PERSON_COLOR_HEALTHY);
				}
				g.fillOval((int) (person.getxPosition() * WIDTH), (int) (person.getyPosition() * HEIGHT), (int)(PERSON_SIZE * WIDTH), (int)(PERSON_SIZE * WIDTH));
			}
		}
	}

	public static void main(String[] args){
		GameBoard gameBoard = new GameBoard();
		new GameBoardUI(gameBoard);
	}

	static public int getWIDTH() {
		return WIDTH;
	}

	static public int getHEIGHT() {
		return HEIGHT;
	}
}




