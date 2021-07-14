package Model;

import java.util.List;

public class InfectedCounter {

	private List<Person> people;
	private House house;

	public int calc() {
		long sum;
		sum = people.stream().filter(Person::isInfected).count();
		if(house.infected)
			sum = sum + house.getInhabitantsCounter();
		return (int) sum;
	}

	public InfectedCounter(List<Person> people,House house) {
		this.people = people;
		this.house = house;
	}
}
