package Controller;

import Model.Paddle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	public Paddle paddle;
	public Paddle paddle2;
	public boolean multiplayer;
	public GameBoard gameBoard;

	public Keyboard(Paddle paddle, GameBoard gameBoard) {
		this.paddle = paddle;
		this.gameBoard = gameBoard;
		multiplayer = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_A) {
			paddle.moveLeft();
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			paddle.moveRight();
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT && multiplayer) {
			paddle2.moveRight();
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT && multiplayer) {
			paddle2.moveLeft();
		}

		if (e.getKeyCode() == KeyEvent.VK_M && !multiplayer) {
			gameBoard.multiplayer();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	public void setPaddle2(Paddle paddle2) {
		this.paddle2 = paddle2;
		multiplayer = true;
	}
}
