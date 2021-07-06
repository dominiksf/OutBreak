package Model;

import java.util.List;

public class InfectedCounter {
	
	public int infected;

public void calc(List<Person> people) {
		long sum;
		sum = people.stream().filter(Person::isInfected).count();
	}

}
