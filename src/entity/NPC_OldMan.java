package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class NPC_OldMan extends Entity{
	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 1;
		
	}
	public void getNpcImage() {
		up1 = setup("oldman_up_1");
		up2 = setup("oldman_up_2");
		left1 = setup("oldman_left_1");
		left2 = setup("oldman_left_2");
		right1 = setup("oldman_right_1");
		right2 = setup("oldman_right_2");
		down1 = setup("oldman_down_1");
		down2 = setup("oldman_down_2");
		
	}
	
	
	public BufferedImage setup(String imageName) {
		UtilityTool utilityTool = new UtilityTool();
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/npc/" + imageName + ".png"));
			image = utilityTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
