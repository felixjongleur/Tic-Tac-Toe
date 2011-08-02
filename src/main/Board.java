package main;

public class Board {

	// 0 == empty
	// 1 == O
	// 2 == X
	private int[][] grid;
	
	private int turn = 1;
	
	public Board() {
		grid = new int[3][3];
	}
	
	public int[][] getGrid() {
		return grid;
	}
	
	public int getHash() {
		int value = 0;
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++) {
				if(getPieceAt(x, y) == 2) {
					value += 2 * (y + x);
				}
				if(getPieceAt(x, y) == 1) {
					value += (y + x);
				}
			}
		}
		return value;
	}
	
	public Board(Board g) {
		grid = new int[3][3];
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++) {
				setValueAt(x, y, g.getPieceAt(x, y));
			}
		}
		this.turn = g.getTurn();
	}
	
	public int getPieceAt(int x, int y) {
		return grid[y][x];
	}
	
	public void setPieceAt(int x, int y) {
		if(getPieceAt(x, y) == 0) {
			grid[y][x] = turn;
			
			if(!gameOver()) {
				if(turn == 1)
					turn = 2;
				else
					turn = 1;
			}
		}
	}
	
	private void setValueAt(int x, int y, int value) {
		grid[y][x] = value;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public boolean gameOver() {
		// Check Rows
		for(int y = 0; y < 3; y++) {
			if(getPieceAt(0, y) == getPieceAt(1, y))
				 if(getPieceAt(1, y)== getPieceAt(2, y))
					 if(getPieceAt(2, y) != 0)
						 return true;
		}
		// Check Columns
		for(int x = 0; x < 3; x++) {
			if(getPieceAt(x, 0) == getPieceAt(x, 1))
				 if(getPieceAt(x, 1)== getPieceAt(x, 2))
					 if(getPieceAt(x, 2) != 0)
						 return true;
		}
		// Check Down Right
		if(getPieceAt(0, 0) == getPieceAt(1, 1))
			 if(getPieceAt(1, 1)== getPieceAt(2, 2))
				 if(getPieceAt(2, 2) != 0)
					 return true;
		// Check Down Left
		if(getPieceAt(2, 0) == getPieceAt(1, 1))
			 if(getPieceAt(1, 1)== getPieceAt(0,2))
				 if(getPieceAt(0, 2) != 0)
					 return true;		
		
		return false;
	}
	
	@Override
	public String toString() {
		String output = "\n";
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++) {
				if(getPieceAt(x, y) == 0)
					output += " ";
				else if(getPieceAt(x, y) == 1)
					output += "O";
				else
					output += "X";
				if(x < 2)
					output += "|";
			}
			if(y < 2)
				output += "\n-----\n";
		}
		return output;
	}
}
