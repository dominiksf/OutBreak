package StrategyPattern;

import java.util.List;
import Model.Person;

public class Policy {
    
public static final int INFECTION_LIMIT = 100;


   public static boolean isInfectionLimitReached(List<Person> persons) {
       int currentlyInfected = 0;
       for (Person person : persons) {
          if (person.isInfected()) {
             currentlyInfected++;
          }
        }
    
        if (currentlyInfected > INFECTION_LIMIT) {
          return true;
        } else {
          return false;
        }
    }
}
