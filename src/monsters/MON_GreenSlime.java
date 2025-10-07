package monsters;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_GreenSlime extends Entity{
	GamePanel gp;
	
	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		this.gp = gp;
		// TODO Auto-generated constructor stub
		type = 2;
		name = "Green Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		setSolidArea(3,18,42,30);
		getImage();
	}
	
	public void getImage() {
		up1 = setup("/monster/greenslime_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
		up2 = setup("/monster/greenslime_down_2",gp.TILE_SIZE,gp.TILE_SIZE);
		down1 = setup("/monster/greenslime_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
		down2 = setup("/monster/greenslime_down_2",gp.TILE_SIZE,gp.TILE_SIZE);
		left1 = setup("/monster/greenslime_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
		left2 = setup("/monster/greenslime_down_2",gp.TILE_SIZE,gp.TILE_SIZE);
		right1 = setup("/monster/greenslime_down_1",gp.TILE_SIZE,gp.TILE_SIZE);
		right2 = setup("/monster/greenslime_down_2",gp.TILE_SIZE,gp.TILE_SIZE);
	}
	
	public void setAction() {
		actionLockCounter ++;
		//ensures that it doesnt reset the action too frequently
		if(actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;
			//pick a random num from 1 -100
			//simple direciton chosing for now
			if(i <=25) {
				direction = "up";
			}
			if(i > 25 && i<= 50) {
				direction = "down";
			}
			if(i > 50 && i <=75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			actionLockCounter = 0;
		}
	}

}
