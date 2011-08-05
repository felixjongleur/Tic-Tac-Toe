package main;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BoardTest {
	
	@Test
	public void testCompareBoards() {
		Board board1 = new Board();
		Board board2 = new Board();
		board1.setPieceAt(0, 0);
		board2.setPieceAt(0, 0);
		
		assertEquals(board1, board2);
	}	
	
	@Test
	public void testRotate() {
		Board board1 = new Board();
		AI ai = new AI(board1);
		board1.setPieceAt(0, 0);
		board1.setPieceAt(1, 0);
		board1.setPieceAt(2, 0);

		Board board2 = ai.rotateRight(board1);
		Board board3 = ai.rotateRight(board2);
		
		System.out.println(board1);
		System.out.println(board2);
		System.out.println(board3);
	}
}
