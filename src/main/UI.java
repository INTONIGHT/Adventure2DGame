package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;


public class UI {
	GamePanel gp;
	Font arial_40, arial_80B;
	Graphics2D g2;
	//BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished;
	double playTime;
	//this means up to 2 decimal points
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	public String currentDialogue = "";
	public int commandNum = 0;
	public BufferedImage heart_full,heart_half,heart_blank;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial",Font.PLAIN,40);
		arial_80B = new Font("Arial",Font.BOLD,80);
//		OBJ_Key key = new OBJ_Key(gp);
//		keyImage = key.image;
		//create HUD object
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	public void oldDraw(Graphics2D g2) {
//		if(gameFinished) {
//			String text;
//			int textLength;
//			g2.setFont(arial_40);
//			g2.setColor(Color.white);
//			text = "You found the treasure!";
//			//returns length of the text
//			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//			
//			int x = gp.SCREEN_WIDTH/2 - textLength/2;
//			int y = gp.SCREEN_HEIGHT/2 - (gp.TILE_SIZE*3);
//			g2.drawString(text, x, y);
//			
//			text = "Your time was: " + dFormat.format(playTime) + "!";
//			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//			x = gp.SCREEN_WIDTH/2 - textLength/2;
//			y = gp.SCREEN_HEIGHT/2 + (gp.TILE_SIZE*4);
//			g2.drawString(text, x, y);
//			
//			g2.setFont(arial_80B);
//			g2.setColor(Color.GREEN);
//			text = "Congratulations!!!!!";
//			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//			x = gp.SCREEN_WIDTH/2 - textLength/2;
//			y = gp.SCREEN_HEIGHT/2 + (gp.TILE_SIZE*2);
//			g2.drawString(text, x, y);
//			
//			gp.gameThread = null;
//			
//		}else {
//			//dont want to instantiate things as then it will be called every loop
//			g2.setFont(arial_40);
//			g2.setColor(Color.white);
//			//g2.drawImage(keyImage,gp.TILE_SIZE/2,gp.TILE_SIZE/2,gp.TILE_SIZE,gp.TILE_SIZE,null);
//			//time
//			playTime += (double) 1/60;
//			g2.drawString("Time: " + dFormat.format(playTime), gp.TILE_SIZE * 11, 65);
//			g2.drawString("x " + gp.player.keysPossessed,74,65);
//			//message
//			if(messageOn) {
//				//changing font size for this message
//				g2.setFont(g2.getFont().deriveFont(30F));
//				g2.drawString(message, gp.TILE_SIZE/2, gp.TILE_SIZE*5);
//				messageCounter++;
//				if(messageCounter > 120) {
//					messageCounter = 0;
//					messageOn = false;
//				}
//			}
//		}
	}
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		//play state
		if(gp.gameState == gp.playState) {
			//draw playstate
			drawPlayerLife();
		}
		//pausestate
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		//dialogue state
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		if(gp.gameState == gp.titleState) {
			drawTitleState();
		}
	}
	
	public void drawPlayerLife() {
		//can use this to test the drawing:
		//gp.player.life = 5;
		// TODO Auto-generated method stub
		int x = gp.TILE_SIZE/2;
		int y = gp.TILE_SIZE/2;
		int i =0;
		//draw max life
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x,y,null);
			i++;
			x += gp.TILE_SIZE;
		}
		//reset
		 x = gp.TILE_SIZE/2;
		 y = gp.TILE_SIZE/2;
		 i =0;
		 //draw current life
		while(i < gp.player.life) {
			g2.drawImage(heart_half,x,y,null);
			i++;
			if(i <gp.player.life) {
				g2.drawImage(heart_full, x,y,null);
			}
			i++;
			x += gp.TILE_SIZE;
		}
	}

	public void drawTitleState() {
		// TODO Auto-generated method stub
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		String text = "Adventure Game";
		int x = getCenteredX(text);
		int y = gp.TILE_SIZE * 3;
		//shadow
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		//main color
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		//image
		
		x = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE*2)/2;
		y += gp.TILE_SIZE * 2;
		g2.drawImage(gp.player.down1,x,y,gp.TILE_SIZE*2,gp.TILE_SIZE*2,null);
		//menu
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
		
		text = "NEW GAME";
		x = getCenteredX(text);
		y += gp.TILE_SIZE * 3.5;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x - gp.TILE_SIZE, y);
		}
		
		text = "LOAD GAME";
		x = getCenteredX(text);
		y += gp.TILE_SIZE;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x - gp.TILE_SIZE, y);
		}
		text = "QUIT";
		x = getCenteredX(text);
		y += gp.TILE_SIZE;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x - gp.TILE_SIZE, y);
		}
		
		
	}

	public void drawDialogueScreen() {
		// TODO Auto-generated method stub
		//window
		int x = gp.TILE_SIZE * 2;
		int y = gp.TILE_SIZE / 2;
		int width = gp.SCREEN_WIDTH - (gp.TILE_SIZE * 4);
		int height = gp.TILE_SIZE * 4;
		drawSubWindow(x,y,width,height);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
		x += gp.TILE_SIZE;
		y += gp.TILE_SIZE;
		//drawing text
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
		
		
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		//the 4th number affects the opacity
		Color c = new Color(0,0,0,210);
		g2.setColor(c);
		//arcwidth height are the 35
		g2.fillRoundRect(x, y, width, height,35,35);
		c = new Color(255,255,255);
		g2.setColor(c);
		//width of the stroke
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}

	public void drawPauseScreen() {
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80));
		String text = "PAUSED";
		int x = getCenteredX(text);
		
		int y = gp.SCREEN_HEIGHT/2;
		g2.drawString(text, x, y);
	}
	
	public int getCenteredX(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.SCREEN_WIDTH /2 - length /2;
		return x;
	}
	
}
