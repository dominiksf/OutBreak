package StrategyPattern;

import Controller.GameBoard;
import Model.Block;
import Model.Hospital;
import Model.House;
import Model.Person;

import java.util.List;

public class Context {
   static double x = 0.05;
   static double y = 0.2;
   public static Block selectCreation(List<Person> persons, GameBoard gameBoard) {
       if(x < 0.65){
           x += 0.15;
       } else {
           y += 0.15;
           x = 0.2;
       }
        if (Policy.isInfectionLimitReached(persons)) {
            return new Hospital(new double[]{x,y}, gameBoard);
       } else {
          return new House(gameBoard, new double[]{x,y});
       }
    }
}
