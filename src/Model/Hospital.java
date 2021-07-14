package Model;

import Controller.GameBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Hospital extends Block{

	public List<Long> entryTimes;

	private final GameBoard gameBoard;

	public final static Color color = Color.MAGENTA;

	private final long stayingTime = 4000000000L;

	private final Object monitor = new Object();


	public void release() {
		synchronized (monitor) {
			entryTimes.remove(0);
		}
		gameBoard.generatePeople(1,false);
	}

	@Override
	public void collide(Person person, int surfaceDirection) {
		if(!person.isInfected()){
			person.collide(surfaceDirection);
		} else {
			synchronized (monitor) {
				entryTimes.add(System.nanoTime());
			}
			gameBoard.deletePerson(person);
		}
	}

	public Hospital(double[] position, GameBoard gameBoard) {
		super(position);
		this.gameBoard = gameBoard;
		entryTimes = new ArrayList<>();
		new Thread(() -> {
			while (GameBoard.gameOutcome == GameOutcome.RUNNING){
					for (int i = 0; i < entryTimes.size(); i++) {
						if (System.nanoTime() - entryTimes.get(i) > stayingTime) {
							release();
						}
				}
				try {
					sleep(GameBoard.UPDATE_PERIOD);
				} catch (InterruptedException ignored) {
				}
			}
		}).start();
	}

	@Override
	public Color getColor() {
		return color;
	}
}
