package View;

import Controller.GameBoard;
import Model.Block;
import Model.Paddle;
import Model.Person;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
		paddle = gameBoard.paddle;
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
		synchronized (blocks){
			for (Block block:blocks) {
				g.setColor(block.getColor());
				g.fillRect((int) (block.position[0] * getWIDTH()), (int) (block.position[1] * getHEIGHT()), (int) (Block.dimensions[0] * getWIDTH()), (int) (Block.dimensions[1] * getHEIGHT()));
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




