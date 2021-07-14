import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;

import Controller.GameBoard;
import Model.Block;
import Model.Collidable;
import Model.Hospital;
import Model.Person;

public class OutBreakMockTest {

	@TestSubject
	GameBoard gameBoard = new GameBoard();
	@Mock
	Block block;
	@Mock
	Block hospital;

	@Test
	void testCollisionDetectionWithBlock() {
		GameBoard gameBoard = new GameBoard();
		Block block = EasyMock.createMock(Block.class);
		Person person = new Person(true, 0.5 - Person.getR(), 0.6, 0, gameBoard);
		EasyMock.expect(block.getPosition()).andReturn(new double[] { 0.5, 0.5 });
		EasyMock.expect(block.getDimensions()).andReturn(new double[] { 0.2, 0.1 });
		EasyMock.expectLastCall().andAnswer(() -> {
			person.collide(90);
			return null;
		});
		EasyMock.replay(block);
		Collidable.detectRectCollision(block, person);
		assertEquals(180, person.getDirection());
	}

	@Test
	void testCollisionDetectionWithHospital() {
		GameBoard gameBoard = new GameBoard();
		Hospital hospital = EasyMock.createMock(Hospital.class);
		Person person = new Person(true, 0.5 - Person.getR(), 0.6, 0, gameBoard);
		EasyMock.expect(hospital.getPosition()).andReturn(new double[] { 0.5, 0.5 });
		EasyMock.expect(hospital.getDimensions()).andReturn(new double[] { 0.2, 0.1 });
		EasyMock.expectLastCall().andAnswer(() -> {
			person.setInfected(false);
			person.collide(90);
			return null;
		});
		EasyMock.replay(hospital);
		Collidable.detectRectCollision(hospital, person);
		assertFalse(person.isInfected());
		assertEquals(180, person.getDirection());
	}

	@Test
	void testMock() {
		GameBoard gameBoard1 = createMock(GameBoard.class);
		// System.out.println(gameBoard1.blocks);
	}

	/*
	 * @Test void testCollisionWithHouseWithVacancy() {
	 * 
	 * GameBoard gameBoard = new GameBoard(); House house = new House();
	 * 
	 * gameBoard.startGame();
	 * 
	 * int oldPeopleSize = GameBoard.people.size();
	 * 
	 * Person person = GameBoard.people.get(0);
	 * 
	 * expect(house.hasVacancy()).andReturn(true);
	 * 
	 * replay(house.hasVacancy());
	 * 
	 * Collidable.collide(person, house.hasVacancy());
	 * 
	 * assertNotEquals(oldPeopleSize, GameBoard.people.size());
	 * 
	 * }
	 * 
	 * @Test void testCollisionWithHouseWithNoVacancy() { GameBoard gameBoard = new
	 * GameBoard(); House house = new House();
	 * 
	 * gameBoard.startGame();
	 * 
	 * int oldPeopleSize = GameBoard.people.size();
	 * 
	 * Person person = GameBoard.people.get(0);
	 * 
	 * expect(house.hasVacancy()).andReturn(false);
	 * 
	 * replay(house.hasVacancy());
	 * 
	 * Collidable.collide(person, house.hasVacancy(), null);
	 * 
	 * assertEquals(oldPeopleSize, GameBoard.people.size());
	 * 
	 * }
	 */

}
