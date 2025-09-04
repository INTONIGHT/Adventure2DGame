package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Key;

public class UI {
	GamePanel gp;
	Font arial_40;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial",Font.PLAIN,40);
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		//dont want to instantiate things as then it will be called every loop
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		g2.drawImage(keyImage,gp.TILE_SIZE/2,gp.TILE_SIZE/2,gp.TILE_SIZE,gp.TILE_SIZE,null);
		
		g2.drawString("x " + gp.player.keysPossessed,74,65);
		//message
		if(messageOn) {
			//changing font size for this message
			g2.setFont(g2.getFont().deriveFont(30F));
			g2.drawString(message, gp.TILE_SIZE/2, gp.TILE_SIZE*5);
			messageCounter++;
			if(messageCounter > 120) {
				messageCounter = 0;
				messageOn = false;
			}
		}
		
	}
}
