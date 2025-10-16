package entity;


import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;




import main.GamePanel;
import main.KeyHandler;


public class Player extends Entity{
	
	public KeyHandler keyH;
	public final int SCREEN_X;
	public final int SCREEN_Y;
	
	//public int keysPossessed = 0;
	//where we draw the player on screen
	
	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		
		this.keyH = keyH;
		SCREEN_X = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);
		SCREEN_Y = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);
		//these values can work for the image to make it smaller for collisions
		solidArea = new Rectangle(10,20,24,24);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		attackArea.height = 36;
		attackArea.width = 36;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
	}
	
	public void setDefaultValues() {
		//not where your character is drawn but where you are on the worldmap
		worldX= gp.TILE_SIZE * 23;
		worldY = gp.TILE_SIZE * 21;
		speed = 4;
		direction = "down";
		//player status
		maxLife = 6;
		life = maxLife;
		
	}
	public void getPlayerImage() {
		up1 = setup("/player/boy_up_1",gp.TILE_SIZE,gp.TILE_SIZE);
		up2 = setup("/player/boy_up_2",gp.TILE_SIZE,gp.TILE_SIZE);
		left1 = setup("/player/boy_left_1",gp.TILE_SIZE,gp.TILE_SIZE);
		left2 = setup("/player/boy_left_2",gp.TILE_SIZE,gp.TILE_SIZE);
		right1 = setup("/player/boy_right_1",gp.TILE_SIZE,gp.TILE_SIZE);
		right2 = setup("/player/boy_right_2",gp.TILE_SIZE,gp.TILE_SIZE);
		down1 = setup("/player/boy_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
		down2 = setup("/player/boy_down_2",gp.TILE_SIZE,gp.TILE_SIZE);
		
	}
	
	public void getPlayerAttackImage() {
		attackUp1 = setup("/player/boy_attack_up_1",gp.TILE_SIZE,gp.TILE_SIZE *2);
		attackUp2 = setup("/player/boy_attack_up_2",gp.TILE_SIZE,gp.TILE_SIZE *2);
		attackLeft1 = setup("/player/boy_attack_left_1",gp.TILE_SIZE*2,gp.TILE_SIZE);
		attackLeft2 = setup("/player/boy_attack_left_2",gp.TILE_SIZE*2,gp.TILE_SIZE);
		attackRight1 = setup("/player/boy_attack_right_1",gp.TILE_SIZE*2,gp.TILE_SIZE);
		attackRight2 = setup("/player/boy_attack_right_2",gp.TILE_SIZE*2,gp.TILE_SIZE);
		attackDown1 = setup("/player/boy_attack_down_1",gp.TILE_SIZE,gp.TILE_SIZE *2);
		attackDown1 = setup("/player/boy_attack_down_2",gp.TILE_SIZE,gp.TILE_SIZE *2);
	}
	
	
	public void update() {
		if(attacking) {
			attackMonster();
		}
		else if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.spacePressed || keyH.fPressed) {
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
		
		//check the collision
		collisionOn = false;
		gp.collisionDetector.checkTile(this);
		//check object collision
		int objIndex = gp.collisionDetector.checkObject(this, true);
		pickUpObject(objIndex);
		//check npc Collision
		int npcIndex = gp.collisionDetector.checkEntity(this, gp.npc);
		npcInteraction(npcIndex);
		//check monster collision
		int monsterIndex = gp.collisionDetector.checkEntity(this, gp.monsters);
		monsterInteraction(monsterIndex);
		//check event
		gp.eventHandler.checkEvent();
		//System.out.println("collisionOn value: " + collisionOn);
		//if collision is false
		if(!collisionOn && !keyH.spacePressed) {
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
		gp.keyH.spacePressed = false;
		
		}else {
			spriteNum = 1;
		}
		//update the invinicbility counter
		if(invincible) {
			invincibleCounter ++;
			if(invincibleCounter >60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		
	}
	public void attackMonster() {
		spriteCounter ++;
		if(spriteCounter <= 5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			//save the current world x etc 
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			//adjust player world x for the attack area
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left" : worldX -= attackArea.width; break;
			case "right" : worldX += attackArea.width; break;
			}
			//attack area becomes solid area
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			//check monster collision with the updated values
			int monsterIndex = gp.collisionDetector.checkEntity(this, gp.monsters);
			damageMonster(monsterIndex);
			//restore the values
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
			
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
		
	}

	public void draw(Graphics2D g2) {
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.TILE_SIZE, gp.TILE_SIZE);
		int tempScreenX = SCREEN_X;
		int tempScreenY = SCREEN_Y;
		BufferedImage image = null;
		switch(direction) {
		case"up":
			if(!attacking) {
				if(spriteNum == 1) {
					image = up1;
				}
				if(spriteNum == 2) {
					image = up2;
				}
			}else {
				tempScreenY = SCREEN_Y - gp.TILE_SIZE;
				if(spriteNum == 1) {
					image = attackUp1;
				}
				if(spriteNum == 2) {
					image = attackUp2;
				}
			}
			
			break;
		case "down" :
			if(!attacking) {
				if(spriteNum == 1) {
					image = down1;
				}
				if(spriteNum == 2) {
					image = down2;
				}
			}else {
				if(spriteNum == 1) {
					image = attackDown1;
				}
				if(spriteNum == 2) {
					image = attackDown2;
				}
			}
			
			
			break;
		case "left":
			if(!attacking) {
				if(spriteNum == 1) {
					image = left1;
				}
				if(spriteNum == 2) {
					image = left2;
				}
			}else {
				tempScreenX = SCREEN_X - gp.TILE_SIZE;
				if(spriteNum == 1) {
					image = attackLeft1;
				}
				if(spriteNum == 2) {
					image = attackLeft2;
				}
			}
			
			break;
		case "right":
			if(!attacking) {
				if(spriteNum == 1) {
					image = right1;
				}
				if(spriteNum == 2) {
					image = right2;
				}
			}else {
				if(spriteNum == 1) {
					image = attackRight1;
				}
				if(spriteNum == 2) {
					image = attackRight2;
				}
			}
			
			break;
		}
		if(invincible) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
		}
		g2.drawImage(image, tempScreenX, tempScreenY,null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
		//this allows you to see the collision box
		//g2.setColor(Color.red);
		//g2.drawRect(SCREEN_X + solidArea.x,SCREEN_Y + solidArea.y, solidArea.width,solidArea.height);
		
	}
	
	
	
	
	public void pickUpObject(int index) {
		if(index != -1) {
			
		}
	}
	
	public void npcInteraction(int npcIndex) {
		// TODO Auto-generated method stub
		if(npcIndex != -1) {
			if(gp.keyH.spacePressed) {
				gp.gameState = gp.dialogueState;
				gp.npc[npcIndex].speak();
			}
			gp.keyH.spacePressed = false;
		} else {
			if(gp.keyH.fPressed) {
				//gp.playSoundEffect(7);
				attacking = true;
			}
		}
	}
	
	public void monsterInteraction(int monsterIndex) {
		if(monsterIndex != -1) {
			if(!invincible) {
				gp.playSoundEffect(6);
				life -=1;
				invincible = true;
				
			}
			
		}
	}
	public void damageMonster(int monsterIndex) {
		if(monsterIndex != -1) {
			if(!gp.monsters[monsterIndex].invincible) {
				gp.playSoundEffect(5);
				gp.monsters[monsterIndex].life -= 1;
				gp.monsters[monsterIndex].invincible = true;
				gp.monsters[monsterIndex].damageReaction();
				if(gp.monsters[monsterIndex].life <= 0 ) {
					
					gp.monsters[monsterIndex].dying = true;
				}
			}
		
		}
	}
}
