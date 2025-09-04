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
	public int keysPossessed = 0;
	//where we draw the player on screen
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		SCREEN_X = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);
		SCREEN_Y = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);
		//these values can work for the image to make it smaller for collisions
		solidArea = new Rectangle(8,16,32,32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
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
			
		}
		if(keyH.downPressed) {
			direction = "down";
			
		}
		if(keyH.leftPressed) {
			direction = "left";
			
		}
		if(keyH.rightPressed) {
			direction = "right";
			
		}
		//check the collision
		collisionOn = false;
		gp.collisionDetector.checkTile(this);
		//check object collision
		int objIndex = gp.collisionDetector.checkObject(this, true);
		pickUpObject(objIndex);
		
		//if collision is false
		if(!collisionOn) {
			switch(direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
				
			}
		}
		
		
		
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
	
	public void pickUpObject(int index) {
		if(index != -1) {
			//gp.obj[index] = null;
			//deletes the object we touch
			String objectName = gp.obj[index].name;
			
			switch(objectName) {
			case "Key":
				gp.playSoundEffect(1);
				keysPossessed ++;
				gp.obj[index] = null;
				gp.ui.showMessage("You got a key!");
				break;
			case "Door":
				if(keysPossessed > 0) {
					gp.obj[index] = null;
					gp.playSoundEffect(3);
					keysPossessed--;
				}
				break;
			case "Boots":
				speed += 1;
				gp.playSoundEffect(2);
				gp.obj[index] = null;
				break;
			}
		}
	}
	
}
