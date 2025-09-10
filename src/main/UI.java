package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

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
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial",Font.PLAIN,40);
		arial_80B = new Font("Arial",Font.BOLD,80);
//		OBJ_Key key = new OBJ_Key(gp);
//		keyImage = key.image;
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
		if(gp.gameState == gp.playState) {
			//draw playstate
		}
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
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
