package main;

public class Triplet {
	private Position pos;
	private Integer score;
	private Board grid;

	public Triplet(Board grid, Position pos, Integer score) {
		this.pos = pos;
		this.score = score;
		this.grid = grid;
	}

	public Position getPos() {
		return pos;
	}
	
	public int getScore() {
		return score;
	}
	
	public Board getGrid() {
		return grid;
	}
	
	@Override
	public String toString() {
		return "[" + pos + " , " + score + " , \n" + grid + "]";
	}
}