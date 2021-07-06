package Model;

import Controller.GameBoard;

public class House extends Block implements HouseInterface {



	public boolean inhabitated;

	public void releaseAll() {

	}

	public boolean hasVacancy() {
		return true;

	}

	public void collide(Person person) {

		if (hasVacancy()) {
			GameBoard.people.remove(person);
		} else {
			Collidable.detectRectCollision(this, person);
		}

	}
}
