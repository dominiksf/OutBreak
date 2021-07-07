package Model;

import Controller.GameBoard;

import java.awt.*;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Hospital extends Block{
	
	public List<Long> entryTimes;

	private final GameBoard gameBoard;

	public final static Color color = Color.MAGENTA;

	private final long stayingTime = 4000000000L;


	public void release() {
		entryTimes.remove(0);
		Person person = new Person(false, position[0] + 0.1, position[1] + 0.1 + Person.getR(), (new Random()).nextInt(4)* 90 + 45, gameBoard);
		GameBoard.people.add(person);
	}

	@Override
	public void collide(Person person, int surfaceDirection) {
		if(!person.isInfected()){
			person.collide(surfaceDirection);
		} else {
			entryTimes.add(System.nanoTime());
			GameBoard.people.remove(person);
		}
	}

	public Hospital(double[] position, GameBoard gameBoard) {
		super(position);
		this.gameBoard = gameBoard;
		new Thread(() -> {
			while (GameBoard.gameOutcome == GameOutcome.RUNNING){
				for(long l:entryTimes){
					if(System.nanoTime() - l > stayingTime){
						release();
					}
				}
				try {
					sleep(GameBoard.UPDATE_PERIOD);
				} catch (InterruptedException ignored) {
				}
			}
		});
	}

	@Override
	public Color getColor() {
		return color;
	}
}
