package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	final static int ORIGINAL_TILE_SIZE = 16;
	final static int SCALE = 3;
	final static int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;//48
	final static int MAX_SCREEN_COL = 16;
	final static int MAX_SCREEN_ROW = 12;
	final static int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; //768
	final static int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;//576
	
	KeyHandler kh = new KeyHandler();
	Thread gameThread;
	
	//set players default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);//drawing done in an offscreen buffer
		this.addKeyListener(kh);
		this.setFocusable(true);
		
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(gameThread != null) {
			//update 
			update();
			repaint();
			//draw screen
			
		}
	}
	
	public void update() {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;//has some inbuilt functions
		g2.setColor(Color.white);
		g2.fillRect(playerX, playerY, TILE_SIZE, TILE_SIZE);
		
		g2.dispose();
		
	}
	
	
	
}
