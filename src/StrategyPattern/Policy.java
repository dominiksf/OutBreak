package StrategyPattern;

import Model.Person;

import java.util.List;

public class Policy {
    
public static final int INFECTION_LIMIT = 0;


   public static boolean isInfectionLimitReached(List<Person> persons) {
       int currentlyInfected = 0;
       for (Person person : persons) {
          if (person.isInfected()) {
             currentlyInfected++;
          }
        }
    
        if(currentlyInfected > INFECTION_LIMIT) {
          return true;
        } else {
          return false;
        }
    }
}
