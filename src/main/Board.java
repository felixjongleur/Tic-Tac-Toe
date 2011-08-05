package main;

import java.util.Arrays;

public class Board {

	// 0 == empty
	// 1 == X
	// 2 == O
	private int[] grid;
	
	private int turn = 1;
	
	public Board() {
		grid = new int[9];
	}
	
	public int[] getGrid() {
		return grid;
	}
	
	public int getPosition(int x, int y) {
		if(y == 0)
			return x;
		if(y == 1)
			return x + 3;
		
		return x + 6;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(grid);
		result = prime * result + turn;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (!Arrays.equals(grid, other.grid))
			return false;
		if (turn != other.turn)
			return false;
		return true;
	}

	public Board(Board g) {
		grid = new int[9];
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++) {
				setValueAt(x, y, g.getPieceAt(x, y));
			}
		}
		this.turn = g.getTurn();
	}
	
	public int getPieceAt(int x, int y) {		
		return grid[getPosition(x, y)];
	}
	
	public void setPieceAt(int x, int y) {
		if(getPieceAt(x, y) == 0) {
			grid[getPosition(x, y)] = turn;
			
			if(!gameOver()) {
				if(turn == 1)
					turn = 2;
				else
					turn = 1;
			}
		}
	}
	
	private void setValueAt(int x, int y, int value) {
		grid[getPosition(x, y)] = value;
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
					output += "X";
				else
					output += "O";
				if(x < 2)
					output += "|";
			}
			if(y < 2)
				output += "\n-----\n";
		}
		return output;
	}
}
