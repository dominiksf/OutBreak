package Model;

import Controller.GameBoard;

public interface HouseInterface {
    public boolean hasVacancy();
    public void collide(Person person);
}
