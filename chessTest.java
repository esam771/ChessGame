package chessGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class chessTest {

	@Test
	void test() {

		mainChess a = new mainChess();
		a.generateBoard();
		a.playGame();
	}

}
