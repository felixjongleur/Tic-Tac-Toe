package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AI extends Thread {

	// Player 2 (Comp) =  1
	// Player 1		   = -1
	// Tie / No Winner =  0
	
	Tree<Triplet> tree;
	
	Map<Integer, Position> uniqueBoardAndMove;
	
	private int nodesCreated = 0;
	
	public AI(Board grid) {
		this.uniqueBoardAndMove = new HashMap<Integer, Position>();
		this.tree = new Tree<Triplet>();
		this.tree.setRootElement(new Node<Triplet>(new Triplet(new Board(grid), new Position(0, 0), 0)));
	}
	
	@Override
	public void run() {
		createTree(tree.getRootElement());
		
		System.out.println(tree);
	}
	
	private void createTree(Node<Triplet> node) {
		System.out.println("NUM OF NODES = " + nodesCreated);
		List<Position> positions = getAvailablePositions(node.getData().getGrid());
		
		for(Position pos : positions) {
			Board temp = new Board(node.getData().getGrid());
			temp.setPieceAt(pos.getX(), pos.getY());
			
			if(!uniqueBoardAndMove.containsKey(temp.getHash())) {
				Node<Triplet> child = new Node<Triplet>(new Triplet(temp, pos, getBoardValue(temp)));
				nodesCreated++;
				node.addChild(child);
				uniqueBoardAndMove.put(temp.getHash(), pos);
			}
		}
		
		for(Node<Triplet> child : node.getChildren()) {
			if(child.getData().getScore() == 0)
				createTree(child);
		}
	}
	
	private int getBoardValue(Board grid) {

		if(grid.gameOver()) {
			if(grid.getTurn() == 2)
				return 1;
			else if(grid.getTurn() == 1)
				return -1;			
		}		
		return 0;
	}
	
	private List<Position> getAvailablePositions(Board grid) {
		List<Position> positions = new ArrayList<Position>();
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++) {
				if(grid.getPieceAt(x, y) == 0)
					positions.add(new Position(x, y));
			}
		}
		
		return positions;
	}
}
