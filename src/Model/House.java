package Model;

import Controller.GameBoard;

import java.awt.*;

public class House extends Block implements HouseInterface {

    public int inhabitantsCounter;
    public int capacity;
    public boolean infected;
    public final static Color infectedColor = Color.RED;
    public final static Color nonInfectedColor = Color.GREEN;

    public GameBoard gameBoard;

    public House(GameBoard gameBoard, double[] position) {
        super(position);
        this.inhabitantsCounter = 0;
        this.capacity = 4;
        this.gameBoard = gameBoard;
        this.infected = false;
    }

    public void releaseAll() {

        if (this.inhabitantsCounter == 0) {
            return;
        }
		/*
        // Person an jeder Ecke des Blocks spawnen

        Person person1 = new Person(infected, position[0] + 0.1, position[1] + 0.1 + Person.getR(), (new Random()).nextInt(4) * 90 + 45, gameBoard); // links oben
        Person person2 = new Person(infected, position[0] + this.getDimensions()[0] + 0.1, position[1] + 0.1 + Person.getR(), (new Random()).nextInt(4) * 90 + 45, gameBoard); // rechts oben
        Person person3 = new Person(infected, position[0] + 0.1, position[1] + this.getDimensions()[1] + 0.1 + Person.getR(), (new Random()).nextInt(4) * 90 + 45, gameBoard); // links unten
        Person person4 = new Person(infected, position[0] + this.getDimensions()[0] + 0.1, position[1] + 0.1 + Person.getR(), (new Random()).nextInt(4) * 90 + 45, gameBoard); // rechts unten

        GameBoard.people.add(person1);
        GameBoard.people.add(person2);
        GameBoard.people.add(person3);
        GameBoard.people.add(person4);

		 */

        gameBoard.generatePeople(inhabitantsCounter, infected);
        inhabitantsCounter = 0;
        this.infected = false;

    }

    public boolean hasVacancy() {

        if (this.inhabitantsCounter < capacity) {
            return true;
        }

        return false;

    }

    public boolean isInhabitated() {

        if (this.inhabitantsCounter == 0) {
            return false;
        }

        return true;
    }

    public void addPerson(boolean isInfected) {

        this.inhabitantsCounter++;

        if (isInfected) {
            this.infected = true;
        }


    }


    // Kollisionsverhalten: Wenn noch Platz -> Person wird ins Haus aufgenommen / Wenn kein Platz mehr -> Person wird zurückgeschleudert und alle Personen im Haus rausgelassen
    @Override
    public void collide(Person person, int surfaceDirection) {

        person.collide(surfaceDirection);
        if (hasVacancy()) {
            gameBoard.deletePerson(person);
            this.addPerson(person.isInfected());
        } else {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.releaseAll();
        }

    }

    @Override
    public Color getColor() {
        if (infected) {
            return infectedColor;
        }
        return nonInfectedColor;
    }
}
