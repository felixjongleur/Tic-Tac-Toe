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
	
	Map<Integer, Board> uniqueBoardAndMove;

	Node<Triplet> root;
	
	Tree<Triplet> tree;
		
	public AI(Board grid) {
		this.uniqueBoardAndMove = new HashMap<Integer, Board>();
		root = new Node<Triplet>(new Triplet(new Board(grid), new Position(0, 0), 0));
		tree = new Tree<Triplet>();
		tree.setRootElement(root);
	}
	
	@Override
	public void run() {
		createTree(root);
		System.out.println("TREE DONE!");
		System.out.println(tree.toList().size());
		System.out.println(uniqueBoardAndMove.size());
		
		int count = 0;
		for(Node<Triplet> node : tree.toList()) {
			if(node.getData().getScore() == 0)
				count++;
		}
		System.out.println("Count = " + count);
	}
	
	private void createTree(Node<Triplet> root) {

		Queue<Node<Triplet>> queue = new LinkedList<Node<Triplet>>();
		
		queue.add(root);
		uniqueBoardAndMove.put(root.getData().getGrid().hashCode(), root.getData().getGrid());

		int nodesCreated = 0;
		while(!queue.isEmpty()) {
			Node<Triplet> node = queue.remove();
			
			List<Position> positions = getAvailablePositions(node.getData().getGrid());
			
			for(Position pos : positions) {
				Board temp = new Board(node.getData().getGrid());
				temp.setPieceAt(pos.getX(), pos.getY());				
				
				if(getBoardValue(temp) == 0 && isBoardUnique(temp)) {
					Node<Triplet> child = new Node<Triplet>(new Triplet(temp, pos, getBoardValue(temp)));
					nodesCreated++;
					node.addChild(child);
					queue.add(child);
					uniqueBoardAndMove.put(temp.hashCode(), temp);
				} else {
			//		System.out.println(uniqueBoardAndMove.get(temp.hashCode()));
			//		System.out.println(nodesCreated);
				}
			}
		/*	if(nodesCreated == 1) {
				System.out.println(queue.size());
				System.out.println(queue.peek());
				System.out.println(tree.toList().size());
			}*/
		}
	}
	
	public boolean isBoardUnique(Board temp) {
		// Check For Existing Hash Code
		if(uniqueBoardAndMove.containsKey(temp.hashCode()))
			return false;
		
		// Rotate Right 3 Times
		for(int count = 0; count < 3; count++) {
			temp = rotateRight(temp);
			if(uniqueBoardAndMove.containsKey(temp.hashCode()))
				return false;
		}		
		
		return true;
	}
	
	public Board rotateRight(Board temp) {
		Board board = new Board();

		board.getGrid()[0] = temp.getGrid()[6];
		board.getGrid()[1] = temp.getGrid()[3];
		board.getGrid()[2] = temp.getGrid()[0];
		board.getGrid()[3] = temp.getGrid()[7];
		board.getGrid()[4] = temp.getGrid()[4];
		board.getGrid()[5] = temp.getGrid()[1];
		board.getGrid()[6] = temp.getGrid()[8];
		board.getGrid()[7] = temp.getGrid()[5];
		board.getGrid()[8] = temp.getGrid()[2];
		
		return board;
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
