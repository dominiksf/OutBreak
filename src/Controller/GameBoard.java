package Controller;

import Model.*;
import StrategyPattern.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class GameBoard {

	public static List<Person> people;
	public List<Block> blocks;
	public Paddle paddle;
	public static GameOutcome gameOutcome = GameOutcome.RUNNING;
	public Keyboard keyboard;
	public AudioPlayer audioPlayer;
	private Thread renewer;
	public InfectedCounter infectedCounter;

	public static final int NUMBER_OF_PERSONS = 1;

	//public AudioPlayer audioPlayer = new AudioPlayer();
	public static final int UPDATE_PERIOD = 1000 / 60;

	// initial Position of the paddle - GameBoardUI ?
	private final double initalPosX = 0.5;
	private final double initialPosY = 0.9;

	public void startGame() {
		generatePeople(NUMBER_OF_PERSONS);
		audioPlayer.playBackgroundMusic();
		renewer = new Thread(() -> {
			while (blocks.size() < 12){
				blocks.add(Context.selectCreation(people,this));
				try {
					sleep(10000);
				} catch (InterruptedException ignored) {
				}
			}
		});
		renewer.start();
		Thread generator = new Thread(() -> {
			while (gameOutcome == GameOutcome.RUNNING){
				if(people.size() == 0){
					generatePeople(1);
				}
				try {
					sleep(UPDATE_PERIOD);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		generator.start();
	}

	public void generatePeople(int n) {
	     for(int i = 0; i < n; i++) {
	     	boolean infected = Math.random() < 0.5;
			 people.add(new Person(infected, 0.1 + Math.random()*8/10,0.7, (new Random()).nextInt(4) * 90 + 45,this));
		 }
	}

	public void deletePerson(Person person){
		person.endThread();
		people.remove(person);
	}

	public GameBoard(){
		paddle = new Paddle(initalPosX);
		people = new ArrayList<>();
		blocks = new ArrayList<>();
		keyboard = new Keyboard(paddle);
		audioPlayer = new AudioPlayer();
		infectedCounter = new InfectedCounter(people);
		startGame();
	}

	public Keyboard getKeyboard() {
		return keyboard;
	}
}
