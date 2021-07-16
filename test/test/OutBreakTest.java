package test;

import Controller.GameBoard;
import Model.*;
import StrategyPattern.Policy;
import View.GameBoardUI;
import org.easymock.EasyMock;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(EasyMockExtension.class)
class OutBreakTest {

	// Tests for Collision
	/*
	 * tests if a collision is only being detected if the person is in range of the
	 * paddle or the wall
	 */

	@Test
	void testCollisionDetectionWithPaddle() {
		GameBoard gameBoard = new GameBoard();

		// collisions with paddle
		Paddle paddle = new Paddle(0.5);

		Person person1 = new Person(false, paddle.getPosition()[0] + paddle.getLength() / 2,
				paddle.getPosition()[1] / GameBoardUI.getHEIGHT() - Person.getR(), 0, gameBoard);

		Person person2 = new Person(false, 0, 0, 180, gameBoard);
		assertFalse(Collidable.detectRectCollision(paddle, person2));

	}

	@Test
	void testCollisionDetectionWithWalls() {

		GameBoard gameBoard = new GameBoard();

		Person person3 = new Person(false, 1.1, 0.5, 90, gameBoard);
		assertTrue(Collidable.detectSideCollision(person3));

		Person person4 = new Person(false, -0.1, 0.5, 270, gameBoard);
		assertTrue(Collidable.detectSideCollision(person4));

		Person person5 = new Person(false, 0.9, 0.5, 90, gameBoard);
		assertFalse(Collidable.detectSideCollision(person5));

		Person person6 = new Person(false, 0.1, 0.5, 270, gameBoard);
		assertFalse(Collidable.detectSideCollision(person6));
	}

	@Test
	void testDirectionChangeAfterCollisionWithPaddle() {

		GameBoard gameBoard = new GameBoard();

		Paddle paddle = new Paddle(0.5);
		Person person = new Person(false, paddle.getXPosition(), GameBoardUI.getHEIGHT(), 180, gameBoard);

		paddle.collide(person, 90);
		assertNotEquals(180, person.getDirection());

	}

	// MockTests
	// is a collision with a block being detected correctly?
	// is the classification of a block (house or hospital) being done correctly?
	/**
	 * @Test void testCollisionWithBlock() { Block block =
	 *       EasyMock.createMock(Block.class); Person person = new Person(false,
	 *       0.5, 0.5, 180, gameBoard); EasyMock.expectLastCall() .andAnswer(() -> {
	 *       person.collide(90); return null; }); EasyMock.replay(block);
	 *       block.collide(person,90); assertEquals(0,person.getDirection()); }
	 */
	// Tests for GameBoard
	@Test
	void testGeneratePersonsAfterGameStart() {
		GameBoard gameBoard = new GameBoard();
		gameBoard.startGame();
		assertNotEquals(0, gameBoard.people.size());
	}

	@Test
	void testGeneratePaddleAfterGameStart() {
		GameBoard gameBoard = new GameBoard();
		gameBoard.startGame();

		assertNotEquals(null, gameBoard.paddle);
	}

	@Test
	void testIfAllPersonsBeingMovedAfterGameStart() {
		GameBoard gameBoard = new GameBoard();
		gameBoard.startGame();
		List<Person> people = gameBoard.people;

		try {
			Thread.sleep(1000 / 60);
		} catch (InterruptedException ignored) {
		}

		double[] oldPositions = new double[people.size()];

		for (int i = 0; i < people.size(); i++) {
			oldPositions[i] = people.get(i).getPosition()[0];
		}

		try {
			Thread.sleep(1000 / 60);
		} catch (InterruptedException ignored) {
		}

		for (int i = 0; i < people.size(); i++) {
			assertNotEquals(oldPositions[i], people.get(i).getPosition()[0]);
		}

	}

	// Tests for Strategy Pattern
	@Test
	void testCreationOfNewBlocks() {

		GameBoard gameBoard = new GameBoard();

		List<Person> persons = new ArrayList<>();

//		Block block = StrategyPattern.Context.selectCreation(persons);
//		assertEquals(House.class, block.getClass());

		for (int i = 1; i <= Policy.INFECTION_LIMIT + 1; i++) {
			persons.add(new Person(true, 0, 0, 0, gameBoard));
		}

//		Block block1 = StrategyPattern.Context.selectCreation(persons);

//		assertEquals(Hospital.class, block1.getClass());

	}

	// Tests for KeyBoard / Paddle
	/*
	 * tests if a keyboard input leads a changed position of the paddle
	 */

	@Test
	void testPaddlePositionUpdate() {
		Paddle paddle = new Paddle(0);
		double paddleOldPosition = paddle.getXPosition();

		paddle.moveLeft();
		assertNotEquals(paddleOldPosition, paddle.getXPosition());

		paddle.moveRight();
		paddle.moveRight();
		assertNotEquals(paddleOldPosition, paddle.getXPosition());

	}

}
