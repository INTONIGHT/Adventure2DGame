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
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	final static int FPS = 60;
	
	//set players default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);//drawing done in an offscreen buffer
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		double drawInterval = 1000000000 /FPS;
//		double nextDrawTime = System.nanoTime() + drawInterval;
//		while(gameThread != null) {
//			//update 
//			
//			
//			update();
//			repaint();
//			//draw screen
//			//sleep method
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime = remainingTime/1000000;//convert to milliseconds
//				if( remainingTime < 0) {
//					remainingTime = 0;
//				}
//				Thread.sleep((long) remainingTime);
//				nextDrawTime += drawInterval;
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000 /FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta --;
				drawCount ++;
				
			}
			//display fps
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
		}
	}
	
	public void update() {
		if(keyH.upPressed) {
			playerY -= playerSpeed;
		}
		if(keyH.downPressed) {
			playerY += playerSpeed;
		}
		if(keyH.leftPressed) {
			playerX -= playerSpeed;
		}
		if(keyH.rightPressed) {
			playerX += playerSpeed;
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;//has some inbuilt functions
		g2.setColor(Color.white);
		g2.fillRect(playerX, playerY, TILE_SIZE, TILE_SIZE);
		
		g2.dispose();
		
	}
	
	
	
}
