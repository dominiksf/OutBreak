package StrategyPattern;

import java.util.List;
import Model.Block;
import Model.Hospital;
import Model.House;
import Model.Person;

public class Context {
   public static Block selectCreation(List<Person> persons) {
       
        if (Policy.isInfectionLimitReached(persons)) {
          return new Hospital();
       } else {
          return new House();
       }
    }
}
