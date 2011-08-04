package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class AI extends Thread {

	// Player 2 (Comp) =  1
	// Player 1		   = -1
	// Tie / No Winner =  0
	
	Map<Integer, Position> uniqueBoardAndMove;

	Node<Triplet> root;
	
	Tree<Triplet> tree;
		
	public AI(Board grid) {
		this.uniqueBoardAndMove = new HashMap<Integer, Position>();
		root = new Node<Triplet>(new Triplet(new Board(grid), new Position(0, 0), 0));
		tree = new Tree<Triplet>();
		tree.setRootElement(root);
	}
	
	@Override
	public void run() {
		createTree(root);
		System.out.println("TREE DONE!");
	}
	
	private void createTree(Node<Triplet> root) {

		Queue<Node<Triplet>> queue = new LinkedList<Node<Triplet>>();
		
		queue.add(root);
		
		while(!queue.isEmpty()) {
			int nodesCreated = 0;
			Node<Triplet> node = queue.remove();
			
			List<Position> positions = getAvailablePositions(node.getData().getGrid());
			
			for(Position pos : positions) {
				Board temp = new Board(node.getData().getGrid());
				temp.setPieceAt(pos.getX(), pos.getY());
				
				Node<Triplet> child = new Node<Triplet>(new Triplet(temp, pos, getBoardValue(temp)));
				nodesCreated++;
				node.addChild(child);
				queue.add(child);
			}
			if(nodesCreated == 0) {
				System.out.println(queue.size());
				System.out.println(queue.peek());
				System.out.println(tree.toList().size());
			}
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
