package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	public int worldX,worldY;
	public int speed;
	
	public BufferedImage up1, up2, down1,down2, left1, left2, right1, right2;
	public String direction;
	public int spriteCounter =0;
	public int spriteNum = 1;
	//public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	//default solid area for entitties
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	
	public boolean collisionOn = false;
	GamePanel gp;
	
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public BufferedImage setup(String imagePath) {
		UtilityTool utilityTool = new UtilityTool();
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = utilityTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
		int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;
		//this if statement will only draw tiles in the visible screen rather than all tiles at the same time
		//as long as  tile is in the boundary it will be drawn
		//will look the same but saves some processing
		BufferedImage image = null;
		if(worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X && 
		   worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X &&
		   worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y &&
		   worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
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
			g2.drawImage(image, screenX, screenY, gp.TILE_SIZE,gp.TILE_SIZE,null);
		}
	}
	
}
