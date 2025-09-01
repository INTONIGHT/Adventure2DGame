package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	GamePanel gp;
	KeyHandler keyH;
	public final int SCREEN_X;
	public final int SCREEN_Y;
	//where we draw the player on screen
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		SCREEN_X = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);
		SCREEN_Y = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);
		//these values can work for the image to make it smaller for collisions
		solidArea = new Rectangle(8,16,32,32);
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		//not where your character is drawn but where you are on the worldmap
		worldX= gp.TILE_SIZE * 23;
		worldY = gp.TILE_SIZE * 21;
		speed = 4;
		direction = "down";
		
	}
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
			spriteCounter ++;
			//gets called 60 times a second
			//this rate will change the sprite every 10 frames
			if(spriteCounter > 10) {//you can change this number if you feel it isnt smooth
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum =1;
				}
				spriteCounter  = 0;
			}
		}
		if(keyH.upPressed) {
			direction = "up";
			worldY -= speed;
		}
		if(keyH.downPressed) {
			direction = "down";
			worldY += speed;
		}
		if(keyH.leftPressed) {
			direction = "left";
			worldX -= speed;
		}
		if(keyH.rightPressed) {
			direction = "right";
			worldX += speed;
		}
		collisionOn = false;
		gp.collisionDetector.checkTile(this);
		
	}
	public void draw(Graphics2D g2) {
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.TILE_SIZE, gp.TILE_SIZE);
		BufferedImage image = null;
		switch(direction) {
		case"up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down" :
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;
		}
		g2.drawImage(image, SCREEN_X, SCREEN_Y,gp.TILE_SIZE,gp.TILE_SIZE,null);
		
		
	}
	
}
