package Controller;

import Model.Block;
import Model.GameOutcome;
import Model.Paddle;
import Model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class GameBoard {

	public static List<Person> people;
	public List<Block> blocks;
	public Paddle paddle;
	public GameOutcome gameOutcome;
	public Keyboard keyboard;
	public AudioPlayer audioPlayer;

	public static final int NUMBER_OF_PERSONS = 10;

	//public AudioPlayer audioPlayer = new AudioPlayer();

	private Timer gameTimer;
	// The update period of the game in ms, this gives us 25 fps.
	private static final int UPDATE_PERIOD = 1000 / 25;

	// initial Position of the paddle - GameBoardUI ?
	private final double initalPosX = 0.5;
	private final double initialPosY = 0.9;

	public void startGame() {
		//initInputListeners();
		generatePeople(NUMBER_OF_PERSONS);
		//generatePaddle();
		audioPlayer.playBackgroundMusic();
	}

	public void setAudioPlayer(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	/*
	private void initInputListeners() {
		this.mouse = new Mouse(paddle, this.gameBoardUI);
		this.keyboard = new Keyboard(paddle, this.gameBoardUI);
	}
	*/

	private void generatePeople(int n) {
	     for(int i = 0; i < n; i++) {
	     	boolean infected = Math.random() < 0.5;
	     	people.add(new Person(infected, Math.random(), Math.random(), (new Random()).nextInt(4) * 90 + 45,this));
		 }
	}

	public void generatePaddle() {
		paddle = new Paddle(initalPosX);
		System.out.println();
	}

	public GameBoard(){
		generatePaddle();
		people = new ArrayList<>();
		keyboard = new Keyboard(paddle);
		audioPlayer = new AudioPlayer();
		startGame();
		/*
		new Thread(() -> {
			while (true) {
				if (true) {
					generatePeople(1);
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		 */
	}

	public Keyboard getKeyboard() {
		return keyboard;
	}

	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}
}
