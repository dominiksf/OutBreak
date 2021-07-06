package Model;

import Controller.GameBoard;
import View.GameBoardUI;

public interface Collidable {

    double[] getDimensions();

    double[] getPosition();

    /**
     * Changes a person's direction during a collision
     *
     * @param person           Person that collides with an obstacle (e.g. Block, Paddle or gameboard wall)
     * @param surfaceDirection the direction of the obstacle's surface
     */
    void collide(Person person, int surfaceDirection);


    /**
     * Detects collisions for every Person on the gameboard and changes their directions accordingly
     */
    static boolean detectRectCollision(Collidable collider, Person person) { //Statische Methode, kann überall aufgerufen werden, testet kollision zwischen Person und Collidable
        double test = person.getyPosition() - Person.getR();
        double test2 = person.getyPosition() + Person.getR();
        if (isInRange(collider.getPosition()[0],
                collider.getPosition()[0] + collider.getDimensions()[0],
                person.getxPosition() - Person.getR(),person.getxPosition() + Person.getR()) &&
                isInRange(collider.getPosition()[1]/ GameBoardUI.getHEIGHT(),
                        collider.getPosition()[1]/ GameBoardUI.getHEIGHT() - collider.getDimensions()[1],
                        test,test2)) { //testet ob kollision vorliegt
            if(straitCollision(collider,person)){
                return true;
            }
            /**
             * rechnet aus welche ecke des rechtecks relevant für die berchnug ist
             * sollte die person von links unten kommen (d = 45) dann ist nur die untere linke ecke relevant
             * um zu berechnen ob die person nach unten under nach rechts abgelenkt wird
             */
            int o = 0;
            int q = 0;
            for (int i = 0; i < 4; i++) {
                if (person.getDirection() > 90 * i && person.getDirection() < 90 * (i + 1)) {
                    switch (i) {
                        case 1 -> {
                            o = 1;
                        }
                        case 2 -> {
                            o = 1;
                            q = 1;
                        }
                        case 3 -> {
                            q = 1;
                        }
                    }
                    collider.collide(person, calcDirection(new double[]{collider.getPosition()[0] + collider.getDimensions()[0] * q, collider.getPosition()[1]/GameBoardUI.getHEIGHT() + collider.getDimensions()[1] * o}, person, i));
                    return true;
                }
            }
        }
        return false;
    }

    static boolean detectSideCollision(Person person) {
        int d = 1; //sagt ob der radius addiert oder subtrahiert werden muss
        int s = 1; // besagt ob x (0) oder y (1) koordinate für die berechnung relevant ist
        int p = 0;
        for (int i = 0; i < 4; i++) { // rechte wand = 0 dann gegen den Uhrzeigersinn
            s -= d; // d sorgt dafür das abwechselnd zwischen 0 und 1 iteriet wird
            d = -d; // iteriert zwischen -1 und 1
            switch (i){
                case 0, 3 -> p = -1;
                case 1, 2 -> p = 1;
            }

            if (
                    person.getPosition()[s] + p * Person.getR() < 0
                            || person.getPosition()[s] + p * Person.getR() > 1
            ) { //kollision liegt vor
                if(straitCollision(new Collidable() {
                    @Override
                    public double[] getDimensions() {
                        return new double[0];
                    }

                    @Override
                    public double[] getPosition() {
                        return new double[0];
                    }

                    @Override
                    public void collide(Person person, int surfaceDirection) {
                        person.collide(surfaceDirection);
                    }
                }, person)){
                    return true;
                }
                person.collide(Math.abs(270 - 90*i));
                return true;
            }

        }
        return false;
    }

    static boolean straitCollision(Collidable collider, Person person){
        switch ((int) person.getDirection()) { // bei eindimensionaler bewegung klar
            case 0, 180 -> {
                collider.collide(person, 90); //direction legt richtung des Vectors fest an dem die personen richtung gespiegelt werden muss
                return true;
            }
            case 90, 270 -> {
                collider.collide(person, 180);
                return true;
            }
        }
        return false;
    }

    static boolean isInRange(double a, double b, double... reference) { // testet ob reference zwischen a und b liegt
        if (a > b) {
            double tempA = a;
            a = b;
            b = tempA;
        }
        for(double i:reference) {
            if(a <= i && b >= i){
                return true;
            }
        }
        return false;
    }

    private static int calcDirection(double[] point, Person person, int d) {  // berechnet den genauen Richtungsvector für den relevanten punkt und der richtung
        int w = 1;
        int h = 1;
        switch (d) { // determienieren um welchen punkt es sich handelt
            case 3 -> {
                w = -1;
            }
            case 2 -> {
                w = -1;
                h = -1;
            }
            case 1 -> {
                h = -1;
            }
        }
        if (
                Math.abs(person.getxPosition() + w * Person.getR() - point[0])
                        > Math.abs(person.getyPosition() + h * Person.getR() - point[1])
        ) { // berchnet welcher der beiden koordinaten +- r näher am relevanten punkt liegt und gibt die entsprechnde richtung zurück
            if (d == 0 || d == 3) {
                return 180;
            }
            return 0;
        }
        if (d <= 1) {
            return 270;
        }
        return 90;
    }
    
    public static void collide(Person person, boolean hasVacancy, Block house) {
        
        if (hasVacancy) {
			GameBoard.people.remove(person);
		} else {
			Collidable.detectRectCollision(house, person);
		}
        
    }

}
