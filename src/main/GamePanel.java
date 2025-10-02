package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;

import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	final  int ORIGINAL_TILE_SIZE = 16;
	final  int SCALE = 3;
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;//48
	public final int MAX_SCREEN_COL = 16;
	public final int MAX_SCREEN_ROW = 12;
	public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; //768
	public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;//576
	//world settings
	public final int MAX_WORLD_COL = 50;
	public final int MAX_WORLD_ROW = 50;
	public final int WORLD_WIDTH = TILE_SIZE  * MAX_WORLD_COL;
	public final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;
	
	public KeyHandler keyH = new KeyHandler(this);
	//sound
	Sound music = new Sound();
	Sound se = new Sound();
	Thread gameThread;
	final static int FPS = 60;
	//entity and objects
	public Player player = new Player(this,keyH);
	TileManager tileM = new TileManager(this);
	public boolean gameRunning = true;
	
	public CollisionDetector collisionDetector  = new CollisionDetector(this);
	public AssetLoader assetLoader = new AssetLoader(this);
	public UI ui = new UI(this);
	public EventHandler eventHandler = new EventHandler(this);

	//display 10 objects at the same time not 10 objects can change this value if you want but more objects will slow the game down
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
	public Entity monsters[] = new Entity[20];
	ArrayList<Entity> entityList = new ArrayList<>();
	//game state
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int titleState = 0;
	
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);//drawing done in an offscreen buffer
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	public void setUpGame() {
		assetLoader.setObject();
		assetLoader.setNPC();
		assetLoader.setMonster();
		//turning off the background music as it annoys me lol
		//can uncomment if you want the music
		//playMusic(0); //will play the background music 
		gameState = titleState;
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	public void stopGame() {
		this.stopMusic();
		gameRunning = false;
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
		
		while(gameThread  != null) {
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
				//System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
		}
	}
	
	public void update() {
		//if you change the playstate number that can helpo
		if(gameState == playState) {
			
			player.update();
			
			//NPC
			for(int i =0; i< npc.length;i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
			for(int i =0; i<monsters.length;i++) {
				if(monsters[i] != null) {
					monsters[i].update();
				}
			}
		}
		if(gameState == pauseState) {
			
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;//has some inbuilt functions
		//DEBUG
		long drawStart =0;
		if(keyH.checkDrawTime) {
			drawStart = System.nanoTime();
		}
		
		//TITLE Screen
		if(gameState == titleState) {
			ui.draw(g2);
		}
		//others
		else {
			
			
			tileM.draw(g2);
			
			//adding entities to the array list
			entityList.add(player);
			for(int i =0; i<npc.length;i++) {
				if(npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			
			for(int i =0; i<obj.length;i++) {
				if(obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			for(int i =0; i<monsters.length;i++) {
				if(monsters[i] != null) {
					entityList.add(monsters[i]);
				}
			}
			
			//sort the list based on world y
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
				
			});
			//draw entities
			for(int i =0; i<entityList.size();i++) {
				entityList.get(i).draw(g2);
			}
			//empty entity List to not continually grow this array List
			entityList.clear();
			
			ui.draw(g2);
		}
		
		//tile
		
		//DEBUG
		if(keyH.checkDrawTime) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			double milliseconds = (double) passed/1000000;
			g2.setColor(Color.white);
			g2.drawString("Draw Time milliseconds : " + milliseconds, 10, 400);
			System.out.println("milliseconds passed: " + milliseconds);
		}
		
		g2.dispose();
		
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	
	public void playSoundEffect(int i) {
		se.setFile(i);
		se.play();
	}
	
	
}
