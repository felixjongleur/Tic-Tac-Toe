package main;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TicTacToe extends Applet implements MouseListener, Runnable {

	private static final long serialVersionUID = 4523571763894795612L;

	private Image backBuffer;

	private Graphics backg;

	private int width = 400;
	private int height = 400;	

	private Thread ticTacToeThread;
	
	private Board board;
	
	private AI AIThread;
	private boolean ai = true;
	
	@Override
	public void init() {
		setSize(width, height);

		setLayout(null);
		setBackground(Color.darkGray);

		addMouseListener(this);

		backBuffer = createImage(width, height);
		backg = backBuffer.getGraphics();
		
		board = new Board();

		
		if(ai) {
			AIThread = new AI(board);
			AIThread.start();
		}
	}

	public void start() {
		if(ticTacToeThread == null) {
			ticTacToeThread = new Thread(this);
			ticTacToeThread.start();
		}
	}
	
	public void stop() {
		ticTacToeThread = null;
	}
	
	public void run() {
		while(Thread.currentThread() == ticTacToeThread) {
			repaint();
		}
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	@Override
	public void paint(Graphics g) {

		backg.clearRect(0, 0, width, height);
		backg.setColor(Color.white);
		// Vertical Lines
		backg.drawLine(150, 50, 150, 350);
		backg.drawLine(250, 50, 250, 350);
		// Horizontal Lines
		backg.drawLine(50, 150, 350, 150);
		backg.drawLine(50, 250, 350, 250);
		
		Font font = new Font("SansSarif", Font.PLAIN, 40);
		backg.setFont(font);
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 3; x++) {				
				if(board.getPieceAt(x, y) == 1) {
					backg.drawString("O", (x * 100) + 90, (y * 100) + 115);
				} else if(board.getPieceAt(x, y) == 2){
					backg.drawString("X", (x * 100) + 90, (y * 100) + 115);					
				}
			}
		}
		
		g.drawImage(backBuffer, 0, 0, this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(board.gameOver())
			return;
		
		int mx = e.getX();
		int my = e.getY();
		
		if(mx >= 50 && mx <= 350 && my >= 50 && my <= 350) {
			int x, y;
			
			if(mx <= 149)
				x = 0;
			else if(mx <= 249)
				x = 1;
			else
				x = 2;

			if(my <= 149)
				y = 0;
			else if(my <= 249)
				y = 1;
			else
				y = 2;

			board.setPieceAt(x, y);
			
			if(board.gameOver())
				System.out.println("PLAYER " + board.getTurn() + " WINS!!");
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {	}
	
	@Override
	public void mouseExited(MouseEvent e) { }
	
	@Override
	public void mousePressed(MouseEvent e) { }
	
	@Override
	public void mouseReleased(MouseEvent e) { }
}
