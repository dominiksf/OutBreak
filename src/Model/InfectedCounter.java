package Model;

import java.util.List;

public class InfectedCounter {

	private List<Person> people;
	private List<Block> blocks;

	public int calc() {
		long sum;
		sum = people.stream().filter(Person::isInfected).count();
		synchronized (Person.monitor){
			sum += blocks.stream().filter(block -> block.getClass().equals(House.class))
					.filter(block -> ((House)block).infected)
					.mapToInt(blocks -> ((House)blocks).inhabitantsCounter)
					.sum();
		}
		return (int) sum;
	}

	public InfectedCounter(List<Person> people, List<Block> blocks) {
		this.people = people;
		this.blocks = blocks;
	}
}
