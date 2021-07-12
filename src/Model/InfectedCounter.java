package Model;

import java.util.List;

public class InfectedCounter {

	private List<Person> people;

	public int calc() {
		long sum;
		sum = people.stream().filter(Person::isInfected).count();
		return (int) sum;
	}

	public InfectedCounter(List<Person> people) {
		this.people = people;
	}
}
