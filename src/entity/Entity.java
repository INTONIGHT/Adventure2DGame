package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
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
	public BufferedImage attackUp1, attackUp2, attackLeft1, attackLeft2, attackRight1, attackRight2, attackDown1, attackDown2;
	public String direction = "down";
	public int spriteCounter =0;
	public int spriteNum = 1;
	//public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	//default solid area for entitties
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int actionLockCounter = 0;
	public Rectangle attackArea =  new Rectangle(0,0,0,0);
	
	public boolean collisionOn = false;
	GamePanel gp;
	String dialogues[] = new String[20];
	public int dialogueIndex = 0;
	
	//Character Status
	public int maxLife;
	public int life;
	
	public BufferedImage image;
	public BufferedImage image2;
	public BufferedImage image3;
	public String name;
	public boolean collision = false;
	public boolean invincible = false;
	public int invincibleCounter =0;
	public int type;//0 = player 1 =npc 2 =monster
	public boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	public int dyingCounter = 0;
	public int dyingLoopCount = 0;
	public boolean hpBarOn = false;
	public int hpBarCounter = 0;
	//player statistics
	public int level;
	public int strength, dexterity, attack, defense, exp, nextLevelExp, coin;
	public Entity currentWeapon, currentShield;
	//item attributes
	public int attackValue,defenseValue;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public BufferedImage setup(String imagePath, int width,int height) {
		UtilityTool utilityTool = new UtilityTool();
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = utilityTool.scaleImage(image, width,height);
			
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
			//drawing monster health bar
			//this is to conditionally display the hp bar
			if(type == 2 && hpBarOn) {//entity is mosnter
				double oneScale = (double) gp.TILE_SIZE /maxLife;
				double hpBarValue = oneScale * life;
				
				
				g2.setColor(new Color(35,35,35));
				g2.fillRect(screenX - 1, screenY - 16, gp.TILE_SIZE+2, 12);
				
				g2.setColor(new Color(255,0,30));
				g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);
				hpBarCounter ++;
				if(hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
				
			}
			
			
			
			
			if(invincible) {
				hpBarOn = true;
				hpBarCounter = 0;
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
			}
			if(dying) {
				dyingAnimation(g2);
			}
			g2.drawImage(image, screenX, screenY, gp.TILE_SIZE,gp.TILE_SIZE,null);
			//reset alpha
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
		}
	}
	
	public void setAction() {
		
	}
	
	public void damageReaction() {
		
	}
	
	public void speak() {
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex ++;
		switch(gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
			
		}
	
	}
	public void update() {
		setAction();
		collisionOn = false;
		gp.collisionDetector.checkTile(this);
		gp.collisionDetector.checkObject(this,false);
		gp.collisionDetector.checkEntity(this, gp.npc);
		gp.collisionDetector.checkEntity(this, gp.monsters);
		boolean contactPlayer = gp.collisionDetector.checkPlayer(this);
		
		if(this.type == 2 && contactPlayer) {
			if(!gp.player.invincible) {
				//give damage
				gp.player.life -=1;
				gp.player.invincible = true;
			}
		}
		
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
		spriteCounter ++;
		//gets called 60 times a second
		//this rate will change the sprite every 10 frames
		if(spriteCounter > 12) {//you can change this number if you feel it isnt smooth
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum =1;
			}
			spriteCounter  = 0;
		}
		//monsters
		if(invincible) {
			invincibleCounter ++;
			if(invincibleCounter >40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void setSolidArea(int x, int y, int width,int height) {
		solidArea.x = x;
		solidArea.y = y;
		solidArea.width = width;
		solidArea.height = height;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		
		if(dyingCounter <= 5) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0F));
		}
		if(dyingCounter > 5 && dyingCounter <= 10) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
		}
		if(dyingLoopCount > 8) {
			dying = false;
			alive = false;
		}
		if(dyingCounter >10) {
			dyingLoopCount ++;
			dyingCounter = 0;
		}
		
	}
	
}
