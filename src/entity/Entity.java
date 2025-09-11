package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

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
	
}
